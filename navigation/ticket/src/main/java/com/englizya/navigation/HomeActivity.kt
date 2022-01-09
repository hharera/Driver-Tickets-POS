package com.englizya.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.ticket.navigation.R
import com.englizya.ticket.navigation.databinding.ActivityHomeBinding

//TODO extend from base activity
class HomeActivity : BaseActivity() {

    private lateinit var bind: ActivityHomeBinding
    private lateinit var navController: NavController
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = Navigation.findNavController(this, R.id.nav_host)
        bind.navView.setupWithNavController(navController)

        getExtras()
    }

    private fun getExtras() {
        intent?.extras?.getString(Arguments.Destination)?.let {
            navController.navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, it, null))
        }
    }


    override fun onResume() {
        super.onResume()

        setupListeners()
    }

    private fun setupListeners() {
        listenToNavigation()

    }

    private fun listenToNavigation() {
        bind.navView.bringToFront()
        bind.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_end_shift -> {
                    navController.navigate(R.id.navigation_end_shift)
                }

                R.id.navigation_end_shift -> {
                    navController.navigate(R.id.navigation_day_report)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
}