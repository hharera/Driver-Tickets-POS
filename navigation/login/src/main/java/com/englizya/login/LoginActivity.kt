package com.englizya.login

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.login.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var bind: ActivityLoginBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        navController = Navigation.findNavController(this, R.id.nav_host)
        getExtras()
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.Destination)?.let {

            navController.navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    it,
                    false
                )
            )


        }
    }

    override fun onBackPressed() {
        if (navController.backQueue.size > 1) {
            navController.popBackStack()
        } else {
            finish()
        }
    }
}