package com.smurzik.videoplayer.di

import com.smurzik.videoplayer.core.VideoItemUiToUrl
import com.smurzik.videoplayer.list.data.VideoItemDataToDomain
import com.smurzik.videoplayer.list.data.cache.VideoDataToCache
import com.smurzik.videoplayer.list.presentation.DurationMapper
import com.smurzik.videoplayer.list.presentation.ErrorLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.IndexMapper
import com.smurzik.videoplayer.list.presentation.ListLiveDataWrapper
import com.smurzik.videoplayer.list.presentation.PlayerInfoMapper
import com.smurzik.videoplayer.list.presentation.VideoItemDomainToUi
import com.smurzik.videoplayer.list.presentation.VideoResultMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideVideoDataToCacheMapper(): VideoDataToCache {
        return VideoDataToCache()
    }

    @Provides
    @Singleton
    fun provideVideoItemToDomainMapper(): VideoItemDataToDomain {
        return VideoItemDataToDomain()
    }

    @Provides
    @Singleton
    fun provideVideoItemUiToUrlMapper(): VideoItemUiToUrl {
        return VideoItemUiToUrl()
    }

    @Provides
    @Singleton
    fun providePlayerInfoMapper(): PlayerInfoMapper {
        return PlayerInfoMapper()
    }

    @Provides
    @Singleton
    fun provideVideoItemDomainToUiMapper(): VideoItemDomainToUi {
        return VideoItemDomainToUi()
    }

    @Provides
    @Singleton
    fun provideVideoResultMapper(
        errorLiveDataWrapper: ErrorLiveDataWrapper.Mutable,
        domainToUi: VideoItemDomainToUi,
        list: ListLiveDataWrapper.Mutable
    ): VideoResultMapper {
        return VideoResultMapper(errorLiveDataWrapper, list, domainToUi)
    }

    @Provides
    @Singleton
    fun provideIndexMapper(): IndexMapper {
        return IndexMapper()
    }

    @Provides
    @Singleton
    fun provideDurationMapper(): DurationMapper {
        return DurationMapper()
    }
}