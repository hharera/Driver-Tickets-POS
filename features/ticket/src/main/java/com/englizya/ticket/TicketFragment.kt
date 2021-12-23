package com.englizya.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.englizya.ticket.databinding.FragmentTicketBinding
import dagger.hilt.android.AndroidEntryPoint


//TODO rec
@AndroidEntryPoint
class TicketFragment : Fragment() {

    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var bind: FragmentTicketBinding

    private lateinit var paymentMethodsAdapter: PaymentMethodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        bind = FragmentTicketBinding.inflate(layoutInflater)
        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)

        paymentMethodsAdapter = PaymentMethodsAdapter {
            TODO("Not yet implemented")
        }

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserves()
        setupListeners()
    }

    private fun setupObserves() {
        TODO("Not yet implemented")
    }

    private fun setupListeners() {
        TODO("Not yet implemented")
    }
}
