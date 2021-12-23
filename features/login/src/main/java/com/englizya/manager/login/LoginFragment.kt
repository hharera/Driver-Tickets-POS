package com.englizya.manager.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.englizya.common.afterTextChanged
import com.englizya.common.base.BaseActivity
import com.englizya.manager.login.databinding.ActivityLoginBinding
import com.englizya.manager_navigation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        bind.password.setText(loginViewModel.password.value)

        bind.email.setText(loginViewModel.email.value)

        loginViewModel.loading.observe(this) {
            handleLoading(it)
        }

        loginViewModel.exception.observe(this) {
            handleFailure(exception = it)
        }

        loginViewModel.loginSuccess.observe(this) {
            goToHomeActivity()
        }

        loginViewModel.formValidity.observe(this) {
            bind.login.isEnabled = it.isValid

            if (it.emailError != null) {
                bind.email.error = getString(it.emailError!!)
            } else if (it.passwordError != null) {
                bind.password.error = getString(it.passwordError!!)
            }
        }
    }

    private fun goToHomeActivity() {
        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            )
        ).also {
            finish()
        }
    }

    private fun setupListeners() {
        bind.email.afterTextChanged {
            loginViewModel.setEmail(it)
        }

        bind.password.afterTextChanged {
            loginViewModel.setPassword(it)
        }

        bind.login.setOnClickListener {
            loginViewModel.login()
            bind.login.isEnabled = false
        }
    }
}