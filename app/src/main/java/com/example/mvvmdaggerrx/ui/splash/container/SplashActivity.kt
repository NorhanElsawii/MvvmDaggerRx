package com.example.mvvmdaggerrx.ui.splash.container

import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.ui.splash.SplashFragment
import com.example.mvvmdaggerrx.utils.base.BaseActivity
import com.example.mvvmdaggerrx.utils.extensions.replaceFragment

class SplashActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_splash

    override fun onViewCreated() {
        replaceFragment(SplashFragment(), R.id.fl_container)
    }

}
