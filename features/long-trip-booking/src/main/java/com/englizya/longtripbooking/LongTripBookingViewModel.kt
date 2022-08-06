package com.englizya.longtripbooking

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.PrintableTicket
import com.englizya.model.Station
import com.englizya.model.request.SeatPriceRequest
import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.UserTicket
import com.englizya.model.response.WalletDetails
import com.englizya.printer.TicketPrinter
import com.englizya.repository.*
import com.englizya.ticket.utils.Constants
import io.ktor.http.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LongTripBookingViewModel constructor(
    private val ticketPrinter: TicketPrinter,
    private val ticketRepository: TicketRepository,
    private val walletRepository: WalletRepository,
    private val seatRepository: SeatPricingRepository,
    private val manifestoRepository: ManifestoRepository,
    private val stationRepository: StationRepository,
    private val preferences: LocalTicketPreferences,
) : BaseViewModel() {
    private val TAG = "LongTripBookingViewModel"

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

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

    private var _tripPrice = MutableLiveData<Double>(0.0)
    val tripPrice: LiveData<Double> = _tripPrice

    private var _cashLongTicket = MutableLiveData<List<PrintableTicket>>(null)
    val cashLongTicket: LiveData<List<PrintableTicket>> = _cashLongTicket

    private var _lastTicket = MutableLiveData<Ticket>()
    val lastTicket: LiveData<Ticket> = _lastTicket

    private val _isPaperOut = MutableLiveData<Boolean>(false)
    val isPaperOut: LiveData<Boolean> = _isPaperOut

    private var _ticketsInMemory = MutableLiveData<Set<Ticket>>(HashSet())
    val ticketsInMemory: LiveData<Set<Ticket>> = _ticketsInMemory

    //                 Scan Qr Live Data
    private var _qrContent = MutableLiveData<String>()
    val qrContent: LiveData<String> = _qrContent

    private var _walletDetails = MutableLiveData<WalletDetails>()
    val walletDetails: LiveData<WalletDetails> = _walletDetails


    private var _longTickets = MutableLiveData<List<UserTicket>>(null)
    val longTickets: LiveData<List<UserTicket>> = _longTickets


    var _printingOperationCompleted = MutableLiveData<Boolean>(false)
    val printingOperationCompleted: LiveData<Boolean> = _printingOperationCompleted

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

    fun setQrContent(contents: String) {
        Log.d(TAG, "setQrContent: $contents")
        _qrContent.postValue(contents)
    }

    fun getBookingOffices() = viewModelScope.launch(Dispatchers.Main) {
        updateLoading(true)
        stationRepository.getAllStations()
            .onSuccess {
                updateLoading(false)
                Log.d("Stations", it.toString())
                _stations.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.Main) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(preferences.getToken())
            .onSuccess {
                updateLoading(false)
                _manifesto.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    // Get Seat Price
    private fun getSeatPrice() {
        updateLoading(true)
        encapsulateSeatPriceRequest()
            .onSuccess {
                updateLoading(false)
                getPrice(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun encapsulateSeatPriceRequest(): Result<SeatPriceRequest> =
        kotlin.runCatching {
            SeatPriceRequest(
                tripId = manifesto.value?.tripId!!,
                sourceBranchId = source.value?.branchId!!,
                destinationBranchId = destination.value?.branchId!!
            )

        }

    private fun getPrice(request: SeatPriceRequest) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        seatRepository.getSeatPrice(request)
            .onSuccess {
                updateLoading(false)
                _tripPrice.postValue(it)
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }


    }


    fun getStations() = viewModelScope.launch(Dispatchers.Main) {
        if (stations.value.isNullOrEmpty())
            getBookingOffices()
    }

    fun setDestination(destination: String) {
        _destination.value = stations.value?.firstOrNull {
            it.branchName == destination
        }
        getTripPrice()
    }

    fun setSource(source: String) {
        _source.value = stations.value?.firstOrNull {
            it.branchName == source
        }
        getTripPrice()
    }

    private fun getTripPrice() {
        if (destination.value != null && source.value != null) {
            getSeatPrice()
        }
    }

    private fun resetQuantity() {
        _quantity.postValue(1)
    }


    // Booking with wallet
    fun loadWalletDetails() {
        synchronized(_qrContent) {
            loadWalletDetails(qrContent.value!!)
        }
    }

    private fun loadWalletDetails(qrContent: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "updateQR: $qrContent")
            updateLoading(true)
            walletRepository
                .getWallet(
                    driverToken = preferences.getToken(),
                    uid = qrContent
                )
                .onSuccess {
                    updateLoading(false)
                    _walletDetails.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    fun requestLongTicketsWithWallet() {
        updateLoading(true)
        encapsulateRequest()
            .onSuccess {
                updateLoading(false)
                requestLongTickets(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun requestLongTickets(it: TourismTicketsWithWalletRequest) =
        viewModelScope.launch(Dispatchers.Main) {
            updateLoading(true)
            ticketRepository
                .requestLongTicketsWithWallet(it)
                .onSuccess {
                    updateLoading(false)
                    _longTickets.value = it
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    private fun encapsulateRequest(): Result<TourismTicketsWithWalletRequest> =
        kotlin.runCatching {
            TourismTicketsWithWalletRequest(
                AuthScheme.Bearer + " " + preferences.getToken(),
                preferences.getReservationId(),
                preferences.getTripId(),
                qrContent.value!!,
                source.value!!.branchId,
                destination.value!!.branchId,
                quantity.value!!
            )
        }

    fun printLongTickets(list: List<UserTicket>) = runBlocking(Dispatchers.IO) {
        ticketPrinter.printTickets(list)
        _printingOperationCompleted.postValue(true)
        _longTickets.postValue(null)
    }

    // Booking with Cash
    fun requestLongTicketCash() =
        viewModelScope.launch(Dispatchers.Main) {
            updateLoading(true)
            ticketRepository
                .requestLongTicketsWithCash(
                    preferences.getToken(), source.value?.branchId!!,
                    destination.value!!.branchId,
                    quantity.value!!
                )
                .onSuccess {
                    updateLoading(false)
                    resetQuantity()
                    printCashLongTickets(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    fun printCashLongTickets(list: List<PrintableTicket>) {
        runBlocking(Dispatchers.IO) {
            ticketPrinter.printCashTickets(list)
//        _printingOperationCompleted.postValue(true)
//        _longTickets.postValue(null)
        }
    }

    fun updateLocation(location: Location) {
        _location.value = location
        Log.d(TAG, "updateLocation: ${location.latitude}")
    }
}