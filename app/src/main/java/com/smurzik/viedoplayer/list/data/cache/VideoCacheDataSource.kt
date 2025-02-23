package com.smurzik.viedoplayer.list.data.cache

import com.smurzik.viedoplayer.list.data.VideoItemData

interface VideoCacheDataSource {

    suspend fun getVideos(): List<VideoItemData>

    suspend fun saveVideos(videos: List<VideoItemData>)

    class Base(
        private val dao: VideoDao,
        private val dataToCache: VideoItemData.Mapper<VideoCache>
    ) : VideoCacheDataSource {

        override suspend fun getVideos(): List<VideoItemData> {
            val data = dao.getVideos()
            return data.map {
                VideoItemData(
                    it.id,
                    it.image,
                    it.duration,
                    it.userName,
                    it.link,
                    it.title,
                    it.index
                )
            }
        }

        override suspend fun saveVideos(videos: List<VideoItemData>) {
            dao.replaceVideo(videos.map { it.map(dataToCache) })
        }
    }
}