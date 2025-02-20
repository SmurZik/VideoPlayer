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

    fun player(): ExoPlayer = exoPlayer

    fun stop() = exoPlayer.stop()
}

class VideoItemUiToUrl : VideoItemUi.Mapper<String> {
    override fun map(
        id: Long,
        image: String,
        duration: String,
        userName: String,
        link: String,
        title: String
    ) = link
}