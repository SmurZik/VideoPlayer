package com.smurzik.viedoplayer.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smurzik.viedoplayer.core.PlayerHelper
import com.smurzik.viedoplayer.main.FullscreenManualLiveDataWrapper
import com.smurzik.viedoplayer.main.Navigation
import com.smurzik.viedoplayer.main.Screen

class PlayerViewModel(
    private val playerHelper: PlayerHelper,
    private val orientationLiveDataWrapper: OrientationLiveDataWrapper.Mutable,
    private val fullScreen: FullscreenManualLiveDataWrapper.Mutable
) : ViewModel(), OrientationLiveDataWrapper.Read {

    fun player() = playerHelper.player()

    override fun liveData(): LiveData<Int> = orientationLiveDataWrapper.liveData()

    fun updateOrientation(value: Int) {
        orientationLiveDataWrapper.update(value)
    }

    fun fullScreenUpdate(value: Boolean) {
        fullScreen.update(value)
    }

    fun isPlaying() = playerHelper.isPlaying()

    fun pause() = playerHelper.pause()

    fun play() = playerHelper.play()
}