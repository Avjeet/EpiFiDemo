package com.express.epifidemo.model.interceptor

import com.express.epifidemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object RetrofitInterceptor {
    fun apiKeyAsQuery(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request().url
                        .newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.OMDB_API_KEY)
                        .build()
                ).build()
        )
    }
}