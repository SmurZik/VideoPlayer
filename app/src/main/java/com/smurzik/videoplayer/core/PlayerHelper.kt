package com.smurzik.videoplayer.core

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.smurzik.videoplayer.list.presentation.VideoItemUi

class PlayerHelper(
    private val exoPlayer: ExoPlayer,
    private val urlMapper: VideoItemUiToUrl
) {

    fun isPlaying() = exoPlayer.isPlaying

    fun setMediaItemList(list: List<VideoItemUi>, index: Int) {
        val mediaList = list.map { MediaItem.fromUri(it.map(urlMapper)) }
        exoPlayer.setMediaItems(mediaList)
        exoPlayer.prepare()
        exoPlayer.seekToDefaultPosition(index)
        exoPlayer.play()
    }

    fun newDuration() = exoPlayer.duration.toInt()

    fun changeVideoProgress(progress: Int) {
        exoPlayer.seekTo(progress.toLong())
    }

    fun currentProgress() = exoPlayer.currentPosition

    fun player(): ExoPlayer = exoPlayer

    fun pause() = exoPlayer.pause()

    fun stop() = exoPlayer.stop()

    fun play() = exoPlayer.play()

    fun seekTo(millis: Long) = exoPlayer.seekTo(millis)
}

class VideoItemUiToUrl : VideoItemUi.Mapper<String> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ) = link
}