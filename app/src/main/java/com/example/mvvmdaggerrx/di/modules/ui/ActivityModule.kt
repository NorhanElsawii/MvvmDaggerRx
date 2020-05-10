package  com.example.mvvmdaggerrx.di.modules.ui

import com.example.mvvmdaggerrx.ui.main.MainActivity
import com.example.mvvmdaggerrx.di.modules.ui.fragmentbuilder.MainActivityFragmentBuilder
import com.example.mvvmdaggerrx.di.modules.ui.fragmentbuilder.SplashActivityFragmentBuilder
import com.example.mvvmdaggerrx.ui.splash.container.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityFragmentBuilder::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashActivityFragmentBuilder::class])
    abstract fun contributeSplashActivity(): SplashActivity


}