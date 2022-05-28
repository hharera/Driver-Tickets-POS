package com.englizya.select_ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.response.ManifestoDetails
import com.englizya.select_ticket.databinding.FragmentSelectTicketBinding
import com.englizya.wallet.WalletPaymentViewModel

class SelectTicketFragment : BaseFragment() {

    companion object {
        private const val TAG = "TicketFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1006
    }

    private val walletPaymentViewModel: WalletPaymentViewModel by activityViewModels()
    private lateinit var binding: FragmentSelectTicketBinding
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSelectTicketBinding.inflate(layoutInflater, container, false)

        changeStatusBarColor(R.color.white_600)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walletPaymentViewModel.getTicketCategories()
        setupObserves()
        setupListeners()
    }

    private fun setupCategoriesAdapter(categoryList: List<Int>) {
        categoriesAdapter = CategoriesAdapter(categoryList.first(), categoryList) {
            walletPaymentViewModel.setSelectedCategory(it)
        }

        binding.categories.adapter = categoriesAdapter
    }

    private fun setupObserves() {
        walletPaymentViewModel.quantity.observe(viewLifecycleOwner) {
            binding.ticketQuantity.text = "$it"
            binding.total.text = "${it * walletPaymentViewModel.selectedCategory.value!!}"
        }

        walletPaymentViewModel.ticketCategory.observe(viewLifecycleOwner) {
            categoriesAdapter.setSelectedCategory(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            walletPaymentViewModel.updateConnectivity(state)
        }

        walletPaymentViewModel.selectedCategory.observe(viewLifecycleOwner) {
            updateCategories(it)
        }

        walletPaymentViewModel.manifesto.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(it: ManifestoDetails) {
        setupCategoriesAdapter(listOf(it.ticketCategory))
    }

    private fun updateCategories(it: Int) {
        categoriesAdapter.setSelectedCategory(it)
    }

    private fun setupListeners() {
        binding.ticketPlus.setOnClickListener {
            walletPaymentViewModel.incrementQuantity()
        }

        binding.ticketMinus.setOnClickListener {
            walletPaymentViewModel.decrementQuantity()
        }

        binding.proceed.setOnClickListener {
            navigateToSetWalletOtp()
        }
    }

    private fun navigateToSetWalletOtp() {
        findNavController()
            .navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.WALLET_OTP,
                    null
                )
            )
    }
}
