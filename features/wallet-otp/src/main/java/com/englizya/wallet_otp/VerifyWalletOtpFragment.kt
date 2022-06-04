package com.englizya.wallet_otp

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.englizya.common.base.BaseFragment
import com.englizya.wallet.WalletPaymentViewModel
import com.englizya.wallet_otp.databinding.FragmentVeifyOtpBinding

class VerifyWalletOtpFragment : BaseFragment() {

    private val walletPaymentViewModel: WalletPaymentViewModel by activityViewModels()
    private lateinit var binding: FragmentVeifyOtpBinding
    private var locationManager: LocationManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentVeifyOtpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
        setupNumberListeners()
        binding.confirmationMessage.text = getString(
            R.string.enter_confirmation_details,
            walletPaymentViewModel.total.value
        )
        locationManager =
            activity?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
        setupLocationListener()
    }

    private fun setupNumberListeners() {
        binding.zero.setOnClickListener {
            walletPaymentViewModel.putCharacter("0")
        }
        binding.one.setOnClickListener {
            walletPaymentViewModel.putCharacter("1")
        }
        binding.two.setOnClickListener {
            walletPaymentViewModel.putCharacter("2")
        }
        binding.three.setOnClickListener {
            walletPaymentViewModel.putCharacter("3")
        }
        binding.four.setOnClickListener {
            walletPaymentViewModel.putCharacter("4")
        }
        binding.five.setOnClickListener {
            walletPaymentViewModel.putCharacter("5")
        }
        binding.six.setOnClickListener {
            walletPaymentViewModel.putCharacter("6")
        }
        binding.seven.setOnClickListener {
            walletPaymentViewModel.putCharacter("7")
        }
        binding.eight.setOnClickListener {
            walletPaymentViewModel.putCharacter("8")
        }
        binding.nine.setOnClickListener {
            walletPaymentViewModel.putCharacter("9")
        }
        binding.delete.setOnClickListener {
            walletPaymentViewModel.removeCharacter()
        }
    }

    private fun setupListeners() {
        binding.pay.setOnClickListener {
            walletPaymentViewModel.whenPayClicked()
            binding.pay.isEnabled = false
        }
    }

    private fun setupLocationListener() {
        try {
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f
            ) { location ->
                walletPaymentViewModel.updateLocation(location)
            }
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    private fun setupObservers() {
        walletPaymentViewModel.verificationState.observe(viewLifecycleOwner) { verificationState ->
            if (verificationState) {
                progressToNextScreen()
            }
        }

        walletPaymentViewModel.uid.observe(viewLifecycleOwner) {

        }

        walletPaymentViewModel.walletOtp.observe(viewLifecycleOwner) {
            updateCodeUI(it)
        }

        walletPaymentViewModel.codeValidity.observe(viewLifecycleOwner) {
            binding.pay.isEnabled = it
        }

        walletPaymentViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        walletPaymentViewModel.shortTicket.observe(viewLifecycleOwner) {
            walletPaymentViewModel.printTickets(it)
        }

        walletPaymentViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }

        walletPaymentViewModel.printingOperationCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> activity?.onBackPressed()
            }
        }
    }

    private fun updateCodeUI(code: String) {
        for (index in 0..3) {
            val char = code.getOrNull(index)
            val str = char?.toString() ?: ""
            when (index) {
                0 -> binding.code1.text = str
                1 -> binding.code2.text = str
                2 -> binding.code3.text = str
                3 -> binding.code4.text = str
            }
        }
    }

    private fun progressToNextScreen() {
    }
}