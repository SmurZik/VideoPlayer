package com.smurzik.videoplayer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smurzik.videoplayer.list.presentation.ListScreen
import com.smurzik.videoplayer.player.presentation.OrientationLiveDataWrapper

class MainViewModel(
    private val navigation: Navigation.Mutable,
    private val orientation: OrientationLiveDataWrapper.Mutable,
    private val fullscreenManual: FullscreenManualLiveDataWrapper.Mutable
) : ViewModel(), Navigation.Read {

    override fun liveData(): LiveData<Screen> = navigation.liveData()

    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(ListScreen)
    }

    fun fullscreenManual() = fullscreenManual.liveData()

    fun updateFullscreenManual(value: Boolean) {
        fullscreenManual.update(value)
    }

    fun orientation() = orientation.liveData()

    fun updateOrientation(value: Int) = orientation.update(value)
}