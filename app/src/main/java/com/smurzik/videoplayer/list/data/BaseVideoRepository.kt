package com.smurzik.videoplayer.list.data

import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.videoplayer.list.domain.NoInternetConnectionException
import com.smurzik.videoplayer.list.domain.ServiceUnavailableException
import com.smurzik.videoplayer.list.domain.VideoItemDomain
import com.smurzik.videoplayer.list.domain.VideoRepository
import java.net.UnknownHostException
import javax.inject.Inject

class BaseVideoRepository @Inject constructor(
    private val cloudDataSource: VideoCloudDataSource,
    private val mapperToDomain: VideoItemDataToDomain,
    private val cacheDataSource: VideoCacheDataSource
) : VideoRepository {

    override suspend fun getVideos(needUpdate: Boolean): List<VideoItemDomain> {
        val result: List<VideoItemData>
        if (cacheDataSource.getVideos().isEmpty() || needUpdate) {
            try {
                result = cloudDataSource.getVideos()
                cacheDataSource.saveVideos(result)
            } catch (e: Exception) {
                throw when (e) {
                    is UnknownHostException -> NoInternetConnectionException()
                    else -> ServiceUnavailableException()
                }
            }
        } else {
            result = cacheDataSource.getVideos()
        }
        return result.map { it.map(mapperToDomain) }
    }
}