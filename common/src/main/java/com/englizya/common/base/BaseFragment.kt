package com.englizya.common.base

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.englizya.common.R
import com.englizya.common.ui.LoadingDialog
import com.englizya.common.utils.network.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.http.*

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    lateinit var connectionLiveData: ConnectionLiveData

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
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

    fun handleFailure(exception: Exception?, messageRes: Int? = null) {
        exception?.printStackTrace()
    }

    fun handleFailure(throwable: Throwable?) {
        throwable?.printStackTrace()
        checkExceptionType(throwable)
    }

    private fun checkExceptionType(throwable: Throwable?) {
        when (throwable) {
            is Exception -> handleFailure(throwable)
            is ClientRequestException  -> {
                when(throwable.response.status) {
                    HttpStatusCode.BadRequest -> showToast(throwable.message)
                }
                when(throwable.response.status) {
                    HttpStatusCode.Forbidden -> showToast(R.string.relogin)
                }
            }
            is HttpRequestTimeoutException -> showToast(R.string.check_your_internet)
        }
    }

    fun changeStatusBarColor(colorRes : Int) {
        activity?.window?.statusBarColor = resources.getColor(colorRes)
    }

}