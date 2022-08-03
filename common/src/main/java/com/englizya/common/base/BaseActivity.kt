package com.englizya.common.base

import android.content.Intent
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.englizya.common.R
import com.englizya.common.ui.ErrorDialog
import com.englizya.common.ui.LoadingDialog
import com.englizya.common.ui.NoInternetDialog
import io.ktor.client.features.*
import io.ktor.http.*

open class BaseActivity : AppCompatActivity() {

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(messageRes: Int) {
        Toast.makeText(this, getText(messageRes), Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun handleFailure(exception: Exception?, messageRes: Int? = null) {
//        TODO handle every single exception
        exception?.printStackTrace()
        messageRes?.let { res ->
            showToast(res)
        }
    }

//    fun handleFailure(throwable: Throwable?, messageRes: Int? = null) {
//        throwable?.printStackTrace()
//        messageRes?.let { res ->
//            showToast(res)
//        }
//    }
    fun handleFailure(throwable: Throwable?) {
        throwable?.printStackTrace()
        checkExceptionType(throwable)
    }

    private fun checkExceptionType(throwable: Throwable?) {
        when (throwable) {
            is ClientRequestException -> {
                when(throwable.response.status) {
                    HttpStatusCode.BadRequest -> showErrorDialog(throwable.message.split("Text:")[1].dropWhile { it == '"' })
                }

                when (throwable.response.status) {
                    HttpStatusCode.Forbidden -> showErrorDialog(getString(R.string.not_authorized))
                }
            }

            is HttpRequestTimeoutException -> showNoInternetDialog(R.string.check_your_internet)
        }
    }

    private fun showErrorDialog(message: String) {
        val dialog = ErrorDialog(message)
        dialog.show(supportFragmentManager, "errorDialog")
    }
    private fun showNoInternetDialog(messageId : Int) {
        val dialog = NoInternetDialog(getString(messageId))
        dialog.show(supportFragmentManager, "NoInternetDialog")
    }

    fun <T : AppCompatActivity> gotToActivity(activityClass: Class<T>) {
        startActivity(Intent(this, activityClass))
            .also { finish() }
    }
}
