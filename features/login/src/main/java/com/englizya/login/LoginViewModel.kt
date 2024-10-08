package com.englizya.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.datastore.utils.Value
import com.englizya.manager.login.LoginFormState
import com.englizya.model.request.LoginRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.repository.ManifestoRepository
import com.englizya.repository.UserRepository
import com.englizya.ticket.login.R

class LoginViewModel constructor(
    private val userRepository: UserRepository,
    private val manifestoRepository: ManifestoRepository,
    private val preferences: LocalTicketPreferences,
) : BaseViewModel() {

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<LoginFormState>()
    val formValidity: LiveData<LoginFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>(null)
    val redirectRouting: LiveData<String> = _redirectRouting

    private var _manifesto = MutableLiveData<ManifestoDetails>()
    val manifesto: LiveData<ManifestoDetails> = _manifesto

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private fun checkFormValidity() {
        if (username.value.isNullOrBlank()) {
            _formValidity.postValue(LoginFormState(usernameError = R.string.empty_username_error))
        } else if (Validity.checkUsername(username.value!!).not()) {
            _formValidity.postValue(LoginFormState(usernameError = R.string.invalid_username_error))
        } else if (password.value.isNullOrBlank()) {
            _formValidity.value =
                LoginFormState(passwordError = R.string.empty_password_error)
        } else if (Validity.checkDriverPassword(password.value!!).not()) {
            _formValidity.value =
                LoginFormState(passwordError = R.string.invalid_password_error)
        } else {
            _formValidity.value = LoginFormState(isValid = true)
        }
    }

    fun setUsername(it: String) {
        _username.value = it
        checkFormValidity()
    }

    fun setPassword(it: String) {
        _password.value = it
        checkFormValidity()
    }

    suspend fun login() {
        updateLoading(true)
        userRepository
            .login(LoginRequest(username.value!!.toInt(), password.value!!))
            .onSuccess {
                updateLoading(false)
                preferences.setToken(it.jwt)
                getDriverDetails()
            }
            .onFailure {
                preferences.setToken(Value.NULL_STRING)
                updateLoading(false)
                _loginOperationState.postValue(false)
                handleException(it)
            }
    }


    private suspend fun getDriverDetails() {
        updateLoading(true)
        manifestoRepository
            .getManifesto()
            .onSuccess {
                updateLoading(false)
                updateLocalData(it)
                _loginOperationState.postValue(true)
                _manifesto.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                _loginOperationState.postValue(false)
                preferences.setToken(Value.NULL_STRING)
                handleException(it)
            }
    }

    private fun updateLocalData(manifesto: ManifestoDetails) {
        preferences.setManifestoDate(manifesto.date)
        preferences.setIsManifestoShort(manifesto.isShortManifesto)
        preferences.setManifestoNo(manifesto.manifestoId)
        preferences.setManifestoYear(manifesto.year)
        preferences.setManifestoType(manifesto.isShortManifesto)

        preferences.setDriverCode(manifesto.driverCode)
        preferences.setReservationId(manifesto.reservationId)
        preferences.setTripId(manifesto.tripId)
        preferences.setCarCode(manifesto.carCode)
        preferences.setCarLineCode(manifesto.lineCode)
        preferences.setTicketCategories(
            manifesto.lineCategory.map { it.toString() }
                .toSet())
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }
}