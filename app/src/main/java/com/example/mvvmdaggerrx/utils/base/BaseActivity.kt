package com.example.mvvmdaggerrx.utils.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmdaggerrx.di.modules.viewmodel.ViewModelFactory
import com.example.mvvmdaggerrx.utils.extensions.setSystemBarTheme
import com.example.mvvmdaggerrx.utils.locale.LocaleHelper
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    abstract fun layoutId(): Int

    abstract fun onViewCreated()

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        onViewCreated()
        setSystemBarTheme()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        super.applyOverrideConfiguration(LocaleHelper.onAttach(baseContext).resources.configuration)
    }

    fun <T : ViewModel> getViewModel(classType: Class<T>): T {
        return ViewModelProviders.of(this, viewModelFactory).get(classType)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}