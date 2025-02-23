package com.smurzik.viedoplayer.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.viedoplayer.core.PlayerHelper
import com.smurzik.viedoplayer.core.SharedDurationLiveDataWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerHelper: PlayerHelper,
    private val orientationLiveDataWrapper: OrientationLiveDataWrapper.Mutable,
    private val seekBar: SeekBarLiveDataWrapper.Mutable,
    private val duration: SharedDurationLiveDataWrapper.Mutable
) : ViewModel(), OrientationLiveDataWrapper.Read {

    private var seekBarJob: Job? = null

    fun player() = playerHelper.player()

    override fun liveData(): LiveData<Int> = orientationLiveDataWrapper.liveData()

    fun updateOrientation(value: Int) {
        orientationLiveDataWrapper.update(value)
    }

    fun changeVideoProgress(progress: Int) = playerHelper.changeVideoProgress(progress)

    fun duration() = duration.liveData()

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
}