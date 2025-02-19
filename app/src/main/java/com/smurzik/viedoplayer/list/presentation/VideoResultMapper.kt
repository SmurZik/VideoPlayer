package com.smurzik.viedoplayer.list.presentation

import com.smurzik.viedoplayer.list.domain.VideoItemDomain
import com.smurzik.viedoplayer.list.domain.VideoResult

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