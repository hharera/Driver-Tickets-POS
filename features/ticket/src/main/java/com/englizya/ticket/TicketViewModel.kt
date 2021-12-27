package com.englizya.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Integer.max
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(

) : BaseViewModel() {

    private var _quantity = MutableLiveData<Int>(1)
    val quantity: LiveData<Int> = _quantity


    fun decrementQuantity() {
        _quantity.postValue(
            Math.max(_quantity.value!! - 1, 1)
        )
    }

    fun incrementQuantity() {
        _quantity.postValue(
            Math.min(_quantity.value!! + 1, 99)
        )
    }

    fun setQuantity(quantity: Int) {
        _quantity.postValue(min(max(1, quantity), 99))
    }

}
