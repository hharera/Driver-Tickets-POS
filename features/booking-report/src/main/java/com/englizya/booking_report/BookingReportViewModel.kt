package com.englizya.booking_report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.response.BookingReportResponse
import com.englizya.repository.BookingInformationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingReportViewModel(
    private val bookingInformationRepository: BookingInformationRepository,
    private val dataStore: LocalTicketPreferences,

    ) : BaseViewModel() {
    private val _bookingReport = MutableLiveData<List<BookingReportResponse>>()
    val bookingReport: LiveData<List<BookingReportResponse>> = _bookingReport

    fun getBookingReport() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        bookingInformationRepository
            .getBookingReport(dataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _bookingReport.postValue(it)
            }.onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
}