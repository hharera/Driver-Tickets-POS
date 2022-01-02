package com.englizya.day_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.englizya.day_report.databinding.FragmentBottomSheetLoginBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetLoginBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentBottomSheetLoginBinding.inflate(layoutInflater)
        return binding.root
    }






}