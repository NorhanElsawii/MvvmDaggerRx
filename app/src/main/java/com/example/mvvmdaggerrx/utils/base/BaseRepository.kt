package com.example.mvvmdaggerrx.utils.base

import com.example.mvvmdaggerrx.data.local.ConnectivityUtils
import com.example.mvvmdaggerrx.data.local.LocalDataUtils
import javax.inject.Inject

abstract class BaseRepository {
    @Inject
    lateinit var connectivityUtils: ConnectivityUtils

    @Inject
    lateinit var localDataUtils: LocalDataUtils


    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }

    fun getString(id: Int): String {
        return localDataUtils.getString(id)
    }

    fun isUserLogin(): Boolean {
        return true
    }

    fun clearUserData() {
    }
}