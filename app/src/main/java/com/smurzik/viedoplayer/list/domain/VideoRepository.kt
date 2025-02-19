package com.smurzik.viedoplayer.list.domain

interface VideoRepository {

    suspend fun getVideos(): List<VideoItemDomain>
}