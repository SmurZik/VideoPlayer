package com.smurzik.videoplayer.core

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.smurzik.videoplayer.list.data.BaseVideoRepository
import com.smurzik.videoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.videoplayer.list.data.VideoItemDataToDomain
import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cache.VideoDataToCache
import com.smurzik.videoplayer.list.data.cache.VideoRoomDatabase
import com.smurzik.videoplayer.list.data.cloud.VideoService
import com.smurzik.videoplayer.list.domain.VideoInteractor
import com.smurzik.videoplayer.list.presentation.DurationMapper
import com.smurzik.videoplayer.list.presentation.IndexMapper
import com.smurzik.videoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.ListViewModel
import com.smurzik.videoplayer.list.presentation.ProgressLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.VideoItemDomainToUi
import com.smurzik.videoplayer.list.presentation.VideoResultMapper
import com.smurzik.videoplayer.main.MainViewModel
import com.smurzik.videoplayer.main.Navigation
import com.smurzik.videoplayer.player.presentation.OrientationLiveDataWrapper
import com.smurzik.videoplayer.player.presentation.PlayerViewModel
import com.smurzik.videoplayer.player.presentation.SeekBarLiveDataWrapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNCHECKED_CAST")
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
    private val seekBar = SeekBarLiveDataWrapper.Base()
    private val duration = SharedDurationLiveDataWrapper.Base()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(
                navigation = navigation
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