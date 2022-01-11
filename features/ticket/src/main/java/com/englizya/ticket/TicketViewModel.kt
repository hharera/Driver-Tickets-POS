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
import com.englizya.printer.TicketPrinter
import com.englizya.printer.utils.Time.getTicketTimeMillis
import com.englizya.repository.TicketRepository
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

    private var _paymentWay = MutableLiveData<String>()
    val paymentWay: LiveData<String> = _paymentWay

    private var _ticketCategory = MutableLiveData<Int>()
    val ticketCategory: LiveData<Int> = _ticketCategory

    private val TAG = "TicketViewModel"

    init {
        _ticketCategory.postValue(ticketDataStore.getTicketCategory())
//        TODO test
        viewModelScope.launch(Dispatchers.IO) {
            ticketRepository.getLocalTickets().onSuccess {
                Log.d(TAG, "$it")
            }
        }
    }

    fun decrementQuantity() {
        _quantity.postValue(
            Math.max(_quantity.value!! - 1, 1)
        )
    }

    fun incrementQuantity() {
        _quantity.postValue(
            Math.min(_quantity.value!! + 1, 99)
        )
    }

    fun setQuantity(quantity: Int) {
        _quantity.postValue(min(max(1, quantity), 99))
    }

    fun setPaymentWay(way : String) {
        _paymentWay.postValue(way)
    }

    suspend fun orderTickets() {
        getTickets(quantity.value!!).let { tickets ->
            insertTickets(tickets)
        }
    }

    private suspend fun insertTickets(tickets: ArrayList<Ticket>) {
        updateLoading(true)
        ticketRepository
            .insertTickets(tickets, connectivity.value!!)
            .onSuccess {
                updateLoading(false)
                ticketPrinter.printTickets(tickets)
                Log.d(TAG, "orderTickets: $tickets")
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun getTickets(quantity: Int): ArrayList<Ticket> {
        val currentMillis = getTicketTimeMillis()
        val tickets = ArrayList<Ticket>()

        for (i in 1..quantity) {
            tickets.add(
                Ticket(
//                    TODO insert Connectivity
                    ticketId = "${carDataStore.getCarCode()}-${driverDataStore.getDriverCode()}-${(currentMillis + i)}",
                    lineCode = carDataStore.getCarLineCode(),
                    driverCode = driverDataStore.getDriverCode(),
                    carCode = carDataStore.getCarCode(),
                    time = DateTime.now().toString(),
                    date = DateTime.now().toString(),
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
}
