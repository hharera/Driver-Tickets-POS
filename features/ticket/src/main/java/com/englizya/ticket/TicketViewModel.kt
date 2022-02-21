package com.englizya.ticket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.core.CarDataStore
import com.englizya.datastore.core.DriverDataStore
import com.englizya.datastore.core.ManifestoDataStore
import com.englizya.datastore.core.TicketDataStore
import com.englizya.model.request.Ticket
import com.englizya.common.utils.time.TimeUtils.getTicketTimeMillis
import com.englizya.printer.PaxPrinter
import com.englizya.printer.TicketPrinter
import com.englizya.printer.utils.PrinterState.OUT_OF_PAPER
import com.englizya.repository.TicketRepository
import com.englizya.ticket.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.lang.Integer.max
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketPrinter: TicketPrinter,
    private val ticketRepository: TicketRepository,
    private val manifestoDataStore: ManifestoDataStore,
    private val driverDataStore: DriverDataStore,
    private val carDataStore: CarDataStore,
    private val ticketDataStore: TicketDataStore,
) : BaseViewModel() {

    private var _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity

    private var _ticketsInMemory = MutableLiveData<Set<Ticket>>(HashSet())
    val ticketsInMemory: LiveData<Set<Ticket>> = _ticketsInMemory

    private var _lastTicket = MutableLiveData<Ticket>()
    val lastTicket: LiveData<Ticket> = _lastTicket

    private var _paymentWay = MutableLiveData<String>()
    val paymentWay: LiveData<String> = _paymentWay

    private var _ticketCategory = MutableLiveData<Int>()
    val ticketCategory: LiveData<Int> = _ticketCategory

    private val TAG = "TicketViewModel"

    private val _isPaperOut = MutableLiveData<Boolean>(false)
    val isPaperOut: LiveData<Boolean> = _isPaperOut

    init {
        _ticketCategory.postValue(ticketDataStore.getTicketCategory())

        viewModelScope.launch(Dispatchers.IO) {
            ticketRepository.getLocalTickets().onSuccess {
                Log.d(TAG, "$it")
            }
        }
    }

    fun decrementQuantity() {
        _quantity.postValue(
            (_quantity.value!! - 1).coerceAtLeast(Constant.MIN_TICKETS)
        )
    }

    fun incrementQuantity() {
        _quantity.postValue(
            (_quantity.value!! + 1).coerceAtMost(Constant.MAX_TICKETS)
        )
    }

    fun setQuantity(quantity: Int) {
        _quantity.postValue(min(max(Constant.MIN_TICKETS, quantity), Constant.MAX_TICKETS))
    }

    fun setPaymentWay(way: String) {
        _paymentWay.postValue(way)
    }

    suspend fun submitTickets() {
        generateTickets(quantity.value!!).let { tickets ->
            insertTickets(tickets, connectivity.value!!)
        }
    }

    private suspend fun insertTickets(tickets: ArrayList<Ticket>, pushRemote: Boolean) {
        updateLoading(true)
        ticketRepository
            .insertTickets(tickets, pushRemote)
            .onSuccess {
                updateLoading(false)
                resetQuantity()
                printTickets(tickets)
                Log.d(TAG, "orderTickets: $tickets")
            }
            .onFailure {
                if (pushRemote)
                    insertTickets(tickets, false)
                updateLoading(false)
                handleException(it)
            }
    }

    private fun printTickets(tickets: ArrayList<Ticket>) {
        viewModelScope.launch(Dispatchers.IO) {
            _lastTicket.postValue(tickets.last())
            tickets.forEach { ticket ->
                ticketPrinter.printTicket(ticket).let { printState ->
                    checkPrintState(printState, ticket)
                }
            }
        }
    }

    fun printTicketsInMemory() {
        ticketsInMemory.value!!.forEach { ticket ->
            ticketPrinter.printTicket(ticket).let { printState ->
                checkPrintTicketsInMemoryState(printState, ticket)
            }
        }
    }

    private fun checkPrintTicketsInMemoryState(printState: String, ticket: Ticket) {
        Log.d(TAG, "checkPrintTicketsInMemoryState: $printState")
        if (printState.contains("Success")) {
            _ticketsInMemory.postValue(ticketsInMemory.value!!.minus(ticket))
        }
    }

    private suspend fun checkPrintState(printState: String, ticket: Ticket) {
        Log.d(TAG, "checkPrintState: $printState")
        if (printState == OUT_OF_PAPER) {
            _isPaperOut.postValue(true)
            Log.d(TAG, "checkPrintState: $ticket")
            _ticketsInMemory.postValue(ticketsInMemory.value!!.plus(ticket))
        }
    }

    private suspend fun saveUnPrintedTicket(ticket: Ticket) {
        ticketRepository
            .insertUnPrintedTicket(ticket)
            .onSuccess {
                getUnPrintedTickets()
            }
    }

    private fun getUnPrintedTickets() {
        ticketRepository
            .getAllUnPrintedTickets()
            .onSuccess {
            }
    }

    private fun resetQuantity() {
        _quantity.postValue(1)
    }

    private suspend fun insertTicketsLocally(tickets: ArrayList<Ticket>) {
        updateLoading(true)
        ticketRepository
            .insertTickets(tickets, false)
            .onSuccess {
                updateLoading(false)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun generateTickets(quantity: Int): ArrayList<Ticket> {
        val currentMillis = getTicketTimeMillis()
        val tickets = ArrayList<Ticket>()

        for (i in 1..quantity) {
            tickets.add(
                Ticket(
                    ticketId = createTicketId(currentMillis + i * 5),
                    lineCode = carDataStore.getCarLineCode(),
                    driverCode = driverDataStore.getDriverCode(),
                    carCode = carDataStore.getCarCode(),
                    time = DateTime.now().toString(),
                    paymentWay = paymentWay.value!!,
                    ticketCategory = ticketDataStore.getTicketCategory(),
                    manifestoId = manifestoDataStore.getManifestoNo(),
                    manifestoYear = manifestoDataStore.getManifestoYear(),
                    ticketLatitude = null,
                    ticketLongitude = null
                )
            )
        }

        return tickets
    }

    private fun createTicketId(currentTime: Long): String {
        return carDataStore.getCarCode()
            .plus("-")
            .plus(driverDataStore.getDriverCode())
            .plus("-")
            .plus(currentTime)
    }
}
