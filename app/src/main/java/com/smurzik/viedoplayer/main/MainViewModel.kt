package com.smurzik.viedoplayer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smurzik.viedoplayer.list.presentation.ListScreen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel(), Navigation.Read {

    override fun liveData(): LiveData<Screen> = navigation.liveData()

    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(ListScreen)
    }
}