package com.englyzia.navigation

import android.os.Bundle
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englyzia.navigation.databinding.ActivityHomeBinding

//TODO extend from base activity
class HomeActivity : BaseActivity() {

    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val navController = Navigation.findNavController(this, R.id.nav_host)
        intent?.extras?.getString(Arguments.Destination)?.let {
            navController.navigate(NavigationUtils.getUriNavigation(Domain.ENGLIZYA_PAY, it, null))
        }
    }
}