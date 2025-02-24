package com.smurzik.videoplayer.list.data

import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.videoplayer.list.domain.NoInternetConnectionException
import com.smurzik.videoplayer.list.domain.VideoItemDomain
import com.smurzik.videoplayer.list.domain.VideoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseVideoRepositoryTest {

    private lateinit var repository: VideoRepository
    private lateinit var cloudDataSource: TestVideoCloudDataSource
    private lateinit var cacheDataSource: TestVideoCacheDataSource

    @Before
    fun setUp() {
        cloudDataSource = TestVideoCloudDataSource()
        cacheDataSource = TestVideoCacheDataSource()
        val mapper = VideoItemDataToDomain()
        repository = BaseVideoRepository(cloudDataSource, mapper, cacheDataSource)
        cloudDataSource.changeConnection(true)
    }

    @Test
    fun test_get_videos_cache_not_empty() = runBlocking {
        val list = listOf(
            VideoItemData(1, "img", 2, "user", "link", "title", 0),
            VideoItemData(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        cacheDataSource.replaceData(list)
        val actual = repository.getVideos(false)
        val expected = listOf(
            VideoItemDomain(1, "img", 2, "user", "link", "title", 0),
            VideoItemDomain(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        assertEquals(expected, actual)
        assertEquals(2, cacheDataSource.getVideoCachedCalledCount)
        assertEquals(0, cacheDataSource.savedVideoCalledCount)
        assertEquals(0, cloudDataSource.getVideoCalledCount)
    }

    @Test
    fun test_get_videos_cache_empty() = runBlocking {
        val list = listOf(
            VideoItemData(1, "img", 2, "user", "link", "title", 0),
            VideoItemData(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        cacheDataSource.replaceData(emptyList())
        cloudDataSource.makeExpected(list)
        val actual = repository.getVideos(false)
        val expected = listOf(
            VideoItemDomain(1, "img", 2, "user", "link", "title", 0),
            VideoItemDomain(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.getVideoCachedCalledCount)
        assertEquals(1, cacheDataSource.savedVideoCalledCount)
        assertEquals(1, cloudDataSource.getVideoCalledCount)
    }

    @Test
    fun test_need_update_cache_not_empty() = runBlocking {
        val list = listOf(
            VideoItemData(1, "img", 2, "user", "link", "title", 0),
            VideoItemData(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        cacheDataSource.replaceData(list)
        cloudDataSource.makeExpected(list)
        val actual = repository.getVideos(true)
        val expected = listOf(
            VideoItemDomain(1, "img", 2, "user", "link", "title", 0),
            VideoItemDomain(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        assertEquals(expected, actual)
        assertEquals(1, cacheDataSource.getVideoCachedCalledCount)
        assertEquals(1, cacheDataSource.savedVideoCalledCount)
        assertEquals(1, cloudDataSource.getVideoCalledCount)
    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_get_videos_cache_is_empty_failure() {
        runBlocking {
            val list = listOf(
                VideoItemData(1, "img", 2, "user", "link", "title", 0),
                VideoItemData(2, "img2", 4, "user2", "link2", "title2", 1)
            )
            cacheDataSource.replaceData(emptyList())
            cloudDataSource.makeExpected(list)
            cloudDataSource.changeConnection(false)
            assertEquals(0, cacheDataSource.getVideoCachedCalledCount)
            assertEquals(0, cacheDataSource.savedVideoCalledCount)
            assertEquals(0, cloudDataSource.getVideoCalledCount)
            repository.getVideos(false)
        }
    }
}

private class TestVideoCloudDataSource : VideoCloudDataSource {

    var getVideoCalledCount = 0
    private var thereIsConnection = true
    private var videoDataList = listOf(VideoItemData(-1, "", 0, "", "", "", -1))

    fun makeExpected(data: List<VideoItemData>) {
        videoDataList = data
    }

    fun changeConnection(connected: Boolean) {
        thereIsConnection = connected
    }

    override suspend fun getVideos(): List<VideoItemData> {
        getVideoCalledCount++
        if (thereIsConnection)
            return videoDataList
        else
            throw UnknownHostException()
    }
}

private class TestVideoCacheDataSource : VideoCacheDataSource {

    var getVideoCachedCalledCount = 0
    var savedVideoCalledCount = 0
    val data = mutableListOf<VideoItemData>()

    fun replaceData(value: List<VideoItemData>) {
        data.clear()
        data.addAll(value)
    }

    override suspend fun getVideos(): List<VideoItemData> {
        getVideoCachedCalledCount++
        return data
    }

    override suspend fun saveVideos(videos: List<VideoItemData>) {
        savedVideoCalledCount++
        data.clear()
        data.addAll(videos)
    }
}