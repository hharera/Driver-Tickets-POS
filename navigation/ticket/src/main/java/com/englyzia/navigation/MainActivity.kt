package com.englyzia.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import com.englizya.common.base.BaseActivity
import com.englyzia.navigation.databinding.ActivityMainBinding

//TODO extend from base activity
class MainActivity : BaseActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val navController = Navigation.findNavController(this, R.id.nav_host)
    }
}