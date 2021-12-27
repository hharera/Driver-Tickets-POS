package com.englizya.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(

) : BaseViewModel() {

    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity


    fun decrementQuantity() {
        _quantity.postValue(_quantity.value!! - 1)
    }

    fun incrementQuantity() {
        _quantity.postValue(_quantity.value!! + 1)
    }

}
