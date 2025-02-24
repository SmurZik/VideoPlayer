package com.smurzik.videoplayer.di

import com.smurzik.videoplayer.list.data.cloud.VideoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideService(): VideoService =
        Retrofit.Builder().baseUrl("https://api.pexels.com/videos/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(VideoService::class.java)
}