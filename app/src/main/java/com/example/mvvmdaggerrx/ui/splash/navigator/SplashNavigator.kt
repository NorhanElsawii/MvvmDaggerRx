package com.example.mvvmdaggerrx.ui.splash.navigator

sealed class SplashNavigator {
    object Home : SplashNavigator()
    object Authentication : SplashNavigator()
}