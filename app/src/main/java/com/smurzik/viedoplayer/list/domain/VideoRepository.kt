package com.smurzik.viedoplayer.list.domain

interface VideoRepository {

    suspend fun getVideos(needUpdate: Boolean): List<VideoItemDomain>
}