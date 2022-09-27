package com.express.epifidemo.di.modules

import android.content.Context
import com.express.epifidemo.model.OMDBApiService
import com.express.epifidemo.model.interceptor.RetrofitInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun getOMDBApiService(retrofit: Retrofit): OMDBApiService {
        return retrofit.create(OMDBApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okhttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OMDBApiService.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okhttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { RetrofitInterceptor.apiKeyAsQuery(it) }
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        // if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)
        //}
        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }
}