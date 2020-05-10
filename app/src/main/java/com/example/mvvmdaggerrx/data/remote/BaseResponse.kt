package com.example.mvvmdaggerrx.data.remote

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<D> {

    @field:SerializedName("message")
    val message: String = ""

    @field:SerializedName("data")
    var data: D? = null
}