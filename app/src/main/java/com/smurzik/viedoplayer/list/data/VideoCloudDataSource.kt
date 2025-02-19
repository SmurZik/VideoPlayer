package com.smurzik.viedoplayer.list.data

interface VideoCloudDataSource {

    suspend fun getVideos(): List<VideoItemData>

    class Base(
        private val service: VideoService
    ) : VideoCloudDataSource {

        override suspend fun getVideos(): List<VideoItemData> {
            val result = service.getVideos().videos
            return result.map {
                VideoItemData(
                    it.id,
                    it.image,
                    it.duration,
                    it.user.name,
                    it.files.first().link
                )
            }
        }
    }
}