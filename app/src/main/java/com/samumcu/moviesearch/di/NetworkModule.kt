package com.samumcu.moviesearch.di

import com.samumcu.moviesearch.BuildConfig
import com.samumcu.moviesearch.data.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesHeaderInterceptor() = Interceptor { chain ->
        val req = chain.request()
        val originalHttpUrl = req.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(MovieService.APIKEY, BuildConfig.OMDBAPI_KEY)
            .build()
        val reqBuilder = req.newBuilder().url(url)
        val newReq = reqBuilder.build()
        chain.proceed(newReq)
    }

    @Provides
    @Singleton
    fun providesHttpClient(logger: HttpLoggingInterceptor, header: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(header)
            addInterceptor(logger)
            callTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().run {
            baseUrl(MovieService.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            build()
        }

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}
