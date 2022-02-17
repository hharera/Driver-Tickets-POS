package com.englizya.end_shift

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.core.DriverDataStore
import com.englizya.datastore.core.ManifestoDataStore
import com.englizya.datastore.utils.Value
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.TicketPrinter
import com.englizya.printer.utils.PrinterState.OUT_OF_PAPER
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EndShiftViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val ticketPrinter: TicketPrinter,
    private val manifestoRepository: ManifestoRepository,
    private val manifestoDataStore: ManifestoDataStore,
    private val driverDataStore: DriverDataStore,
) : BaseViewModel() {

    private val TAG = "EndShiftViewModel"
    private val _shiftReport = MutableLiveData<ShiftReportResponse>()
    val shiftReport: LiveData<ShiftReportResponse> = _shiftReport

    private val _isPaperOut = MutableLiveData<Boolean>(false)
    val isPaperOut: LiveData<Boolean> = _isPaperOut

    suspend fun endShift() {
        getLocalTickets()
    }

    private suspend fun getLocalTickets() {
        updateLoading(true)

        ticketRepository
            .getLocalTickets()
            .onSuccess {
                updateLoading(false)
                Log.d(TAG, "getLocalTickets: $it")
                uploadLocalTickets(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private suspend fun uploadLocalTickets(tickets: List<Ticket>) {
        updateLoading(true)

        if (tickets.isNotEmpty()) {
            ticketRepository
                .insertTickets(tickets, true)
                .onSuccess {
                    updateLoading(false)
                    deleteLocalTickets()
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        } else {
            updateLoading(false)
            deleteLocalTickets()
        }

    }

    private suspend fun deleteLocalTickets() {
        updateLoading(true)

        ticketRepository
            .deleteLocalTickets()
            .onSuccess {
                updateLoading(false)
                getShiftReport()
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private suspend fun getShiftReport() {
        updateLoading(true)

        manifestoRepository
            .getShiftReport(
                EndShiftRequest(
                    manifestoDataStore.getManifestoYear(),
                    manifestoDataStore.getManifestoNo()
                )
            )
            .onSuccess {
                updateLoading(false)
                logout()
                _shiftReport.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun logout() {
        driverDataStore.setToken(Value.NULL_STRING)
    }

    fun printReport(shiftReport: ShiftReportResponse) {
        ticketPrinter.printShiftReport(shiftReport).let {
            checkPrintResult(it)
        }
    }

    private fun checkPrintResult(printState: String) {
        if (printState == OUT_OF_PAPER) {
            _isPaperOut.postValue(true)
        }
    }
}