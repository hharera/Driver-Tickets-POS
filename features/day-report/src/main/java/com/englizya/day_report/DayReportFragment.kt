package com.englizya.day_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.ticket.day_report.databinding.FragmentDayReportBinding

class DayReportFragment : BaseFragment() {

    private lateinit var binding: FragmentDayReportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDayReportBinding.inflate(layoutInflater)

        return binding.root
    }
}