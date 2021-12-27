package com.englizya.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.englizya.common.base.BaseFragment
import com.englizya.ticket.databinding.FragmentTicketBinding


//TODO rec
class TicketFragment : BaseFragment() {

    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var bind: FragmentTicketBinding

    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        bind = FragmentTicketBinding.inflate(layoutInflater)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)


        activity?.window?.statusBarColor = resources.getColor(R.color.ticket_status_color)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserves()
        setupPaymentAdapter()
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
    }

    private fun setupListeners() {
        bind.ticketPlus.setOnClickListener {
            ticketViewModel.incrementQuantity()
        }

        bind.ticketMinus.setOnClickListener {
            ticketViewModel.decrementQuantity()
        }
    }
}
