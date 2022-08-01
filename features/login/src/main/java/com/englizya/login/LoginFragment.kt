package com.englizya.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.ticket.login.R
import com.englizya.ticket.login.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var bind: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            it.getString(Arguments.REDIRECT)?.let { redirect ->
//                loginViewModel.setRedirectRouting(redirect)
//            }
//        }

        changeStatusBarColor(R.color.blue_600)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentLoginBinding.inflate(layoutInflater)

        return bind.root
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        bind.password.setText(loginViewModel.password.value)
        bind.username.setText(loginViewModel.username.value)

        loginViewModel.loading.observe(this) {
            handleLoading(it)
        }

        loginViewModel.error.observe(this) {
            handleFailure(it)
        }

//        loginViewModel.loginOperationState.observe(this) { state ->
//            checkLoginState(state)
//        }
        loginViewModel.manifesto.observe(this) {
            Log.d("ManifestoDetails",it.toString())
            if (it.isShortManifesto == 0) {
                goBooking()
            } else {
                goTicket()
            }
        }

        loginViewModel.formValidity.observe(this) {
            bind.login.isEnabled = it.isValid

            if (it.usernameError != null) {
                bind.username.error = getString(it.usernameError!!)
            } else if (it.passwordError != null) {
                bind.password.error = getString(it.passwordError!!)
            }
        }
    }

//    private fun checkLoginState(state: Boolean) {
//        if (state) {
//            redirect()
//        }
//    }

    private fun goBooking() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.LONG_TRIP_BOOKING,
                false
            )
        )
    }

    private fun goTicket() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.TICKET,
                false
            )
        )
    }

    private fun redirect() {
        val redirectDestination = loginViewModel.redirectRouting.value

        if (redirectDestination == null) {
            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.TICKET,
                    null
                )
            )
            return
        }

        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                redirectDestination,
                null
            )
        )
    }

    private fun setupListeners() {
        bind.username.afterTextChanged {
            loginViewModel.setUsername(it)
        }

        bind.password.afterTextChanged {
            loginViewModel.setPassword(it)
        }

        bind.login.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                loginViewModel.login()
            }
            bind.login.isEnabled = false
        }
    }
}