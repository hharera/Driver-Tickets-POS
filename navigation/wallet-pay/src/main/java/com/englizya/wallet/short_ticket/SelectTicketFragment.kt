package com.englizya.wallet.short_ticket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.wallet.R
import com.englizya.wallet.WalletPaymentViewModel
import com.englizya.wallet.databinding.FragmentSelectTicketBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectTicketFragment : BaseFragment() {

    companion object {
        private const val TAG = "TicketFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1006
    }

    private val walletPaymentViewModel: WalletPaymentViewModel by sharedViewModel()
    private lateinit var binding: FragmentSelectTicketBinding
    private var categoriesAdapter: CategoriesAdapter? = null

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
        walletPaymentViewModel.quantity.observe(viewLifecycleOwner) { quntity ->
            binding.ticketQuantity.text = "$quntity"

            walletPaymentViewModel.selectedCategory.value?.let { ticketPrice ->
                binding.total.text = "اجمالي: ${quntity * ticketPrice}"
            }
        }

        walletPaymentViewModel.ticketCategory.observe(viewLifecycleOwner) {
            categoriesAdapter?.setSelectedCategory(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) { state ->
            walletPaymentViewModel.updateConnectivity(state)
        }

        walletPaymentViewModel.selectedCategory.observe(viewLifecycleOwner) {
            updateCategories(it)
        }

        walletPaymentViewModel.ticketCategories.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        walletPaymentViewModel.total.observe(viewLifecycleOwner) {
            updateTotal(it)
        }
    }

    private fun updateTotal(it: Int) {
        binding.total.text = "اجمالي: $it"
    }

    private fun updateUI(it: Set<String>) {
        setupCategoriesAdapter((it.map { it.toInt() }).toList())
    }

    private fun updateCategories(it: Int) {
        categoriesAdapter?.setSelectedCategory(it)
    }

    private fun setupListeners() {
        binding.ticketPlus.setOnClickListener {
            walletPaymentViewModel.incrementQuantity()
        }

        binding.ticketMinus.setOnClickListener {
            walletPaymentViewModel.decrementQuantity()
        }

        binding.proceed.setOnClickListener {
//            navigateToSetWalletOtp()
//            walletPaymentViewModel.whenPayClicked()
//            Toast.makeText(activity , "تم طباعة التذاكر" , Toast.LENGTH_LONG).show()
//            activity ?.let{
//                val intent = Intent (it, HomeActivity::class.java)
//                it.startActivity(intent)
//            }
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
