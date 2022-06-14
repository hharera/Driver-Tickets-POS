package com.englizya.wallet

import android.content.Intent
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.ReservationTicket
import com.englizya.model.Station
import com.englizya.model.Trip
import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.UserTicket
import com.englizya.model.response.WalletDetails
import com.englizya.printer.TicketPrinter
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.StationRepository
import com.englizya.repository.TicketRepository
import com.englizya.repository.WalletRepository
import io.ktor.http.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class WalletPaymentViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val manifestoRepository: ManifestoRepository,
    private val walletRepository: WalletRepository,
    private val localTicketPreferences: LocalTicketPreferences,
    private val ticketPrinter: TicketPrinter,
    private val stationRepository: StationRepository,
) : BaseViewModel() {

    companion object {
        private const val TAG = "WalletPaymentViewModel"
        private const val ACCEPT_PAYMENT_REQUEST = 3005
    }

    private var _longTickets = MutableLiveData<List<UserTicket>>()
    val longTickets: LiveData<List<UserTicket>> = _longTickets

    private var _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity

    private var _selectedCategory = MutableLiveData<Int>()
    val selectedCategory: LiveData<Int> = _selectedCategory

    private var _ticketCategory = MutableLiveData<Int>()
    val ticketCategory: LiveData<Int> = _ticketCategory

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    private var _source = MutableLiveData<Station>()
    val source: LiveData<Station> = _source

    private var _destination = MutableLiveData<Station>()
    val destination: LiveData<Station> = _destination

    private var _date = MutableLiveData<DateTime>()
    val date: LiveData<DateTime> = _date

    private var _tripId = MutableLiveData<Int>()
    val tripId: LiveData<Int> = _tripId

    private var _trip = MutableLiveData<Trip>()
    val trip: LiveData<Trip> = _trip

    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    private var _reservationTickets = MutableLiveData<List<ReservationTicket>>()
    val reservationTickets: LiveData<List<ReservationTicket>> = _reservationTickets

    private var _uid = MutableLiveData<String>()
    val uid: LiveData<String> = _uid

    private var _walletOtp = MutableLiveData<String>("")
    val walletOtp: LiveData<String> = _walletOtp

    private var _codeValidity = MutableLiveData<Boolean>(false)
    val codeValidity: LiveData<Boolean> = _codeValidity

    private var _formValidity = MutableLiveData<Boolean>(false)
    val formValidity: LiveData<Boolean> = _formValidity

    private var _verificationState = MutableLiveData<Boolean>(false)
    val verificationState: LiveData<Boolean> = _verificationState

    private var _qrContent = MutableLiveData<String>()
    val qrContent: LiveData<String> = _qrContent

    private var _walletDetails = MutableLiveData<WalletDetails>()
    val walletDetails: LiveData<WalletDetails> = _walletDetails

    private var _ticketCategories = MutableLiveData<Set<String>>()
    val ticketCategories: LiveData<Set<String>> = _ticketCategories

    private var _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>> = _stations

    private var _tourismTicket = MutableLiveData<List<Ticket>>()
    val tourismTicket: LiveData<List<Ticket>> = _tourismTicket

    private var _shortTicket = MutableLiveData<List<Ticket>>()
    val shortTicket: LiveData<List<Ticket>> = _shortTicket

    private var _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude

    private var _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> = _longitude

    private var _sourceStationId = MutableLiveData<Int>()
    val sourceStationId: LiveData<Int> = _sourceStationId

    private var _destinationStationId = MutableLiveData<Int>()
    val destinationStationId: LiveData<Int> = _destinationStationId

    private var _printingOperationCompleted = MutableLiveData<Boolean>()
    val printingOperationCompleted: LiveData<Boolean> = _printingOperationCompleted

    init {
        setDefaultDate()
        setDefaultSelectedCategory()
        resetQuantity()
        fetchDriverManifesto()
//        _qrContent.postValue("RzuMXSKTGcNoc6Lwd6svPnK73E42")
    }

    private fun setDefaultSelectedCategory() {
        _selectedCategory.value =
            localTicketPreferences.getTicketCategories()?.firstOrNull()?.toInt()?.also {
                _total.value = quantity.value?.times(it)
            }
    }

    private fun setDefaultDate() {
        _date.postValue(DateTime.now())
    }

    private fun checkFormValidity() {
        if (null == _source.value) {
            _formValidity.value = false
        } else if (null == _destination.value) {
            _formValidity.value = false
        } else _formValidity.value = null != _date.value
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }


    fun setDestination(destination: String) {
        _destination.value = stations.value?.firstOrNull {
            it.branchName == destination
        }
        checkFormValidity()
    }

    fun setSource(source: String) {
        Log.d(TAG, "setSource: $source")
        _source.value = stations.value?.firstOrNull {
            it.branchName == source

        }
        checkFormValidity()
    }

    fun requestPayment() {
        when (localTicketPreferences.getManifestoType()) {
            0 -> {
                createBookingBilling()
            }

            1 -> {
                createTicketsBilling()
            }
        }
    }

    private fun createTicketsBilling() {

    }

    private fun createBookingBilling() {
        // to go to booking fragment


    }

    private fun calculateAmount(): Double {
        return trip.value?.let {
            it.plan?.seatPrices?.first {
                it.source == source.value?.branchId &&
                        it.destination == destination.value?.branchId
            }?.vipPrice!! * quantity.value!!
        }!!.toDouble()
    }

    fun putCharacter(numberCharacter: String) {
        if (_walletOtp.value.isNullOrBlank())
            _walletOtp.value = numberCharacter
        else if (_walletOtp.value!!.length < 4)
            _walletOtp.value = _walletOtp.value!!.plus(numberCharacter)

        checkCodeValidity()
    }

    fun removeCharacter() {
        if (null != _walletOtp.value && _walletOtp.value!!.isNotBlank()) {
            _walletOtp.value = _walletOtp.value!!.dropLast(1)
        }

        checkCodeValidity()
    }

    private fun checkCodeValidity() {
        _codeValidity.value = Validity.checkWalletOtpValidity(_walletOtp.value!!)
    }

    fun setQrContent(contents: String) {
        Log.d(TAG, "setQrContent: $contents")
        _qrContent.postValue(contents)
    }

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
                    driverToken = localTicketPreferences.getToken(),
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

    fun getManifestoType(): Int {
        return localTicketPreferences.getManifestoType()
    }

    private fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(localTicketPreferences.getToken())
            .onSuccess {
                updateLoading(false)
                _manifesto.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun decrementQuantity() {
        _quantity.value = (_quantity.value!! - 1).coerceAtLeast(Constant.MIN_TICKETS)
        _total.value = selectedCategory.value?.let {
            quantity.value?.times(it)
        }
    }

    fun incrementQuantity() {
        _quantity.value = (_quantity.value!! + 1).coerceAtMost(Constant.MAX_TICKETS)
        _total.value = selectedCategory.value?.let {
            quantity.value?.times(it)
        }
    }

    private fun resetQuantity() {
        _quantity.postValue(1)
    }

    fun setSelectedCategory(category: Int) {
        _selectedCategory.value = category
    }

    fun getTicketCategories() {
        localTicketPreferences.getTicketCategories().let {
            _ticketCategories.postValue(it)
        }
    }

    fun whenPayClicked() {
        updateLoading(true)
        when (getManifestoType()) {
            0 -> {
                requestTourismTickets()
            }

            1 -> {
                requestShortTickets()
            }
        }
    }


    private fun requestTourismTickets() = viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .requestTourismTickets(
                localTicketPreferences.getToken(),
                qrContent.value!!,
                quantity.value!!,
                sourceStationId.value!!, destinationStationId.value!!,
                tripId.value!!
            )
            .onSuccess {
                updateLoading(false)
                _tourismTicket.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }


    suspend fun getBookingOffices() {
        updateLoading(true)
        stationRepository.getAllStations()
            .onSuccess {
                updateLoading(false)
                _stations.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }


    private fun updateData(list: List<Station>) {
        _source.postValue(list.firstOrNull())
        _destination.postValue(list.lastOrNull())
    }

    private fun requestShortTickets() = viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .requestTickets(
                localTicketPreferences.getToken(),
                qrContent.value!!,
                quantity.value!!,
                selectedCategory.value!!,
//                walletOtp.value!!,
//                latitude.value,
//                longitude.value
            )
            .onSuccess {
                updateLoading(false)
                _shortTicket.postValue(it)
//                Class.forName("com.englizya.navigation.HomeActivity").let {
//                    val intent = Intent(requireContext(), it)
//                    startActivity(intent)
                    //navigateToSelectTicket()
       //         }

            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun updateLocation(location: Location) {
        _latitude.value = location.latitude
        _longitude.value = location.longitude
        Log.d(TAG, "updateLocation: ${location.latitude}")
    }

    fun printTickets(tickets: List<Ticket>) {
        viewModelScope.launch(Dispatchers.IO) {
            tickets.forEach { ticket ->
                ticketPrinter.printTicket(ticket).let { printState ->
                }
            }

            _printingOperationCompleted.postValue(true)
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

    private fun requestLongTickets(it: TourismTicketsWithWalletRequest) = viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .requestLongTicketsWithWallet(it)
            .onSuccess {
                updateLoading(false)
                _longTickets.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun encapsulateRequest(): Result<TourismTicketsWithWalletRequest> =
        kotlin.runCatching {
            TourismTicketsWithWalletRequest(
                AuthScheme.Bearer + " " + localTicketPreferences.getToken(),
                localTicketPreferences.getReservationId(),
                localTicketPreferences.getTripId(),
                qrContent.value!!,
                source.value!!.branchId,
                destination.value!!.branchId,
                quantity.value!!
            )
        }

    fun printLongTickets(list: List<UserTicket>) {
        ticketPrinter.printTickets(list)
    }
}
