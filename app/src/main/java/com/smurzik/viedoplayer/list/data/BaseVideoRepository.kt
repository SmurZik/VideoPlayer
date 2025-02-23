package com.smurzik.viedoplayer.list.data

import com.smurzik.viedoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.viedoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.viedoplayer.list.domain.VideoItemDomain
import com.smurzik.viedoplayer.list.domain.VideoRepository

class BaseVideoRepository(
    private val cloudDataSource: VideoCloudDataSource,
    private val mapperToDomain: VideoItemData.Mapper<VideoItemDomain>,
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