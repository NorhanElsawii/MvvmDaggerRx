package com.example.mvvmdaggerrx.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.data.remote.BaseResponse
import com.example.mvvmdaggerrx.data.remote.ErrorResponse
import com.example.mvvmdaggerrx.utils.SingleLiveEvent
import com.example.mvvmdaggerrx.utils.Status
import com.example.mvvmdaggerrx.utils.extensions.getErrorResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    val showLoginDialog = SingleLiveEvent<Boolean>()
    val showNetworkError = SingleLiveEvent<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    fun <D> subscribe(
        single: Single<BaseResponse<D>>,
        status: MutableLiveData<Status>
    ) {
        if (repository.isNetworkConnected())
            compositeDisposable.add(single
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    status.postValue(Status.Loading)
                }
                .subscribe({ response ->
                    status.postValue(Status.Success(response))
                }, { error ->
                    if (error is HttpException) {
                        val errorResponse = error.getErrorResponse()
                        status.postValue(
                            Status.Error(
                                errorResponse
                            )
                        )
                    } else
                        status.postValue(
                            Status.Error(
                                ErrorResponse().also {
                                    it.message =
                                        repository.getString(R.string.some_thing_went_wrong_error_msg)
                                }
                            )
                        )
                })
            )
        else
            status.postValue(
                Status.Error(
                    ErrorResponse().also {
                        it.message =
                            repository.getString(R.string.check_internet_connection)
                    }
                )
            )
    }

    private fun clearSubscription() {
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        clearSubscription()
        super.onCleared()
    }

    fun isUserLogin(showLoginPopUp: Boolean = true): Boolean {
        val isUserLogin = repository.isUserLogin()
        if (!isUserLogin && showLoginPopUp)
            this.showLoginDialog.postValue(true)
        return isUserLogin
    }

    fun isNetworkConnected(): Boolean {
        val isNetworkConnected = repository.isNetworkConnected()
        if (!isNetworkConnected)
            this.showNetworkError.postValue(true)
        return isNetworkConnected
    }

    fun logout() {
        repository.clearUserData()
    }
}