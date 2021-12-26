package com.englyzia.splash

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englyzia.splash.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var bind: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = FragmentSplashBinding.inflate(layoutInflater)

        splashViewModel.checkLoginState()
        splashViewModel.loginState.observe(viewLifecycleOwner) { state ->
            checkLoginState(state)
        }
    }

    private fun checkLoginState(state: Boolean) {
        if (state) {
            goTicket()
        } else {
            goLogin()
        }
    }

    private fun goLogin() {

    }

    private fun goTicket() {
        lifecycleScope.launch {
            val anim = AnimationUtils.loadAnimation(context, R.anim.fragment_splash_out)
            bind.imageView.startAnimation(anim)
            findNavController().navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY,
                Destination.TICKET))
        }
    }

}