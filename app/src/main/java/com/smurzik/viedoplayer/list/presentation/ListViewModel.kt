package com.smurzik.viedoplayer.list.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smurzik.viedoplayer.list.domain.VideoInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    private val videoInteractor: VideoInteractor,
    private val progressLiveDataWrapper: ProgressLiveDataWrapper.Mutable,
    private val listLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val resultMapper: VideoResultMapper
) : ViewModel(), ListLiveDataWrapper.Mutable {

    fun init() {
        progressLiveDataWrapper.update(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = videoInteractor.getVideos()
            progressLiveDataWrapper.update(View.GONE)
            result.map(resultMapper)
        }
    }

    override fun liveData(): LiveData<List<VideoItemUi>> = listLiveDataWrapper.liveData()

    override fun update(value: List<VideoItemUi>) {
        listLiveDataWrapper.update(value)
    }
}