package com.example.mvvmdaggerrx.ui.splash

import com.example.mvvmdaggerrx.utils.base.BaseRepository
import javax.inject.Inject

class SplashRepository @Inject constructor() : BaseRepository() {
    private val splashTimeInMilliSec = 2000L

    fun getSplashTime(): Long {
        return splashTimeInMilliSec
    }
}