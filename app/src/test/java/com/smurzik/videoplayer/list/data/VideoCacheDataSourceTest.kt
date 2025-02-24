package com.smurzik.videoplayer.list.data

import com.smurzik.videoplayer.list.data.cache.VideoCache
import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cache.VideoDao
import com.smurzik.videoplayer.list.data.cache.VideoDataToCache
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoCacheDataSourceTest {

    @Test
    fun test_get_videos_empty() = runBlocking {
        val dao = TestDao()
        val dataToCache = VideoDataToCache()
        val dataSource = VideoCacheDataSource.Base(dao, dataToCache)

        val actual = dataSource.getVideos()
        val expected = emptyList<VideoCache>()
        assertEquals(expected, actual)
    }

    @Test
    fun test_get_videos_not_empty() = runBlocking {
        val dao = TestDao()
        val dataToCache = VideoDataToCache()
        val dataSource = VideoCacheDataSource.Base(dao, dataToCache)

        val list = listOf(
            VideoCache(
                1,
                "img",
                3,
                "user",
                "link",
                "title",
                0
            ), VideoCache(
                2,
                "img",
                5,
                "user",
                "link2",
                "title",
                1
            )
        )
        dao.data.addAll(list)

        val actualList = dataSource.getVideos()
        val expectedList = listOf(
            VideoItemData(
                1,
                "img",
                3,
                "user",
                "link",
                "title",
                0
            ),
            VideoItemData(
                2,
                "img",
                5,
                "user",
                "link2",
                "title",
                1
            )
        )

        assertEquals(expectedList, actualList)
    }

    @Test
    fun test_save() = runBlocking {
        val dao = TestDao()
        val dataToCache = VideoDataToCache()
        val dataSource = VideoCacheDataSource.Base(dao, dataToCache)

        dataSource.saveVideos(
            listOf(
                VideoItemData(
                    1,
                    "img",
                    3,
                    "user",
                    "link",
                    "title",
                    0
                ),
                VideoItemData(
                    2,
                    "img",
                    5,
                    "user",
                    "link2",
                    "title",
                    1
                )
            )
        )

        val expected = listOf(
            VideoCache(
                1,
                "img",
                3,
                "user",
                "link",
                "title",
                0
            ), VideoCache(
                2,
                "img",
                5,
                "user",
                "link2",
                "title",
                1
            )
        )

        assertEquals(expected, dao.data)
    }
}

private class TestDao : VideoDao {

    val data = mutableListOf<VideoCache>()

    override fun clearVideos() {
        data.clear()
    }

    override fun insertVideos(videos: List<VideoCache>) {
        data.addAll(videos)
    }

    override fun getVideos(): List<VideoCache> {
        return data
    }
}