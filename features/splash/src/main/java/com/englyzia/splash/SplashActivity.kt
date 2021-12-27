package com.englyzia.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englyzia.navigation.HomeActivity
import com.englyzia.splash.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)

        window?.statusBarColor = resources.getColor(R.color.splash_status_color)

        //TODO test
        lifecycleScope.launch {
            delay(1000)
            goTicket()
        }
    }


    override fun onResume() {
        super.onResume()

        splashViewModel.checkLoginState()
        splashViewModel.loginState.observe(this) { state ->
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
            val intent = Intent(this@SplashActivity, HomeActivity::class.java).putExtra(Arguments.Destination, Destination.TICKET)
            startActivity(intent)
            finish()
        }
    }
}