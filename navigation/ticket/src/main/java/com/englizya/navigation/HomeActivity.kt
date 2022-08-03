package com.englizya.navigation

import android.app.Activity
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.longtripbooking.LongTripBookingViewModel
import com.englizya.ticket.TicketViewModel
import com.englizya.ticket.navigation.R
import com.englizya.ticket.navigation.databinding.ActivityHomeBinding
import com.google.android.gms.location.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


//TODO extend from base activity
class HomeActivity : BaseActivity() {

    private lateinit var bind: ActivityHomeBinding
    private val ticketViewModel: TicketViewModel by viewModel()

    private lateinit var navController: NavController
    private val TAG = "HomeActivity"
    private lateinit var locationCallback: LocationCallback
    private var locationManager: LocationManager? = null

    private val userDataStore: LocalTicketPreferences by inject()
    private var manifestoType: Int? =  null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        manifestoType = userDataStore.getManifestoType()
        navController = Navigation.findNavController(this, R.id.nav_host)
        bind.navView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bind.navView, navController)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        setupLocationListener()
        turnGPSOn()

        getExtras()
        getLocation()
    }

    private fun checkGPS() {
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            turnGPSOn()
        } else {
            Log.d(TAG, "checkGPS: GPS is enabled")
        }
    }

    private fun requestLocation() {
        LocationSettingsRequest
            .Builder()
            .addLocationRequest(
                LocationRequest
                    .create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            )
            .build()
    }

    private fun setupLocationListener() {
        try {
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f
            ) { location ->
                ticketViewModel.updateLocation(location)
            }
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    Log.d(TAG, "onLocationResult: $location")
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )

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

                        navController.navigate(
                            NavigationUtils.getUriNavigation(
                                Domain.ENGLIZYA_PAY,
                                Destination.END_SHIFT,
                                false
                            )
                        )
                        bind.root.closeDrawer(GravityCompat.END, true)


                }
                R.id.navigation_scan_payed_ticket -> {
                    navController.navigate(
                        NavigationUtils.getUriNavigation(
                            Domain.ENGLIZYA_PAY,
                            Destination.SCAN_QR,
                            false
                        )
                    )
                    bind.root.closeDrawer(GravityCompat.END, true)


                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationRequest.PRIORITY_HIGH_ACCURACY)
            when (resultCode) {
                Activity.RESULT_OK -> {
                    setupLocationListener()
                }

                Activity.RESULT_CANCELED -> {
                    requestLocation()
                }
            }
    }

    private fun turnGPSOn() {
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
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