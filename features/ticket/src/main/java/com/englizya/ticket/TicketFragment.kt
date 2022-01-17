package com.englizya.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.ticket.ticket.R
import com.englizya.ticket.ticket.databinding.FragmentTicketBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TicketFragment : BaseFragment() {

    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var bind: FragmentTicketBinding

    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        bind = FragmentTicketBinding.inflate(layoutInflater, container, false)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        ticketViewModel.setPaymentWay(getString(PaymentMethod.Cash.titleRes))
        changeStatusBarColor(R.color.white_600)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPaymentAdapter()
        setupObserves()
        setupListeners()
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

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            ticketViewModel.updateConnectivity(state)
        }
    }

    private fun setupListeners() {
        bind.ticketPlus.setOnClickListener {
            ticketViewModel.incrementQuantity()
        }

        bind.ticketMinus.setOnClickListener {
            ticketViewModel.decrementQuantity()
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
}
