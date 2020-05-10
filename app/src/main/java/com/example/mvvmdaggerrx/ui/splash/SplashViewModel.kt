package com.example.mvvmdaggerrx.ui.splash

import com.example.mvvmdaggerrx.ui.splash.navigator.SplashNavigator
import com.example.mvvmdaggerrx.utils.SingleLiveEvent
import com.example.mvvmdaggerrx.utils.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val repository: SplashRepository) :
    BaseViewModel(repository) {

    val navigate = SingleLiveEvent<SplashNavigator>()

    fun startSplashTimer() {
        addSubscription(setTimer())
    }

    private fun setTimer(): Disposable {
        return Observable.defer {
            Observable.just(0).delay(repository.getSplashTime(), TimeUnit.MILLISECONDS)
        }
            .doOnNext {
                if (isUserLogin(false)) {
                    navigate.postValue(SplashNavigator.Home)
                } else
                    navigate.postValue(SplashNavigator.Authentication)

            }
            .subscribe()
    }
}