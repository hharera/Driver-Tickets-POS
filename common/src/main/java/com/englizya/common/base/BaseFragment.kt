package com.englizya.common.base

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.englizya.common.network.ConnectionLiveData
import com.englizya.common.ui.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
    }

    lateinit var connectionLiveData: ConnectionLiveData

    private val loadingDialog: LoadingDialog by lazy {
//        TODO make loading dialog with company icon
        LoadingDialog(requireContext())
    }

    fun handleLoading(state: Boolean) {
        if (state) {
            showLoading()
        } else {
            dismissLoading()
        }
    }

    private fun showLoading() {
        loadingDialog.show()
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(messageRes: Int) {
        Toast.makeText(context, getText(messageRes), Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun handleFailure(exception: Exception?, messageRes: Int?) {
        exception?.printStackTrace()
        messageRes?.let { res ->
            showToast(res)
        }
    }

    fun handleFailure(throwable: Throwable?, messageRes: Int?) {
        throwable?.printStackTrace()
        messageRes?.let { res ->
            showToast(res)
        }
    }
}