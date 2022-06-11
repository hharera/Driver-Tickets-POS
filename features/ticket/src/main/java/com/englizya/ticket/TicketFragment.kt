package com.englizya.ticket

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.ui.PaperOutDialog
import com.englizya.common.utils.permission.PermissionUtils.isPermissionGranted
import com.englizya.common.utils.permission.PermissionUtils.requestPermission
import com.englizya.model.request.Ticket
import com.englizya.ticket.ticket.R
import com.englizya.ticket.ticket.databinding.FragmentTicketBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import android.Manifest.permission.ACCESS_FINE_LOCATION as ACCESS_FINE_LOCATION1

class TicketFragment : BaseFragment() {

    companion object {
        private const val TAG = "TicketFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1006
    }

    private val ticketViewModel: TicketViewModel by sharedViewModel()
    private lateinit var binding: FragmentTicketBinding

    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter
    private var categoriesAdapter: CategoriesAdapter? = null
    private val paperOutDialog: PaperOutDialog by lazy { PaperOutDialog { ticketViewModel.printTicketsInMemory() } }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTicketBinding.inflate(layoutInflater, container, false)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        ticketViewModel.setPaymentMethod(PaymentMethod.Cash)
        changeStatusBarColor(R.color.white_600)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserves()
        setupListeners()
        checkLocationPermission()
    }

    private fun setupPaymentAdapter(method: PaymentMethod) {
        paymentMethodsAdapter = PaymentMethodsAdapter {
            ticketViewModel.setPaymentMethod(it)
        }

        binding.paymentMethods.adapter = paymentMethodsAdapter
    }

    private fun setupCategoriesAdapter(categoryList: List<Int>) {
        categoriesAdapter = categoryList.firstOrNull()?.let {
            CategoriesAdapter(it, categoryList) {
                ticketViewModel.setSelectedCategory(it)
            }.also {
                binding.categories.adapter = it
            }
        }

    }

    private fun setupObserves() {
        ticketViewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            updateTotal(quantity)
            updateQuantity(quantity)
        }

        ticketViewModel.isPaperOut.observe(viewLifecycleOwner) { isPaperOut ->
            checkPaperOutState(isPaperOut)
        }

        ticketViewModel.ticketsInMemory.observe(viewLifecycleOwner) { tickets ->
            checkSavedTicketsSize(tickets)
        }

        ticketViewModel.ticketCategories.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        ticketViewModel.lastTicket.observe(requireActivity()) {
        }

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            ticketViewModel.updateConnectivity(state)
        }

        ticketViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            updatePaymentMethods(method)
        }

        ticketViewModel.selectedCategory.observe(viewLifecycleOwner) {
            updateCategories(it)
        }

        ticketViewModel.connectivity.observe(viewLifecycleOwner) {
            ticketViewModel.updateConnectivity(it)
        }


        ticketViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun updateQuantity(quantity: Int) {
        binding.ticketQuantity.text = "$quantity"
    }

    private fun updateTotal(quantity: Int) {
        ticketViewModel.selectedCategory.value?.let { price ->
            binding.total.text = "اجمالي: ${price * quantity}"
        }
    }

    private fun updateUI(it: Set<String>) {
        setupCategoriesAdapter(it.map { it.toInt() }.toList())
    }

    private fun updateCategories(it: Int?) {
        it?.let { it1 -> categoriesAdapter?.setSelectedCategory(it1) }
    }

    private fun updatePaymentMethods(method: PaymentMethod) {
        setupPaymentAdapter(method)
        method?.let{
            method1 -> paymentMethodsAdapter?.setSelectedPaymentMethod(method1)
        }
      //  setupPaymentAdapter(method)

        if (method == PaymentMethod.QR) {
            navigateToScanWalletQr()
        }
    }

    private fun navigateToScanWalletQr() {
        Class.forName("com.englizya.wallet.WalletPayActivity").let {
            startActivity(Intent(context, it))
        }
    }

    private fun checkSavedTicketsSize(tickets: Set<Ticket>) {
        if (tickets.isEmpty()) {
            runCatching {
                paperOutDialog.dismiss()
            }
        } else
            showPaperOutDialog()
    }

    private fun checkPaperOutState(paperOut: Boolean) {
        if (paperOut) {
//            showPaperOutDialog()
        }
    }

    private fun showPaperOutDialog() {
        paperOutDialog.show(childFragmentManager, TAG)
    }

    private fun setupListeners() {
        binding.ticketPlus.setOnClickListener {
            ticketViewModel.incrementQuantity()
        }

        binding.ticketMinus.setOnClickListener {
            ticketViewModel.decrementQuantity()
        }

//        bind.savedTicketsTitle.setOnClickListener {
//            ticketViewModel.printTicketsInMemory()
//        }


        binding.print.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    binding.print.isEnabled = false
                }

                ticketViewModel.submitTickets()

                delay(500)

                withContext(Dispatchers.Main) {
                    binding.print.isEnabled = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ticketViewModel.setPaymentMethod(PaymentMethod.Cash)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            requestPermission(
                requireActivity() as AppCompatActivity,
                LOCATION_PERMISSION_REQUEST_CODE,
                ACCESS_FINE_LOCATION1,
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
            if (isPermissionGranted(
                    permissions,
                    grantResults,
                    permission.ACCESS_FINE_LOCATION
                ).not()
            ) {
                checkLocationPermission()
            }
        }
    }
}
