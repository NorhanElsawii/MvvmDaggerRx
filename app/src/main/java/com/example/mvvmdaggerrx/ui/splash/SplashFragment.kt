package com.example.mvvmdaggerrx.ui.splash

import com.example.mvvmdaggerrx.ui.main.MainActivity
import com.example.mvvmdaggerrx.R
import com.example.mvvmdaggerrx.ui.splash.navigator.SplashNavigator
import com.example.mvvmdaggerrx.utils.base.BaseFragment
import com.example.mvvmdaggerrx.utils.extensions.observe

class SplashFragment : BaseFragment() {
    private lateinit var viewModel: SplashViewModel

    override fun layoutId(): Int = R.layout.fragment_splash

    override fun onViewReady() {
        viewModel = getViewModel(SplashViewModel::class.java)
        initSplash()
    }

    private fun initSplash() {
        viewModel.startSplashTimer()

        observe(viewModel.navigate) {
            when (it) {
                is SplashNavigator.Home ->
                    MainActivity.start(activity)

//                is SplashNavigator.Authentication ->
//                    AuthenticationActivity.start(this)
            }
        }
    }
}