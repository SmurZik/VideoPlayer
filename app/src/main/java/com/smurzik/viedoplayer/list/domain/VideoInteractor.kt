package com.smurzik.viedoplayer.list.domain

interface VideoInteractor {

    suspend fun getVideos(): VideoResult

    class Base(
        private val repository: VideoRepository
    ) : VideoInteractor {

        override suspend fun getVideos(): VideoResult {
            val result = repository.getVideos()
            return VideoResult.Success(result)
        }
    }
}