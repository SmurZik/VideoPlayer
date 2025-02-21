package com.smurzik.viedoplayer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smurzik.viedoplayer.list.presentation.ListScreen
import com.smurzik.viedoplayer.player.presentation.OrientationLiveDataWrapper

class MainViewModel(
    private val navigation: Navigation.Mutable,
    private val orientation: OrientationLiveDataWrapper.Mutable
) : ViewModel(), Navigation.Read {

    override fun liveData(): LiveData<Screen> = navigation.liveData()

    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(ListScreen)
    }

    fun orientation() = orientation.liveData()

    fun updateOrientation(value: Int) = orientation.update(value)
}