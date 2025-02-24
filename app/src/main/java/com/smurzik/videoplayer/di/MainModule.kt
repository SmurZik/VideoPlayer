package com.smurzik.videoplayer.di

import com.smurzik.videoplayer.core.SharedVideoLiveDataWrapper
import com.smurzik.videoplayer.list.data.BaseVideoRepository
import com.smurzik.videoplayer.list.data.cache.VideoCacheDataSource
import com.smurzik.videoplayer.list.data.cloud.VideoCloudDataSource
import com.smurzik.videoplayer.list.domain.HandleError
import com.smurzik.videoplayer.list.domain.VideoInteractor
import com.smurzik.videoplayer.list.domain.VideoRepository
import com.smurzik.videoplayer.list.presentation.ErrorLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.ProgressLiveDataWrapper
import com.smurzik.videoplayer.main.Navigation
import com.smurzik.videoplayer.player.presentation.OrientationLiveDataWrapper
import com.smurzik.videoplayer.player.presentation.SeekBarLiveDataWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    @Singleton
    abstract fun bindNavigation(navigation: Navigation.Base): Navigation.Mutable

    @Binds
    @Singleton
    abstract fun bindCacheDataSource(cacheDataSource: VideoCacheDataSource.Base): VideoCacheDataSource

    @Binds
    @Singleton
    abstract fun bindCloudDataSource(cloudDataSource: VideoCloudDataSource.Base): VideoCloudDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(repository: BaseVideoRepository): VideoRepository

    @Binds
    @Singleton
    abstract fun bindListLiveDataWrapper(list: ListLiveDataWrapper.Base): ListLiveDataWrapper.Mutable

    @Binds
    @Singleton
    abstract fun bindInteractor(interactor: VideoInteractor.Base): VideoInteractor

    @Binds
    @Singleton
    abstract fun bindOrientation(orientation: OrientationLiveDataWrapper.Base): OrientationLiveDataWrapper.Mutable

    @Binds
    @Singleton
    abstract fun bindSeekBar(seekBar: SeekBarLiveDataWrapper.Base): SeekBarLiveDataWrapper.Mutable

    @Binds
    @Singleton
    abstract fun bindCurrentVideo(currentVideo: SharedVideoLiveDataWrapper.Base): SharedVideoLiveDataWrapper.Mutable

    @Binds
    @Singleton
    abstract fun bindProgressLiveDataWrapper(progress: ProgressLiveDataWrapper.Base): ProgressLiveDataWrapper.Mutable

    @Binds
    @Singleton
    abstract fun bindHandleError(handleError: HandleError.Base): HandleError

    @Binds
    @Singleton
    abstract fun bindErrorMessage(errorMessage: ErrorLiveDataWrapper.Base): ErrorLiveDataWrapper.Mutable

}