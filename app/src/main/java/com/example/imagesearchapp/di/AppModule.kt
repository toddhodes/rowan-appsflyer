package com.example.imagesearchapp.di

import com.example.imagesearchapp.ui.main.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesHttpClient(interceptor : HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

    @Provides
    @Singleton
    fun providesRetrofit(client : OkHttpClient) : Retrofit =
        Retrofit.Builder().
                 addConverterFactory(GsonConverterFactory.create()).
                 baseUrl(UnsplashApi.BASE_URL).
                 client(client).
                 build()

    @Provides
    @Singleton
    fun providesUnsplashApi(retrofit: Retrofit) : UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

}