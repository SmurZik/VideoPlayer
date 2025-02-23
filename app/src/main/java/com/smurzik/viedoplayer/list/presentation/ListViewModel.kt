package com.smurzik.viedoplayer.list.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.viedoplayer.core.PlayerHelper
import com.smurzik.viedoplayer.core.SharedDurationLiveDataWrapper
import com.smurzik.viedoplayer.list.domain.VideoInteractor
import com.smurzik.viedoplayer.main.Navigation
import com.smurzik.viedoplayer.main.Screen
import com.smurzik.viedoplayer.player.presentation.CurrentVideoLiveDataWrapper
import com.smurzik.viedoplayer.player.presentation.OrientationLiveDataWrapper
import com.smurzik.viedoplayer.player.presentation.PlayerScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    private val videoInteractor: VideoInteractor,
    private val progressLiveDataWrapper: ProgressLiveDataWrapper.Mutable,
    private val listLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val resultMapper: VideoResultMapper,
    private val currentVideo: CurrentVideoLiveDataWrapper.Mutable,
    private val playerHelper: PlayerHelper,
    private val navigation: Navigation.Mutable,
    private val orientation: OrientationLiveDataWrapper.Mutable,
    private val duration: SharedDurationLiveDataWrapper.Mutable,
    private val durationMapper: DurationMapper
) : ViewModel(), ListLiveDataWrapper.Mutable {

    fun init() {
        if (playerHelper.isPlaying())
            playerHelper.stop()
        progressLiveDataWrapper.update(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = videoInteractor.getVideos()
            progressLiveDataWrapper.update(View.GONE)
            result.map(resultMapper)
        }
    }

    fun updateOrientation(value: Int) = orientation.update(value)

    fun updateCurrentTrack(value: VideoItemUi) {
        navigation.update(PlayerScreen)
        currentVideo.update(value)
        playerHelper.setMediaItem(value)
        duration.update(value.map(durationMapper))
    }

    fun navigation() = navigation.liveData()

    fun updateNavigation(value: Screen) {
        navigation.update(value)
    }

    fun progressLiveData(): LiveData<Int> = progressLiveDataWrapper.liveData()

    override fun liveData(): LiveData<List<VideoItemUi>> = listLiveDataWrapper.liveData()

    override fun update(value: List<VideoItemUi>) {
        listLiveDataWrapper.update(value)
    }
}