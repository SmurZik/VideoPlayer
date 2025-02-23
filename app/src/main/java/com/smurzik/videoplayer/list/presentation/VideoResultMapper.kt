package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.list.domain.VideoItemDomain
import com.smurzik.videoplayer.list.domain.VideoResult

class VideoResultMapper(
    private val videoListLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val domainToUiMapper: VideoItemDomain.Mapper<VideoItemUi>
) : VideoResult.Mapper<Unit> {
    override fun map(list: List<VideoItemDomain>, errorMessage: String) {
        if (errorMessage.isEmpty()) {
            videoListLiveDataWrapper.update(list.map { it.map(domainToUiMapper) })
        }
    }
}