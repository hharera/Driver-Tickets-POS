package com.englizya.end_shift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.core.DriverDataStore
import com.englizya.datastore.core.ManifestoDataStore
import com.englizya.datastore.utils.Value
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EndShiftViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val manifestoRepository: ManifestoRepository,
    private val manifestoDataStore: ManifestoDataStore,
    private val driverDataStore: DriverDataStore,
) : BaseViewModel() {

    private val _shiftReport = MutableLiveData<ShiftReportResponse>()
    val shiftReport: LiveData<ShiftReportResponse> = _shiftReport

    suspend fun endShift() {
        getLocalTickets()
    }

    private suspend fun getLocalTickets() {
        updateLoading(true)

        ticketRepository
            .getLocalTickets()
            .onSuccess {
                updateLoading(false)
                uploadLocalTickets(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private suspend fun uploadLocalTickets(list: List<Ticket>) {
        updateLoading(true)

        ticketRepository
            .insertTickets(list, true)
            .onSuccess {
                updateLoading(false)
                deleteLocalTickets()
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
                clearDataStore()
                _shiftReport.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun clearDataStore() {
        driverDataStore.setDriverCode(Value.NULL_INT)
    }

}