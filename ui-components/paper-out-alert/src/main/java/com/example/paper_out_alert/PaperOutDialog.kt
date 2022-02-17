package com.example.paper_out_alert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.paper_out_alert.databinding.DialogPaperOutBinding

class PaperOutDialog(
    val whenPrintAgainClicked: () -> Unit,
) : DialogFragment() {

    private lateinit var dialogPaperOutBinding: DialogPaperOutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogPaperOutBinding = DialogPaperOutBinding.inflate(inflater, container, false)
        return dialogPaperOutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        dialogPaperOutBinding.printAgain.setOnClickListener {
            whenPrintAgainClicked()
            it.isEnabled = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}