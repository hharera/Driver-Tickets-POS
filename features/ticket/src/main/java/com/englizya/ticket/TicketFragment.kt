package com.englizya.ticket

import android.Manifest
import android.Manifest.permission
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.permission.PermissionUtils.isPermissionGranted
import com.englizya.common.utils.permission.PermissionUtils.requestPermission
import com.englizya.model.request.Ticket
import com.englizya.ticket.ticket.R
import com.englizya.ticket.ticket.databinding.FragmentTicketBinding
import com.example.paper_out_alert.PaperOutDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.Manifest.permission.ACCESS_FINE_LOCATION as ACCESS_FINE_LOCATION1

class TicketFragment : BaseFragment() {

    private val TAG = "TicketFragment"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1006

    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var bind: FragmentTicketBinding

    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter
    private val paperOutDialog: PaperOutDialog by lazy { PaperOutDialog { ticketViewModel.printTicketsInMemory() } }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        bind = FragmentTicketBinding.inflate(layoutInflater, container, false)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        ticketViewModel.setPaymentWay(getString(PaymentMethod.Cash.titleRes))
        changeStatusBarColor(R.color.white_600)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPaymentAdapter()
        setupObserves()
        setupListeners()
        checkLocationPermission()
    }

    private fun setupPaymentAdapter() {
        paymentMethodsAdapter = PaymentMethodsAdapter {
            bind.paymentMethod.text = "طريقة الدفع: ".plus(getString(it))
        }

        bind.paymentMethods.adapter = paymentMethodsAdapter
    }

    private fun setupObserves() {
        ticketViewModel.quantity.observe(viewLifecycleOwner) {
            bind.ticketQuantity.text = it.toString()
        }

        ticketViewModel.ticketCategory.observe(viewLifecycleOwner) {
            bind.ticketValue.text = getString(R.string.category).plus(" : ").plus(it)
        }

        ticketViewModel.isPaperOut.observe(viewLifecycleOwner) { isPaperOut ->
            checkPaperOutState(isPaperOut)
        }

        ticketViewModel.ticketsInMemory.observe(viewLifecycleOwner) { tickets ->
            bind.savedTickets.text = "${tickets.size}"
            checkSavedTicketsSize(tickets)
        }

        ticketViewModel.lastTicket.observe(viewLifecycleOwner) { ticket ->
            bind.lastTicketId.text = ticket.ticketId
        }

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            ticketViewModel.updateConnectivity(state)
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
        bind.ticketPlus.setOnClickListener {
            ticketViewModel.incrementQuantity()
        }

        bind.ticketMinus.setOnClickListener {
            ticketViewModel.decrementQuantity()
        }

        bind.savedTicketsTitle.setOnClickListener {
            ticketViewModel.printTicketsInMemory()
        }

        bind.print.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    bind.print.isEnabled = false
                }

                ticketViewModel.submitTickets()

                delay(500)

                withContext(Dispatchers.Main) {
                    bind.print.isEnabled = true
                }
            }
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
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
            if (isPermissionGranted(permissions, grantResults, permission.ACCESS_FINE_LOCATION).not()) {
                checkLocationPermission()
            }
        }
    }
}
