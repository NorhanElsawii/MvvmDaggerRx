package com.example.mvvmdaggerrx.di.modules

import com.example.mvvmdaggerrx.BuildConfig
import com.example.mvvmdaggerrx.BuildConfig.MAIN_HOST
import com.example.mvvmdaggerrx.data.local.sharedpreference.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAIN_HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        sharedPreferencesUtils: SharedPreferencesUtils
    ): OkHttpClient {
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("accept", "application/json")

//                request.addHeader("x-api-key", "d123")
//                val user = sharedPreferencesUtils.getUser()
//                if (user != null)
//                    request.addHeader(
//                        "authorization",
//                        "${user.tokenType} ${user.accessToken}"
//                    )
//                request.addHeader(
//                    "accept-Language",
//                    sharedPreferencesUtils.getLanguage().currentLanguageId
//                )

                it.proceed(request.build())
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return builder
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}