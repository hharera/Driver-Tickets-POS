package com.englizya.end_shift

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.datastore.utils.Value
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.request.Ticket
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.TicketPrinter
import com.englizya.printer.utils.PrinterState.OUT_OF_PAPER
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EndShiftViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val ticketPrinter: TicketPrinter,
    private val manifestoRepository: ManifestoRepository,
    private val dataStore: LocalTicketPreferences,
) : BaseViewModel() {

    private val TAG = "EndShiftViewModel"

    private val _shiftReport = MutableLiveData<ShiftReportResponse>()
    val shiftReport: LiveData<ShiftReportResponse> = _shiftReport


    private val _longManifestoShiftReport = MutableLiveData<LongManifestoEndShiftResponse>()
    val longManifestoShiftReport: LiveData<LongManifestoEndShiftResponse> =
        _longManifestoShiftReport

    private val _isPaperOut = MutableLiveData<Boolean>(false)
    val isPaperOut: LiveData<Boolean> = _isPaperOut
    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto
    suspend fun endShift() {
        getLocalTickets()
    }

    init {
        fetchDriverManifesto()
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

    fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(dataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _manifesto.postValue(it)
                Log.d("Manifesto",_manifesto.value?.isShortManifesto.toString())
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
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
        if (_manifesto.value?.isShortManifesto == 0) {
            //Long Manifesto
            manifestoRepository.getLongManifestoShiftReport()
                .onSuccess {
                    updateLoading(false)
                    _longManifestoShiftReport.postValue(it)

                }.onFailure {
                    updateLoading(false)
                    handleException(it)
                }

        } else {
            updateLoading(true)

            manifestoRepository
                .getShiftReport(
                    EndShiftRequest(
                        dataStore.getManifestoYear(),
                        dataStore.getManifestoNo()
                    )
                )
                .onSuccess {
                    updateLoading(false)
                    _shiftReport.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }

    }

    fun logout() {
        dataStore.setToken(Value.NULL_STRING)
    }

    fun printReport(shiftReport: ShiftReportResponse) {
        ticketPrinter.printShiftReport(shiftReport).let {
            checkPrintResult(it)
        }
    }

    fun printReport(shiftReport: LongManifestoEndShiftResponse) {
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