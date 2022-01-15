package com.englizya.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.datastore.core.DriverDataStore
import com.englizya.repository.UserRepository
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
        _loginState.postValue(userDataStore.getToken() != NULL_STRING)
    }

}