package com.smurzik.videoplayer.list.domain

import javax.inject.Inject

interface VideoInteractor {

    suspend fun getVideos(needUpdate: Boolean): VideoResult

    class Base @Inject constructor(
        private val repository: VideoRepository,
        private val handleError: HandleError
    ) : VideoInteractor {

        override suspend fun getVideos(needUpdate: Boolean): VideoResult {
            try {
                val result = repository.getVideos(needUpdate)
                return VideoResult.Success(result)
            } catch (e: Exception) {
                return VideoResult.Failure(handleError.handle(e))
            }
        }
    }
}