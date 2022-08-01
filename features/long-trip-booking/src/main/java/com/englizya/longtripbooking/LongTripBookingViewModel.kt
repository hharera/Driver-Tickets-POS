package com.englizya.longtripbooking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.time.TimeUtils
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.Station
import com.englizya.model.request.Ticket
import com.englizya.model.response.ManifestoDetails
import com.englizya.printer.TicketPrinter
import com.englizya.printer.utils.PrinterState
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.R
import com.englizya.repository.StationRepository
import com.englizya.repository.TicketRepository
import com.englizya.ticket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class LongTripBookingViewModel constructor(
    private val ticketPrinter: TicketPrinter,
    private val ticketRepository: TicketRepository,
    private val manifestoRepository: ManifestoRepository,
    private val stationRepository: StationRepository,
    private val preferences: LocalTicketPreferences,
) : BaseViewModel() {
    private val TAG = "LongTripBookingViewModel"

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    private var _formValidity = MutableLiveData<BookingFormState>()
    val formValidity: LiveData<BookingFormState> = _formValidity

    private var _paymentMethod = MutableLiveData<PaymentMethod>(PaymentMethod.Cash)
    val paymentMethod: LiveData<PaymentMethod> = _paymentMethod

    private var _source = MutableLiveData<Station>()
    val source: LiveData<Station> = _source

    private var _destination = MutableLiveData<Station>()
    val destination: LiveData<Station> = _destination

    private var _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>> = _stations

    private var _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity

    private var _total = MutableLiveData<Int>(0)
    val total: LiveData<Int> = _total

    private var _tripPrice = MutableLiveData<Int>(0)
    val tripPrice: LiveData<Int> = _tripPrice


    private var _lastTicket = MutableLiveData<Ticket>()
    val lastTicket: LiveData<Ticket> = _lastTicket

    private val _isPaperOut = MutableLiveData<Boolean>(false)
    val isPaperOut: LiveData<Boolean> = _isPaperOut

    private var _ticketsInMemory = MutableLiveData<Set<Ticket>>(HashSet())
    val ticketsInMemory: LiveData<Set<Ticket>> = _ticketsInMemory

    fun setPaymentMethod(method: PaymentMethod) {
        _paymentMethod.value = method
    }

    fun decrementQuantity() {
        _quantity.postValue(
            (_quantity.value!! - 1).coerceAtLeast(Constants.MIN_TICKETS)
        )
    }

    fun incrementQuantity() {
        _quantity.postValue(
            (_quantity.value!! + 1).coerceAtMost(Constants.MAX_TICKETS)
        )
    }

    init {
        fetchDriverManifesto()
    }

    fun getBookingOffices() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        stationRepository.getAllStations()
            .onSuccess {
                updateLoading(false)
                Log.d("Stations", it.toString())
                _stations.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
   private fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(preferences.getToken())
            .onSuccess {
                updateLoading(false)
                _manifesto.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun getPrice() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)




    }

    fun getStations() = viewModelScope.launch(Dispatchers.IO) {
        if (stations.value.isNullOrEmpty())
            getBookingOffices()
    }

    fun setDestination(destination: String) {
        _destination.value = stations.value?.firstOrNull {
            it.branchName == destination
        }
//        checkFormValidity()
    }

    fun setSource(source: String) {
//        Log.d(TAG, "setSource: $source")
        _source.value = stations.value?.firstOrNull {
            it.branchName == source
        }
//        checkFormValidity()
    }
    private fun createTicketId(currentTime: Long): String {
        return preferences.getCarCode()
            .plus("-")
            .plus(preferences.getDriverCode())
            .plus("-")
            .plus(currentTime)
    }
    private fun getPaymentMethod(): String {
        return when (paymentMethod.value!!) {
            PaymentMethod.Cash -> "CASH"
            PaymentMethod.Card -> "CARD"
            PaymentMethod.QR -> "QR"
        }
    }

    suspend fun submitTickets() {
        generateTickets(quantity.value!!).let { tickets ->
            Log.d("tickets" , tickets.toString())
            insertTickets(tickets, true)
        }
    }
    private fun resetQuantity() {
        _quantity.postValue(1)
    }
    private fun printTickets(tickets: ArrayList<Ticket>) {
        viewModelScope.launch(Dispatchers.IO) {
            _lastTicket.postValue(tickets.last())
            tickets.forEach { ticket ->
                Log.d("ticket" , ticket.toString())
                ticketPrinter.printTicket(ticket)
//                    .let { printState ->
//                    checkPrintState(printState, ticket)
//                }
            }
        }
    }
    private suspend fun checkPrintState(printState: String, ticket: Ticket) {
        Log.d(TAG, "checkPrintState: $printState")
        if (printState == PrinterState.OUT_OF_PAPER) {
            _isPaperOut.postValue(true)
            Log.d(TAG, "checkPrintState: $ticket")
            _ticketsInMemory.postValue(ticketsInMemory.value!!.plus(ticket))
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
    private fun generateTickets(quantity: Int): ArrayList<Ticket> {
        val currentMillis = TimeUtils.getTicketTimeMillis()
        val tickets = ArrayList<Ticket>()

        for (i in 1..quantity) {
            tickets.add(
                Ticket(
                    ticketId = createTicketId(currentMillis + i * 5),
                    lineCode = preferences.getCarLineCode(),
                    driverCode = preferences.getDriverCode(),
                    carCode = preferences.getCarCode(),
                    time = DateTime.now().toString(),
                    paymentWay = getPaymentMethod(),
                    ticketCategory = 100,
                    manifestoId = preferences.getManifestoNo(),
                    manifestoYear = preferences.getManifestoYear(),

                )
            )
        }

        return tickets
    }


}