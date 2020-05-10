package com.example.mvvmdaggerrx.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.mvvmdaggerrx.application.MyApplication
import com.example.mvvmdaggerrx.di.component.ApplicationComponent
import com.example.mvvmdaggerrx.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

    lateinit var appComponent: ApplicationComponent

    fun init(application: MyApplication) {
        appComponent = DaggerApplicationComponent.builder()
            .application(application)
            .build()

        appComponent.inject(application)

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                //pass
            }

            override fun onActivityResumed(activity: Activity) {
                //pass
            }

            override fun onActivityPaused(activity: Activity) {
                //pass
            }

            override fun onActivityStopped(activity: Activity) {
                //pass
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                //pass
            }

            override fun onActivityDestroyed(activity: Activity) {
                //pass
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector)
            AndroidInjection.inject(activity)

        if (activity is FragmentActivity)
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?
                    ) {
                        if (f is Injectable)
                            AndroidSupportInjection.inject(f)
                    }
                }, true
            )
    }
}