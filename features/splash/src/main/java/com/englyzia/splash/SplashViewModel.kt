package com.englyzia.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.repository.abstraction.UserRepository
import com.englyzia.datastore.base.GeneralConstants.NULL_STRING
import com.englyzia.datastore.core.DriverDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userDataStore: DriverDataStore,
    //TODO add common module dependencies
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun checkLoginState() {
        _loginState.postValue(userDataStore.getDriverUsername() != NULL_STRING)
    }

}