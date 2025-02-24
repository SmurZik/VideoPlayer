package com.smurzik.videoplayer.list.data.cache

import com.smurzik.videoplayer.list.data.VideoItemData
import javax.inject.Inject

interface VideoCacheDataSource {

    suspend fun getVideos(): List<VideoItemData>

    suspend fun saveVideos(videos: List<VideoItemData>)

    class Base @Inject constructor(
        private val dao: VideoDao,
        private val dataToCache: VideoDataToCache
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