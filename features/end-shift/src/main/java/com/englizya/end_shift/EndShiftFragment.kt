package com.englizya.end_shift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.common.base.BaseFragment
import com.englizya.ticket.end_shift.databinding.FragmentEndShiftBinding

class EndShiftFragment : BaseFragment() {

    private lateinit var binding: FragmentEndShiftBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentEndShiftBinding.inflate(layoutInflater)
        return binding.root
    }



}