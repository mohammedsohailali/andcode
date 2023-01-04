package com.weather.info.di


import com.weather.info.BuildConfig
import com.weather.info.api.LiveDataCallAdapterFactory
import com.weather.info.api.WebServiceInterface
import dagger.Module
import dagger.Provides

import com.weather.info.util.Constant
import com.weather.info.util.PrefUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network Manger Class all the API Calls will be made by this class
 */
@Module
class NetworkModule {

    /**
     * final variable having Base Server URL of project
     * */
    private val mBaseUrl = BuildConfig.BASE_URL

    /**
     * this method will @return object of @see OkHttpClient
     * */
    @Singleton
    @Provides
    fun providesOkHttp(prefUtils: PrefUtils): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(RequestTokenInterceptor(prefUtils)).build()

    /**
     * This will @return Object of @see WebServiceInterface
     * */
    @Singleton
    @Provides
    fun provideApiService(oktHttpClient: OkHttpClient): WebServiceInterface =
        Retrofit.Builder().client(oktHttpClient)
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WebServiceInterface::class.java)

    /**
     * This Class will add Authorization Token in Header
     */
    inner class RequestTokenInterceptor(private val prefUtils: PrefUtils) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader(Constant.HEADER_DEVICE_TOKEN, prefUtils.getDeviceId())
                .addHeader(Constant.HEADER_INSTALLATION_TOKEN, prefUtils.getInstallationId())
                .addHeader(
                    Constant.HEADER_AUTHORIZATION,
                    "Bearer " + prefUtils.getAuthenticationToken()
                )
                .build()

            return chain.proceed(request)

        }
    }
}