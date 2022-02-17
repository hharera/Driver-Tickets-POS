package com.englizya.end_shift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.utils.ArabicParameters
import com.englizya.ticket.end_shift.R
import com.englizya.ticket.end_shift.databinding.FragmentEndShiftBinding
import com.example.paper_out_alert.PaperOutDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EndShiftFragment : BaseFragment() {

    private lateinit var binding: FragmentEndShiftBinding
    private val endShiftViewModel: EndShiftViewModel by viewModels()

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

            endShiftViewModel.shiftReport.value?.let { report -> endShiftViewModel.printReport(report) }

            lifecycleScope.launch(Dispatchers.IO) {
                delay(500)
            }

            binding.print.isEnabled = true
        }
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