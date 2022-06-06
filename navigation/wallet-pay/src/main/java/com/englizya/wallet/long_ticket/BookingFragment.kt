package com.englizya.wallet.long_ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.Station
import com.englizya.wallet.R
import com.englizya.wallet.WalletPaymentViewModel
import com.englizya.wallet.databinding.FragmentBookingBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BookingFragment : BaseFragment() {

    companion object {
        private const val TAG = "BookingFragment"
    }

    private lateinit var binding: FragmentBookingBinding
    private lateinit var destinationAdapter: ArrayAdapter<String>
    private lateinit var sourceAdapter: ArrayAdapter<String>
    private val walletViewModel: WalletPaymentViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBookingBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.blue_600)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch { walletViewModel.getBookingOffices() }
    }

    private fun setupListeners() {
        binding.source.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                Log.d(TAG, "setupListeners: $it")
                walletViewModel.setSource(it)
            }
        }

        binding.destination.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.adapter.getItem(i).toString().let {
                Log.d(TAG, "setupListeners: $it")
                walletViewModel.setDestination(it)
            }
        }

        binding.search.setOnClickListener {
            binding.search.isEnabled = false
            walletViewModel.requestLongTicketsWithWallet()
        }
    }

    private fun progressToSelectTrip() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.WALLET_OTP,
                null
            )
        )
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

        binding.source.setAdapter(sourceAdapter)
        binding.destination.setAdapter(destinationAdapter)
    }

    private fun setupObservers() {
        walletViewModel.source.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.source.setText(it)
            }
        }

        walletViewModel.destination.observe(viewLifecycleOwner) {
            it.branchName?.let {
                binding.destination.setText(it)
            }
        }

        walletViewModel.stations.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        walletViewModel.trip.observe(viewLifecycleOwner) {

        }

        walletViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.search.isEnabled = it
        }
    }
}