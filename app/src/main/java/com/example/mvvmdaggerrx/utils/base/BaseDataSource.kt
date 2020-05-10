package com.example.mvvmdaggerrx.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.data.remote.BaseResponse
import com.example.mvvmdaggerrx.data.remote.ErrorResponse
import com.example.mvvmdaggerrx.utils.Status
import com.example.mvvmdaggerrx.utils.extensions.getErrorResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseDataSource<I>(
    private val repository: BaseRepository,
    private val status: MutableLiveData<Status>
) :
    PageKeyedDataSource<Int, I>() {

    private val compositeDisposable = CompositeDisposable()

    private var retry: Action? = null

    fun <D> subscribe(
        single: Single<BaseResponse<D>>,
        retryAction: Action?,
        callBack: (m: D?) -> Unit,
        isLoadMore: Boolean
    ) {
        setRetry(retryAction)
        when {
            repository.isNetworkConnected() -> addSubscription(single
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (isLoadMore)
                        status.postValue(Status.LoadingMore)
                    else
                        status.postValue(Status.Loading)
                }
                .subscribe({ response ->
                    if (isLoadMore)
                        status.postValue(Status.SuccessLoadingMore)
                    else
                        status.postValue(Status.Success(response))

                    callBack(response.data)
                    setRetry(null)

                }, { error ->
                    if (error is HttpException) {
                        val errorResponse = error.getErrorResponse()
                        if (isLoadMore)
                            status.postValue(
                                Status.ErrorLoadingMore(
                                    errorResponse
                                )
                            )
                        else
                            status.postValue(
                                Status.Error(
                                    errorResponse
                                )
                            )
                    } else if (isLoadMore)
                        status.postValue(
                            Status.ErrorLoadingMore(
                                ErrorResponse().also {
                                    it.message =
                                        repository.getString(R.string.some_thing_went_wrong_error_msg)
                                }
                            )
                        )
                    else
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
            isLoadMore -> status.postValue(Status.ErrorLoadingMore(ErrorResponse().also {
                it.message =
                    repository.getString(R.string.check_internet_connection)
            }))
            else -> status.postValue(Status.Error(ErrorResponse().also {
                it.message =
                    repository.getString(R.string.check_internet_connection)
            }))
        }
    }

    private fun clearSubscription() {
        if (!compositeDisposable.isDisposed) compositeDisposable.clear()
    }

    private fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun retry() {
        retry?.run()
    }

    private fun setRetry(action: Action?) {
        retry = action
    }

    override fun invalidate() {
        clearSubscription()
        super.invalidate()
    }

    fun onCleared() {
        clearSubscription()
    }

}