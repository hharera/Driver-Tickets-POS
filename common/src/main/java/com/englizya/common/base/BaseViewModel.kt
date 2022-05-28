package com.englizya.common.base

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.client.features.*

open class BaseViewModel : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    private val _connectivity: MutableLiveData<Boolean> = MutableLiveData(false)
    val connectivity: LiveData<Boolean> = _connectivity

    fun handleException(exception: Exception?) {
        //TODO check for every exception type print specific message
        exception?.printStackTrace()
    }

    fun updateLoading(state: Boolean) {
        _loading.postValue(state)
    }

    fun handleException(exception: Throwable?) {
        //TODO check for every exception type print specific message
        exception?.printStackTrace()
        _error.postValue(exception)
    }

    fun updateConnectivity(connectivity: Boolean) {
        _connectivity.postValue(connectivity)
    }
}