package com.example.mvvmdaggerrx.data.remote

import com.google.gson.annotations.SerializedName

class ErrorResponse {

    @field:SerializedName("code")
    var code: Int = 0

    @field:SerializedName("message")
    var message: String = ""

    @field:SerializedName("errors")
    val errors: Map<String, String>? = null
}