package com.englizya.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.navigation.HomeActivity
import com.englizya.splash.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity() : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModel()
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        window?.statusBarColor = resources.getColor(R.color.splash_status_color)
        setUpObservers()
    }

    private fun setUpObservers() {
        splashViewModel.manifesto.observe(this){
            Log.d("ManifestoDetails" , it.isShortManifesto.toString())
            if(it.isShortManifesto == 0){
                goBooking()

            }else if(it.isShortManifesto == 1){
                goTicket()

            }
        }
    }



    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(500)
            splashViewModel.checkLoginState()
        }

        splashViewModel.loginState.observe(this) { state ->
            checkLoginState(state)
        }
    }

    private fun checkLoginState(state: Boolean) {
        if (state) {
            splashViewModel.fetchDriverManifesto()
//            goTicket()
        } else {
            goLogin()
//            goTicket()
        }
    }

    private fun goLogin() {
        val intent = Intent(this@SplashActivity, HomeActivity::class.java).putExtra(
            Arguments.Destination,
            Destination.LOGIN
        )
        startActivity(intent)
        finish()
    }

    private fun goTicket() {
        lifecycleScope.launch {
            val intent = Intent(
                this@SplashActivity,
                HomeActivity::class.java
            ).putExtra(Arguments.Destination, Destination.TICKET)
            startActivity(intent)
            finish()
        }
    }

    private fun goBooking() {
        val intent = Intent(this@SplashActivity, HomeActivity::class.java).putExtra(
            Arguments.Destination,
            Destination.LONG_TRIP_BOOKING
        )
        startActivity(intent)
        finish()
    }
}