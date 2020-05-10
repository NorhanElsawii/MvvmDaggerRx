package com.example.mvvmdaggerrx.utils.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.data.remote.ErrorResponse
import com.example.mvvmdaggerrx.di.Injectable
import com.example.mvvmdaggerrx.di.modules.viewmodel.ViewModelFactory
import com.example.mvvmdaggerrx.utils.Status
import com.example.mvvmdaggerrx.utils.extensions.hideSoftKeyboard
import com.example.mvvmdaggerrx.utils.extensions.observe
import com.example.mvvmdaggerrx.utils.extensions.showSoftKeyboard
import com.tapadoo.alerter.Alerter
import javax.inject.Inject

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var pd: Dialog? = null

    abstract fun layoutId(): Int

    private var baseViewModel: BaseViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createCustomProgressDialog()
        onViewReady()
        initListeners()
    }

    fun <T : ViewModel> getViewModel(classType: Class<T>): T {
        return ViewModelProviders.of(this, viewModelFactory).get(classType).also {
            if (it is BaseViewModel)
                baseViewModel = it
        }
    }

    private fun initListeners() {
        observe(baseViewModel?.showLoginDialog) {
            showLoginDialog()
        }

        observe(baseViewModel?.showNetworkError) {
            showErrorMsg(getString(R.string.check_internet_connection))
        }
    }

    abstract fun onViewReady()

    fun showErrorMsg(msg: String?) {
        activity?.let {
            if (!msg.isNullOrEmpty())
                Alerter.create(it)
                    .setText(msg)
                    .setTextAppearance(R.style.AlerterCustomTextAppearance)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .setBackgroundColorRes(R.color.red)
                    .show()
        }
    }

    fun showMsg(msg: String?) {
        activity?.let {
            if (!msg.isNullOrEmpty())
                Alerter.create(it)
                    .setText(msg)
                    .setTextAppearance(R.style.AlerterCustomTextAppearance)
                    .setDuration(3000)
                    .enableSwipeToDismiss()
                    .setBackgroundColorRes(R.color.green)
                    .show()
        }
    }

    private fun createCustomProgressDialog() {
        context?.let {
            pd = Dialog(it, R.style.DialogCustomTheme)
            pd?.setContentView(R.layout.layout_dialog_progress)
            pd?.setCancelable(false)
        }
    }

    fun showDialogLoading() {
        pd?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideDialogLoading() {
        pd?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }

    fun hideSoftKeyboard() {
        activity.hideSoftKeyboard()
    }

    fun showSoftKeyboard() {
        activity.showSoftKeyboard()
    }

    private fun showLoginDialog() {

    }

    private fun dismissDialog(dialog: Dialog) {
    }

    fun onError(error: Status.Error, showErrorMsg: Boolean = true, handleError: () -> Unit) {
        handleErrorStatus(error.errorResponse, showErrorMsg, handleError)
    }

    fun onError(
        error: Status.ErrorLoadingMore,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit
    ) {
        handleErrorStatus(error.errorResponse, showErrorMsg, handleError)
    }

    private fun handleErrorStatus(
        errorResponse: ErrorResponse,
        showErrorMsg: Boolean = true,
        handleError: () -> Unit
    ) {
        if (showErrorMsg)
            showErrorMsg(errorResponse.message)
        handleError()
    }


    fun logout() {
        baseViewModel?.logout()
        //handle redirection
    }

    override fun onStop() {
        super.onStop()
        Alerter.clearCurrent(activity)
    }
}