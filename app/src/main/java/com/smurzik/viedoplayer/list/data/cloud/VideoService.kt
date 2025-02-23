package com.smurzik.viedoplayer.list.data.cloud

import com.smurzik.viedoplayer.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoService {

    @Headers("Authorization: ${BuildConfig.apiKey}")
    @GET("search?query=nature&")
    suspend fun getVideos(@Query("page") page: Int): VideoItemCloud
}