package com.englizya.scan_qr

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ScanWalletViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "ScanWalletViewModel"
    }

    private var _qrContent = MutableLiveData<String>()
    val qrContent: LiveData<String> = _qrContent

    fun setQrContent(contents: String) {
        Log.d(TAG, "setQrContent: $contents")
        _qrContent.postValue(contents)
    }
}