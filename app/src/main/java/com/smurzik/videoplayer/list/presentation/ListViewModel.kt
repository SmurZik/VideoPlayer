package com.smurzik.videoplayer.list.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.videoplayer.core.PlayerHelper
import com.smurzik.videoplayer.list.domain.VideoInteractor
import com.smurzik.videoplayer.main.Navigation
import com.smurzik.videoplayer.main.Screen
import com.smurzik.videoplayer.player.presentation.PlayerScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val videoInteractor: VideoInteractor,
    private val progressLiveDataWrapper: ProgressLiveDataWrapper.Mutable,
    private val listLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val resultMapper: VideoResultMapper,
    private val playerHelper: PlayerHelper,
    private val navigation: Navigation.Mutable,
    private val indexMapper: IndexMapper
) : ViewModel(), ListLiveDataWrapper.Mutable {

    fun init(needUpdate: Boolean) {
        if (playerHelper.isPlaying())
            playerHelper.stop()
        progressLiveDataWrapper.update(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = videoInteractor.getVideos(needUpdate)
            progressLiveDataWrapper.update(View.GONE)
            result.map(resultMapper)
        }
    }

    fun updateCurrentPlaylist(playlist: List<VideoItemUi>, selectedItem: VideoItemUi) {
        navigation.update(PlayerScreen)
        val index = selectedItem.map(indexMapper)
        playerHelper.setMediaItemList(playlist, index)
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