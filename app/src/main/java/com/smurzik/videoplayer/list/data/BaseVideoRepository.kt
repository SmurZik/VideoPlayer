package com.smurzik.videoplayer.list.data

import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.videoplayer.list.domain.VideoItemDomain
import com.smurzik.videoplayer.list.domain.VideoRepository
import javax.inject.Inject

class BaseVideoRepository @Inject constructor(
    private val cloudDataSource: VideoCloudDataSource,
    private val mapperToDomain: VideoItemDataToDomain,
    private val cacheDataSource: VideoCacheDataSource
) : VideoRepository {

    override suspend fun getVideos(needUpdate: Boolean): List<VideoItemDomain> {
        val result: List<VideoItemData>
        if (cacheDataSource.getVideos().isEmpty() || needUpdate) {
            result = cloudDataSource.getVideos()
            cacheDataSource.saveVideos(result)
        } else {
            result = cacheDataSource.getVideos()
        }
        return result.map { it.map(mapperToDomain) }
    }
}