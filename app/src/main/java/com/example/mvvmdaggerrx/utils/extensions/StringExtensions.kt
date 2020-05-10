package com.example.mvvmdaggerrx.utils.extensions

import android.content.Intent
import android.content.Intent.ACTION_SEND
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */

//2017-10-19 15:24 56
fun String.getTimeInMilliSec(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Asia/Riyadh")
    return try {
        val mDate = sdf.parse(this)
        mDate!!.time
    } catch (e: ParseException) {
        -1
    }
}

fun String.getDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.getTime(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.getYouTubeImageUrl(): String {
    return "https://img.youtube.com/vi/$this/hqdefault.jpg"
}

fun String.share(startShare: (intent: Intent) -> Unit) {
    try {
        val i = Intent(ACTION_SEND).setType("text/plain")
        i.putExtra(Intent.EXTRA_TEXT, this)
        startShare(i)
    } catch (e: Exception) {
        //pass
    }
}

fun String.isValidEmail(): Boolean {
    return length > 0 && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return length >= 8
}

fun String.isValidName(): Boolean {
    return length in 3..20
}