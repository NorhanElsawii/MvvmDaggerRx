package com.example.mvvmdaggerrx.di.component

import android.app.Application
import com.example.mvvmdaggerrx.application.MyApplication
import com.example.mvvmdaggerrx.di.modules.ApplicationModule
import com.example.mvvmdaggerrx.di.modules.NetworkModule
import com.example.mvvmdaggerrx.di.modules.ui.ActivityModule
import com.example.mvvmdaggerrx.di.modules.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        ActivityModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MyApplication)
}