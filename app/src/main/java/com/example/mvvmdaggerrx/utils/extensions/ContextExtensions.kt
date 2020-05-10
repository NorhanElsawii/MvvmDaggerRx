package com.example.mvvmdaggerrx.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.utils.Constants


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */

fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getDrawableCompat(@DrawableRes drawable: Int): Drawable {
    return ContextCompat.getDrawable(this, drawable)!!
}

fun Context.getSharedPref(): SharedPreferences {
    return this.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
}

fun Context.downloadBitmap(successDownloading: (bitmap: Bitmap) -> Unit, url: Uri) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .apply(
            RequestOptions()
                .override(200, 200)
                .placeholder(R.drawable.ic_place_holder)
        )
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                //pass
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                successDownloading(resource)
            }
        })
}


fun Context.getVersionCode(): String {
    try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo)
        return "$longVersionCode.0"
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun Context.openGooglePlayStoreForUpdate() {
    val appPackageName = packageName
    try {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")
        )
        intent.setPackage("com.android.vending")
        startActivity(intent)
    } catch (e: Exception) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}