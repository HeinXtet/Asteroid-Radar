package com.deevvdd.asteroidradar.api

import com.deevvdd.asteroidradar.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by heinhtet deevvdd@gmail.com on 09,June,2021
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url =
            original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
