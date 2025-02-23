package com.smurzik.videoplayer.list.domain

interface VideoRepository {

    suspend fun getVideos(needUpdate: Boolean): List<VideoItemDomain>
}