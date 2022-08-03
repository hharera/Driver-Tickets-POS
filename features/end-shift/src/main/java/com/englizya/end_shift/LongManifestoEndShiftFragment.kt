package com.englizya.end_shift

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.ui.PaperOutDialog
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.ticket.end_shift.R
import com.englizya.ticket.end_shift.databinding.FragmentLongManifestoEndShiftBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LongManifestoEndShiftFragment : BaseFragment() {


    private lateinit var binding: FragmentLongManifestoEndShiftBinding

    private val endShiftViewModel: LongManifestoEndShiftViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentLongManifestoEndShiftBinding.inflate(layoutInflater)

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

            endShiftViewModel.longManifestoShiftReport.value?.let { report ->
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
                false
            )
        )
    }

    private fun setupObservers() {
        endShiftViewModel.longManifestoShiftReport.observe(viewLifecycleOwner) { shiftReport ->
            updateUI(shiftReport)
        }

        endShiftViewModel.manifesto.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                endShiftViewModel.endShift()
            }
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

        endShiftViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun updateUI(shiftReport: LongManifestoEndShiftResponse) {
        binding.apply {
            carCode.text = shiftReport.carCode
            driverCode.text = shiftReport.driverCode.toString()
            lineCode.text = shiftReport.lineCode
            date.text = TimeUtils.getDate(shiftReport.manifestoDate)
            shiftStart.text = "${TimeUtils.getDate(shiftReport.shiftStartTime)}  ${TimeUtils.getTime(shiftReport.shiftStartTime)}"
            shiftEnd.text =  "${TimeUtils.getDate(shiftReport.shiftEndTime)}  ${TimeUtils.getTime(shiftReport.shiftEndTime)}"
            workHours.text = TimeUtils.calculateWorkHoursAndMinutes(shiftReport)
            shiftReport.ticketDetails.forEach { key, value ->
                ticketsDetails.text = "$value X $key \n"
            }
            totalTickets.text = shiftReport.totalTickets.toString()
            total.text = shiftReport.totalIncome.toString()
        }
    }

    private fun checkPaperOutState(paperOutState: Boolean) {
        if (paperOutState) {
            showPaperOutDialog()
        }
    }

    private fun showPaperOutDialog() {
        PaperOutDialog {
            endShiftViewModel.longManifestoShiftReport.value?.let { endShiftViewModel.printReport(it) }
        }.show(childFragmentManager, "")
    }


}