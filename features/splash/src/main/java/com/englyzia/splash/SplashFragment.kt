package com.englyzia.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englyzia.splash.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var bind: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bind = FragmentSplashBinding.inflate(layoutInflater)

        //TODO test
        lifecycleScope.launch {
            delay(1000)
            goTicket()
        }

//        TODO
        activity?.window?.statusBarColor = resources.getColor(R.color.splash_status_color)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
//            val anim = AnimationUtils.loadAnimation(context, R.anim.fragment_splash_out)
//            val inAnim = AnimationUtils.loadAnimation(context, R.anim.fragment_login_in)
//            bind.imageView.startAnimation(anim)
//            bind.appName.startAnimation(anim)
//            bind.imageBackground.startAnimation(inAnim)
            delay(300)
            findNavController().navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY,
                Destination.TICKET))
        }
    }

}