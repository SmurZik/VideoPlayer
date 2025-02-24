package com.smurzik.videoplayer.list.data.cloud

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoService {

    @Headers("Authorization: $API_KEY")
    @GET("search?query=nature&size=small&")
    suspend fun getVideos(@Query("page") page: Int): VideoItemCloud
}

const val API_KEY = "hOKqUk1gOjkg27DSgoZ3hpxUCrwhAC6XZfo8aYSFMd3Qj8ecpFfF22k1"