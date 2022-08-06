package com.example.scan_reserved_ticket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.response.UserTicket
import com.englizya.printer.TicketPrinter
import com.englizya.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScanReservedTicketViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val localTicketPreferences: LocalTicketPreferences,
    private val ticketPrinterImpl: TicketPrinter,
) : BaseViewModel() {

    companion object {
        private const val TAG = "ScanReservedTicketViewModel"
    }

    private var _qrContent = MutableLiveData<String>()
    val qrContent: LiveData<String> = _qrContent

    private var _reservedTicketResponse = MutableLiveData<UserTicket>()
    val reservedTicketResponse: LiveData<UserTicket> = _reservedTicketResponse


    private var _printingOperationCompleted = MutableLiveData<Boolean>()
    val printingOperationCompleted: LiveData<Boolean> = _printingOperationCompleted


    private var _deactivationResponse = MutableLiveData<String>()
    val deactivationResponse: LiveData<String> = _deactivationResponse
    fun setQrContent(contents: String) {
        Log.d(TAG, "setQrContent: $contents")
        _qrContent.postValue(contents)
    }

    fun requestPayedTicket() = viewModelScope.launch {
        updateLoading(true)
        ticketRepository.requestReservedTicket(
            localTicketPreferences.getToken(),
            _qrContent.value!!
        )
            .onSuccess {
                updateLoading(false)
                _reservedTicketResponse.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun deactivateTicket() = viewModelScope.launch {
        updateLoading(true)
        ticketRepository.deactivateTicket(
            localTicketPreferences.getToken(),
            _qrContent.value!!
        )
            .onSuccess {
                updateLoading(false)
                _deactivationResponse.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun whenPrintClicked() = runBlocking(Dispatchers.IO) {
        reservedTicketResponse.value?.let { printTickets(it) }
    }

    fun printTickets(ticket: UserTicket) {

        viewModelScope.launch(Dispatchers.Main) {
            ticketPrinterImpl.printTicket(ticket).let { printState ->
            }

            _printingOperationCompleted.value = true
        }
    }
}