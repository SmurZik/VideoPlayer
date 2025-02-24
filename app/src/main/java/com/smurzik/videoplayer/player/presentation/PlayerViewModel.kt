package com.smurzik.videoplayer.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.videoplayer.core.PlayerHelper
import com.smurzik.videoplayer.core.SharedVideoLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.DurationMapper
import com.smurzik.videoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.PlayerInfoMapper
import com.smurzik.videoplayer.list.presentation.VideoItemUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class PlayerViewModel(
    private val playerHelper: PlayerHelper,
    private val orientationLiveDataWrapper: OrientationLiveDataWrapper.Mutable,
    private val seekBar: SeekBarLiveDataWrapper.Mutable,
    private val sharedVideoList: ListLiveDataWrapper.Mutable,
    private val durationMapper: DurationMapper,
    private val currentVideo: SharedVideoLiveDataWrapper.Mutable,
    private val playerInfoMapper: PlayerInfoMapper
) : ViewModel(), OrientationLiveDataWrapper.Read {

    private var seekBarJob: Job? = null

    fun player() = playerHelper.player()

    override fun liveData(): LiveData<Int> = orientationLiveDataWrapper.liveData()

    fun updateOrientation(value: Int) {
        orientationLiveDataWrapper.update(value)
    }

    fun newDuration() = playerHelper.newDuration()

    fun formatDuration(millis: Int): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val secondsRemain = seconds % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secondsRemain)
    }

    fun currentVideo() = currentVideo.liveData()

    fun updateCurrentVideo(index: Int) {
        val track = sharedVideoList.liveData().value?.get(index) ?: VideoItemUi(
            -1, "", -1, "", "", "", -1
        )
        currentVideo.update(track.map(playerInfoMapper))
    }

    fun changeVideoProgress(progress: Int) = playerHelper.changeVideoProgress(progress)

    fun duration(index: Int) =
        sharedVideoList.liveData().value?.get(index)?.map(durationMapper) ?: 0

    fun seekBar() = seekBar.liveData()

    fun updateSeekBar() {
        if (seekBarJob?.isActive == true) return

        seekBarJob?.cancel()
        seekBarJob = viewModelScope.launch {
            while (playerHelper.isPlaying()) {
                seekBar.update((playerHelper.currentProgress()).toInt())
                delay(500)
            }
        }
    }

    fun isPlaying() = playerHelper.isPlaying()

    fun pause() = playerHelper.pause()

    fun play() = playerHelper.play()

    fun seekBack() {
        playerHelper.seekTo((playerHelper.currentProgress() - 5000).coerceAtLeast(0))
    }

    fun seekForward() {
        playerHelper.seekTo(
            (playerHelper.currentProgress() + 5000).coerceAtMost(
                playerHelper.newDuration().toLong()
            )
        )
    }
}