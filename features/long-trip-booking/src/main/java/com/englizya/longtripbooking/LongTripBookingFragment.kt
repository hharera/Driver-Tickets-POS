package com.englizya.longtripbooking

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.permission.PermissionUtils
import com.englizya.longtripbooking.databinding.FragmentLongTripBookingBinding
import com.englizya.longtripbooking.di.longTripBookingModule
import com.englizya.model.Station
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LongTripBookingFragment : BaseFragment() {
    private val bookingViewModel: LongTripBookingViewModel by sharedViewModel()
    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter
    private lateinit var destinationAdapter: ArrayAdapter<String>
    private lateinit var sourceAdapter: ArrayAdapter<String>

    companion object {
        private const val TAG = "LongTripBookingFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1006
    }
    private lateinit var binding: FragmentLongTripBookingBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLongTripBookingBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.blue_600)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        bookingViewModel.setPaymentMethod(PaymentMethod.Cash)

        return binding.root
    }
    private fun setupPaymentAdapter(method: PaymentMethod) {
        paymentMethodsAdapter = PaymentMethodsAdapter {
            bookingViewModel.setPaymentMethod(it)
        }

        binding.paymentMethods.adapter = paymentMethodsAdapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setupListeners()
        checkLocationPermission()

    }
    private fun updateUI(it: List<Station>) {
        sourceAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_station,
            R.id.station,
            it.map { it.branchName }
        )

        destinationAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.card_view_station,
            R.id.station,
            it.map { it.branchName }
        )

        binding.fromConstraintLayout.setOnClickListener {
            bookingViewModel.getStations()
            showSourceMenu(it)
        }

        binding.constraintLayout.setOnClickListener {
            bookingViewModel.getStations()
            showDestinationMenu(it)
        }
    }

    private fun showDestinationMenu(view: View) {
        ListPopupWindow(context!!).apply {
            setAdapter(destinationAdapter)
            anchorView = view

            setOnItemClickListener { adapterView, view, i, l ->
                adapterView.adapter.getItem(i).toString().let {
                    bookingViewModel.setDestination(it)
                }
                dismiss()
            }

            show()
        }
    }
    private fun showSourceMenu(view: View) {
        ListPopupWindow(context!!).apply {
            setAdapter(destinationAdapter)
            anchorView = view

            setOnItemClickListener { adapterView, view, i, l ->
                adapterView.adapter.getItem(i).toString().let {
                    bookingViewModel.setSource(it)
                }
                dismiss()
            }

            show()
        }
    }
    private fun setupListeners() {
        binding.ticketPlus.setOnClickListener {
            bookingViewModel.incrementQuantity()
        }

        binding.ticketMinus.setOnClickListener {
            bookingViewModel.decrementQuantity()
        }
        binding.print.setOnClickListener {
            if (bookingViewModel.paymentMethod.value == PaymentMethod.Cash) {
                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        binding.print.isEnabled = false
                    }

                    bookingViewModel.submitTickets()

                    delay(500)

                    withContext(Dispatchers.Main) {
                        binding.print.isEnabled = true
                    }
                }
            }
        }
    }
    private fun setUpObservers() {
        bookingViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            updatePaymentMethods(method)
        }
        bookingViewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            updateTotal(quantity)
            updateQuantity(quantity)
        }
        bookingViewModel.stations.observe(viewLifecycleOwner){
            updateUI(it)
        }
        bookingViewModel.source.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.source.setText(it)
            }
        }

        bookingViewModel.destination.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.destination.setText(it)
            }
        }
    }
    private fun updateQuantity(quantity: Int) {
        binding.ticketQuantity.text = "$quantity"
    }
    override fun onResume() {
        super.onResume()

        bookingViewModel.getBookingOffices()
        bookingViewModel.setPaymentMethod(PaymentMethod.Cash)

    }
    private fun updateTotal(quantity: Int) {
//        bookingViewModel.selectedCategory.value?.let { price ->
//            binding.total.text = "اجمالي: ${price * quantity}"
//        }
    }
    private fun updatePaymentMethods(method: PaymentMethod) {
        setupPaymentAdapter(method)
        method.let{
                method1 -> paymentMethodsAdapter.setSelectedPaymentMethod(method1)
        }
        //  setupPaymentAdapter(method)

//        if (method == PaymentMethod.QR) {
//            ticketViewModel.selectedCategory.value?.let {
//                ticketViewModel.quantity.value?.let { it1 ->
//                    navigateToScanWalletQr(
//                        quantity = it1,
//                        category = it
//                    )
//                }
//            }
//        }
    }
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PermissionUtils.requestPermission(
                requireActivity() as AppCompatActivity,
                LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                false
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(
                    permissions,
                    grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ).not()
            ) {
                checkLocationPermission()
            }
        }
    }
}