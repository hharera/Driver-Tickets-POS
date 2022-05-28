package com.englizya.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.core.DriverDataStore
import com.englizya.datastore.core.ManifestoDataStore
import com.englizya.datastore.core.TicketDataStore
import com.englizya.model.LineStation
import com.englizya.model.ReservationTicket
import com.englizya.model.Station
import com.englizya.model.Trip
import com.englizya.model.request.*
import com.englizya.model.response.*
import com.englizya.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class WalletPaymentViewModel constructor(
    private val userRepository: UserRepository,
    private val manifestoDataStore: ManifestoDataStore,
    private val manifestoRepository: ManifestoRepository,
    private val tripsRepository: TripRepository,
    private val ticketDataStore: TicketDataStore,
    private val walletRepository: WalletRepository,
    private val driverDataStore: DriverDataStore,
) : BaseViewModel() {

    companion object {
        private const val TAG = "WalletPaymentViewModel"
        private const val ACCEPT_PAYMENT_REQUEST = 3005
    }

    private var _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity

    private var _selectedCategory = MutableLiveData<Int>()
    val selectedCategory: LiveData<Int> = _selectedCategory

    private var _ticketCategory = MutableLiveData<Int>()
    val ticketCategory: LiveData<Int> = _ticketCategory

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    private var _source = MutableLiveData<LineStation>()
    val source: LiveData<LineStation> = _source

    private var _destination = MutableLiveData<LineStation>()
    val destination: LiveData<LineStation> = _destination

    private var _date = MutableLiveData<DateTime>()
    val date: LiveData<DateTime> = _date

    private var _tripId = MutableLiveData<Trip>()
    val trip: LiveData<Trip> = _tripId

    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    private var _reservationTickets = MutableLiveData<List<ReservationTicket>>()
    val reservationTickets: LiveData<List<ReservationTicket>> = _reservationTickets

    private var _uid = MutableLiveData<String>()
    val uid: LiveData<String> = _uid

    private var _code = MutableLiveData<String>("")
    val code: LiveData<String> = _code

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

    init {
        setDefaultDate()
        resetQuantity()
        fetchDriverManifesto()
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


    fun setDestination(destination: String) {
        _destination.value = trip.value?.stations?.firstOrNull {
            it.branch?.branchName == destination
        }
        checkFormValidity()
    }

    fun setSource(source: String) {
        Log.d(TAG, "setSource: $source")
        _source.value = trip.value?.stations?.firstOrNull {
            it.branch?.branchName == source
        }
        checkFormValidity()
    }

    fun requestPayment() {
        when (manifestoDataStore.getManifestoType()) {
            0 -> {
                createBookingBilling()
            }

            1 -> {
                createTicketsBilling()
            }
        }
    }

    private fun calculateAmount(): Double {
        return trip.value?.let {
            it.plan?.seatPrices?.first {
                it.source == source.value?.branchId &&
                        it.destination == destination.value?.branchId
            }?.vipPrice!! * selectedSeats.value!!.size
        }!!.toDouble()
    }

    fun putCharacter(numberCharacter: String) {
        if (code.value.isNullOrBlank())
            _code.value = numberCharacter
        else if (code.value!!.length < 6)
            _code.value = code.value!!.plus(numberCharacter)

        checkCodeValidity()
    }

    fun removeCharacter() {
        if (null != _code.value && _code.value!!.isNotBlank()) {
            _code.value = _code.value!!.dropLast(1)
        }

        checkCodeValidity()
    }

    private fun checkCodeValidity() {
        _codeValidity.value = Validity.checkWalletOtpValidity(_code.value!!)
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

    private fun loadWalletDetails(qrContent: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "updateQR: $qrContent")
        updateLoading(true)
        walletRepository
            .getWallet(
                driverToken = driverDataStore.getToken(),
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
        return manifestoDataStore.getManifestoType()
    }

    private fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(driverDataStore.getToken())
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
        _quantity.postValue(
            (_quantity.value!! - 1).coerceAtLeast(Constant.MIN_TICKETS)
        )
    }

    fun incrementQuantity() {
        _quantity.postValue(
            (_quantity.value!! + 1).coerceAtMost(Constant.MAX_TICKETS)
        )
    }

    private fun resetQuantity() {
        _quantity.postValue(1)
    }

    fun setSelectedCategory(category: Int) {
        _selectedCategory.value = category
    }

    fun getTicketCategories() {
        ticketDataStore.getTicketCategories().let {
            _ticketCategories.postValue(it)
        }
    }
}
