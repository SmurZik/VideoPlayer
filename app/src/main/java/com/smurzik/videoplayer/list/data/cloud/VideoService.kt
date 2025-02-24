package com.smurzik.videoplayer.list.data.cloud

import com.smurzik.videoplayer.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoService {

    @Headers("Authorization: ${BuildConfig.apiKey}")
    @GET("search?query=nature&size=small&")
    suspend fun getVideos(@Query("page") page: Int): VideoItemCloud
}