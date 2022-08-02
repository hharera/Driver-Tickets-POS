package com.englizya.end_shift

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.response.ShiftReportResponse
import com.englizya.ticket.end_shift.R
import com.englizya.ticket.end_shift.databinding.FragmentEndShiftBinding
import com.englizya.common.ui.PaperOutDialog
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndShiftFragment : BaseFragment() {

    private lateinit var binding: FragmentEndShiftBinding
    private val endShiftViewModel: EndShiftViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentEndShiftBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.print.setOnClickListener {
            binding.print.isEnabled = false

            endShiftViewModel.shiftReport.value?.let {
                    report ->
                endShiftViewModel.printReport(
                    report
                )
            }.also {
            endShiftViewModel.logout()
            navigateToLogin()
                }


            lifecycleScope.launch(Dispatchers.IO) {
                delay(500)
            }

            binding.print.isEnabled = true
        }
    }

    private fun navigateToLogin() {
        Log.d("Navigate To Login ", " I am navigating")
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.LOGIN,
false            )
        )
    }

    private fun setupObservers() {
        endShiftViewModel.shiftReport.observe(viewLifecycleOwner) { shiftReport ->
            updateUI(shiftReport)
        }

        endShiftViewModel.isPaperOut.observe(viewLifecycleOwner) { paperOutState ->
            checkPaperOutState(paperOutState)
        }

        connectionLiveData.observe(viewLifecycleOwner) { connected ->
            lifecycleScope.launch(Dispatchers.IO) {
                endShiftViewModel.endShift()
            }

            if (connected.not()) {
                showToast(R.string.check_your_internet)
            }
        }
    }

    private fun checkPaperOutState(paperOutState: Boolean) {
        if (paperOutState) {
            showPaperOutDialog()
        }
    }

    private fun showPaperOutDialog() {
        PaperOutDialog {
            endShiftViewModel.shiftReport.value?.let { endShiftViewModel.printReport(it) }
        }.show(childFragmentManager, "")
    }

    private fun updateUI(shiftReport: ShiftReportResponse) {
        binding.apply {
            carCode.text = shiftReport.carCode
            driverCode.text = shiftReport.driverCode.toString()
            lineCode.text = shiftReport.lineCode
            date.text = shiftReport.date
            shiftStart.text = shiftReport.startTime
            shiftEnd.text = shiftReport.endTime
            workHours.text = TimeUtils.calculateWorkHours(shiftReport)
            cashTickets.text = shiftReport.cash.toString()
            qrTickets.text = shiftReport.qr.toString()
            nfcTickets.text = shiftReport.card.toString()
            totalTickets.text = shiftReport.totalTickets.toString()
            ticketPrice.text = shiftReport.ticketCategory.toString()
            total.text = shiftReport.totalIncome.toString()
        }
    }
}