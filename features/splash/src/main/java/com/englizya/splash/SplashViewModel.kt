package com.englizya.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.repository.UserRepository

class SplashViewModel constructor(
    private val userRepository: UserRepository,
    private val userDataStore: LocalTicketPreferences,
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun checkLoginState() {
        _loginState.postValue(userDataStore.getToken() != NULL_STRING)
    }

}