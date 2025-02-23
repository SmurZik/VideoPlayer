package com.smurzik.viedoplayer.core

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.smurzik.viedoplayer.list.data.BaseVideoRepository
import com.smurzik.viedoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.viedoplayer.list.data.VideoItemDataToDomain
import com.smurzik.viedoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.viedoplayer.list.data.cache.VideoDataToCache
import com.smurzik.viedoplayer.list.data.cache.VideoRoomDatabase
import com.smurzik.viedoplayer.list.data.cloud.VideoService
import com.smurzik.viedoplayer.list.domain.VideoInteractor
import com.smurzik.viedoplayer.list.presentation.DurationMapper
import com.smurzik.viedoplayer.list.presentation.IndexMapper
import com.smurzik.viedoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.viedoplayer.list.presentation.ListViewModel
import com.smurzik.viedoplayer.list.presentation.ProgressLiveDataWrapper
import com.smurzik.viedoplayer.list.presentation.VideoItemDomainToUi
import com.smurzik.viedoplayer.list.presentation.VideoResultMapper
import com.smurzik.viedoplayer.main.FullscreenManualLiveDataWrapper
import com.smurzik.viedoplayer.main.MainViewModel
import com.smurzik.viedoplayer.main.Navigation
import com.smurzik.viedoplayer.player.presentation.OrientationLiveDataWrapper
import com.smurzik.viedoplayer.player.presentation.PlayerViewModel
import com.smurzik.viedoplayer.player.presentation.SeekBarLiveDataWrapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val navigation = Navigation.Base()
    private val service = Retrofit.Builder().baseUrl("https://api.pexels.com/videos/")
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(VideoService::class.java)
    private val database by lazy {
        return@lazy Room.databaseBuilder(
            context,
            VideoRoomDatabase::class.java,
            "videos_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    private val cacheDataSource = VideoCacheDataSource.Base(
        database.videoDao(),
        VideoDataToCache()
    )
    private val repository = BaseVideoRepository(
        VideoCloudDataSource.Base(service),
        VideoItemDataToDomain(),
        cacheDataSource
    )
    private val listLiveDataWrapper = ListLiveDataWrapper.Base()
    private val interactor = VideoInteractor.Base(repository)
    private val exoPlayer = ExoPlayer.Builder(context).build()
    private val playerHelper = PlayerHelper(exoPlayer, VideoItemUiToUrl())
    private val orientation = OrientationLiveDataWrapper.Base()
    private val fullScreenManual = FullscreenManualLiveDataWrapper.Base()
    private val seekBar = SeekBarLiveDataWrapper.Base()
    private val duration = SharedDurationLiveDataWrapper.Base()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(
                navigation = navigation,
                orientation = orientation,
                fullScreenManual
            )

            ListViewModel::class.java -> {
                ListViewModel(
                    interactor,
                    ProgressLiveDataWrapper.Base(),
                    listLiveDataWrapper,
                    VideoResultMapper(
                        listLiveDataWrapper,
                        VideoItemDomainToUi()
                    ),
                    playerHelper,
                    navigation,
                    orientation,
                    duration,
                    DurationMapper(),
                    IndexMapper()
                )
            }

            PlayerViewModel::class.java -> PlayerViewModel(
                playerHelper,
                orientation,
                seekBar,
                duration
            )

            else -> throw IllegalStateException("Unknown viewModel class $modelClass")
        } as T
    }
}