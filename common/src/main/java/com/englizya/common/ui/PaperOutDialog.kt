package com.englizya.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.englizya.common.databinding.DialogPaperOutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        dialog?.setCancelable(false)
        return dialogPaperOutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        dialogPaperOutBinding.printAgain.setOnClickListener {
            whenPrintAgainClicked()
            lifecycleScope.launch(Dispatchers.Main) {
                dialogPaperOutBinding.printAgain.isEnabled = false

                withContext(Dispatchers.IO) {
                    delay(1000)
                }

                dialogPaperOutBinding.printAgain.isEnabled = true
            }
        }
    }
}