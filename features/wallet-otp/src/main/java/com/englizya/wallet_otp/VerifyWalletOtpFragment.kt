package com.englizya.wallet_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.wallet.WalletPaymentViewModel
import com.englizya.wallet_otp.databinding.FragmentSendOtpBinding

class VerifyWalletOtpFragment : BaseFragment() {

    private val verifyWalletOtpViewModel: WalletPaymentViewModel by activityViewModels()
    private lateinit var bind: FragmentSendOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentSendOtpBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
        setupNumberListeners()
    }

    private fun setupNumberListeners() {
        bind.zero.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("0")
        }
        bind.one.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("1")
        }
        bind.two.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("2")
        }
        bind.three.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("3")
        }
        bind.four.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("4")
        }
        bind.five.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("5")
        }
        bind.six.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("6")
        }
        bind.seven.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("7")
        }
        bind.eight.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("8")
        }
        bind.nine.setOnClickListener {
            verifyWalletOtpViewModel.putCharacter("9")
        }
        bind.delete.setOnClickListener {
            verifyWalletOtpViewModel.removeCharacter()
        }
    }

    private fun setupListeners() {
        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.pay.setOnClickListener {
            verifyWalletOtpViewModel.signup()
        }
    }

    private fun setupObservers() {
        verifyWalletOtpViewModel.verificationState.observe(viewLifecycleOwner) { verificationState ->
            if (verificationState) {
                progressToNextScreen()
            }
        }

        verifyWalletOtpViewModel.uid.observe(viewLifecycleOwner) {

        }

        verifyWalletOtpViewModel.code.observe(viewLifecycleOwner) {
            updateCodeUI(it)
        }

        verifyWalletOtpViewModel.codeValidity.observe(viewLifecycleOwner) {
            bind.pay.isEnabled = it
        }
    }

    private fun updateCodeUI(code: String) {
        for (index in 1..4) {
            val char = code.getOrNull(index)
            val str = char?.toString() ?: ""
            when (index) {
                1 -> bind.code1.text = str
                2 -> bind.code2.text = str
                3 -> bind.code3.text = str
                4 -> bind.code4.text = str
            }
        }
    }

    private fun progressToNextScreen() {
//        TODO :
    }
}