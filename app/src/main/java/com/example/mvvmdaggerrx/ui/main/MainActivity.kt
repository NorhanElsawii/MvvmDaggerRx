package com.example.mvvmdaggerrx.ui.main

import android.app.Activity
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.utils.base.BaseActivity
import com.example.mvvmdaggerrx.utils.extensions.launchActivity

class MainActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_main

    override fun onViewCreated() {
    }

    companion object {
        fun start(activity: Activity?, finish: Boolean = true) {
            if (finish)
                activity?.finishAffinity()

            activity?.launchActivity<MainActivity>()
        }
    }
}
