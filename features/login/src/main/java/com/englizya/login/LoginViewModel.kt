package com.englizya.manager.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.Validity
import com.englizya.repository.abstraction.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    //TODO add common module dependencies
) : BaseViewModel() {

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _formValidity = MutableLiveData<LoginFormState>()
    val formValidity: LiveData<LoginFormState> = _formValidity

    private var _redirectRouting = MutableLiveData<String>(null)
    val redirectRouting: LiveData<String> = _redirectRouting

    private val _loginOperationState = MutableLiveData<Boolean>()
    val loginOperationState: LiveData<Boolean> = _loginOperationState

    private fun checkFormValidity() {
//        TODO test check validity
        if (username.value.isNullOrBlank()) {
            _formValidity.postValue(LoginFormState(usernameError = R.string.empty_username_error))
        } else if (Validity.checkUsername(username.value!!).not()) {
            _formValidity.postValue(LoginFormState(usernameError = R.string.invalid_username_error))
        } else if (password.value.isNullOrBlank()) {
            _formValidity.value = LoginFormState(passwordError = R.string.empty_password_error)
        } else if (Validity.checkPassword(password.value!!).not()) {
            _formValidity.value = LoginFormState(passwordError = R.string.invalid_password_error)
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

    fun login() {
        TODO()
//        _loading.value = true
//        usreRepository
//            .signInWithEmailAndPassword(email = username.value!!, password = password.value!!)
//            .addOnSuccessListener {
//                _loading.value = false
//                _loginOperationState.value = true
//            }
//            .addOnFailureListener {
//                _loading.value = false
//                _exception.value = it
//            }
    }

    fun setRedirectRouting(redirect: String) {
        _redirectRouting.postValue(redirect)
    }
}