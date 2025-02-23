package com.smurzik.viedoplayer.core

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.smurzik.viedoplayer.list.presentation.VideoItemUi

class PlayerHelper(
    private val exoPlayer: ExoPlayer,
    private val urlMapper: VideoItemUi.Mapper<String>
) {

    fun isPlaying() = exoPlayer.isPlaying

    fun setMediaItem(item: VideoItemUi) {
        val mediaItem = MediaItem.fromUri(item.map(urlMapper))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun changeVideoProgress(progress: Int) {
        exoPlayer.seekTo(progress.toLong())
    }

    fun currentProgress() = exoPlayer.currentPosition

    fun player(): ExoPlayer = exoPlayer

    fun pause() = exoPlayer.pause()

    fun stop() = exoPlayer.stop()

    fun play() = exoPlayer.play()
}

class VideoItemUiToUrl : VideoItemUi.Mapper<String> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String
    ) = link
}