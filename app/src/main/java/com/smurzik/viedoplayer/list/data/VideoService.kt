package com.smurzik.viedoplayer.list.data

import com.smurzik.viedoplayer.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers

interface VideoService {

    //https://api.pexels.com/videos/
    @Headers("Authorization: ${BuildConfig.apiKey}")
    @GET("popular")
    suspend fun getVideos(): VideoItemCloud
}