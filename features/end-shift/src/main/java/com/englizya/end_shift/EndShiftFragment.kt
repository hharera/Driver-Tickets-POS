package com.englizya.end_shift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.response.ShiftReportResponse
import com.englizya.ticket.end_shift.R
import com.englizya.ticket.end_shift.databinding.FragmentEndShiftBinding
import kotlinx.coroutines.Dispatchers
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
            endShiftViewModel.shiftReport.value?.let { report -> endShiftViewModel.printReport(report) }
        }
    }

    private fun setupObservers() {
        endShiftViewModel.shiftReport.observe(viewLifecycleOwner) { shiftReport ->
            updateUI(shiftReport)
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

    private fun updateUI(shiftReport: ShiftReportResponse) {
        binding.apply {
            carCode.text = shiftReport.carCode
            driverCode.text = shiftReport.driverCode.toString()
            lineCode.text = shiftReport.lineCode
            date.text = shiftReport.date
            shiftStart.text = shiftReport.startTime.toString()
            shiftEnd.text = shiftReport.endTime.toString()
//            workHours.text = TimeUtils.calculateWorkHours(endTime = shiftReport.endTime, startTime = shiftReport.startTime)
            cashTickets.text = shiftReport.cash.toString()
            qrTickets.text = shiftReport.qr.toString()
            nfcTickets.text = shiftReport.card.toString()
            totalTickets.text = shiftReport.totalTickets.toString()
            ticketPrice.text = shiftReport.ticketCategory.toString()
            total.text = shiftReport.totalIncome.toString()
        }
    }
}