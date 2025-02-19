package com.smurzik.viedoplayer.list.data

import com.smurzik.viedoplayer.list.domain.VideoItemDomain
import com.smurzik.viedoplayer.list.domain.VideoRepository

class BaseVideoRepository(
    private val cloudDataSource: VideoCloudDataSource,
    private val mapperToDomain: VideoItemData.Mapper<VideoItemDomain>
) : VideoRepository {

    override suspend fun getVideos(): List<VideoItemDomain> {
        val result = cloudDataSource.getVideos()
        return result.map { it.map(mapperToDomain) }
    }
}