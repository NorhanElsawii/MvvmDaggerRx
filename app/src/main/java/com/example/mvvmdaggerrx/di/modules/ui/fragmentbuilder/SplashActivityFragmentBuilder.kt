package com.example.mvvmdaggerrx.di.modules.ui.fragmentbuilder

import com.example.mvvmdaggerrx.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SplashActivityFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment
}