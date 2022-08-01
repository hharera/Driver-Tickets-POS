package com.englizya.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.model.response.ManifestoDetails
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel constructor(
    private val userDataStore: LocalTicketPreferences,
    private val manifestoRepository: ManifestoRepository,

    ) : BaseViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    fun checkLoginState() {
        _loginState.postValue(userDataStore.getToken() != NULL_STRING)
    }
     fun fetchDriverManifesto() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        manifestoRepository
            .getManifesto(userDataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _manifesto.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

}