package com.smurzik.videoplayer.list.domain

interface VideoInteractor {

    suspend fun getVideos(needUpdate: Boolean): VideoResult

    class Base(
        private val repository: VideoRepository
    ) : VideoInteractor {

        override suspend fun getVideos(needUpdate: Boolean): VideoResult {
            val result = repository.getVideos(needUpdate)
            return VideoResult.Success(result)
        }
    }
}