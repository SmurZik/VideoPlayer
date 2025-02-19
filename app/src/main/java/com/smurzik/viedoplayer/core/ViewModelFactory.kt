package com.smurzik.viedoplayer.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smurzik.viedoplayer.list.data.BaseVideoRepository
import com.smurzik.viedoplayer.list.data.VideoCloudDataSource
import com.smurzik.viedoplayer.list.data.VideoItemDataToDomain
import com.smurzik.viedoplayer.list.data.VideoService
import com.smurzik.viedoplayer.list.domain.VideoInteractor
import com.smurzik.viedoplayer.list.domain.VideoRepository
import com.smurzik.viedoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.viedoplayer.list.presentation.ListViewModel
import com.smurzik.viedoplayer.list.presentation.ProgressLiveDataWrapper
import com.smurzik.viedoplayer.list.presentation.VideoItemDomainToUi
import com.smurzik.viedoplayer.list.presentation.VideoResultMapper
import com.smurzik.viedoplayer.main.MainViewModel
import com.smurzik.viedoplayer.main.Navigation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelFactory : ViewModelProvider.Factory {

    private val navigation = Navigation.Base()
    private val service = Retrofit.Builder().baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(VideoService::class.java)
    private val repository = BaseVideoRepository(
        VideoCloudDataSource.Base(service),
        VideoItemDataToDomain()
    )
    private val interactor = VideoInteractor.Base(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(navigation = navigation)
            ListViewModel::class.java -> ListViewModel(
                interactor,
                ProgressLiveDataWrapper.Base(),
                ListLiveDataWrapper.Base(),
                VideoResultMapper(
                    ListLiveDataWrapper.Base(),
                    VideoItemDomainToUi()
                )
            )

            else -> throw IllegalStateException("Unknown viewModel class $modelClass")
        } as T
    }
}