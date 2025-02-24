package com.smurzik.videoplayer.list.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideoInteractorTest {

    private lateinit var interactor: VideoInteractor
    private lateinit var repository: TestVideoRepository

    @Before
    fun setUp() {
        repository = TestVideoRepository()
        interactor = VideoInteractor.Base(
            repository,
            HandleError.Base()
        )
        repository.expectingErrorGetVideos(false)
    }

    @Test
    fun test_get_videos_success() = runBlocking {
        val list = listOf(
            VideoItemDomain(1, "img", 2, "user", "link", "title", 0),
            VideoItemDomain(2, "img2", 4, "user2", "link2", "title2", 1)
        )
        repository.changeExpectedVideos(list)

        val actual = interactor.getVideos(false)
        val expected = VideoResult.Success(list)

        assertEquals(expected, actual)
        assertEquals(1, repository.getVideosCalledCount)
    }

    @Test
    fun test_get_videos_error() = runBlocking {
        repository.expectingErrorGetVideos(true)

        val actual = interactor.getVideos(false)
        val expected = VideoResult.Failure("No internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.getVideosCalledCount)
    }
}

private class TestVideoRepository : VideoRepository {

    private val videos = mutableListOf<VideoItemDomain>()
    private var errorWhileGetVideos = false
    var getVideosCalledCount = 0

    fun changeExpectedVideos(value: List<VideoItemDomain>) {
        videos.clear()
        videos.addAll(value)
    }

    fun expectingErrorGetVideos(error: Boolean) {
        errorWhileGetVideos = error
    }

    override suspend fun getVideos(needUpdate: Boolean): List<VideoItemDomain> {
        getVideosCalledCount++
        if (errorWhileGetVideos)
            throw NoInternetConnectionException()
        else
            return videos
    }
}