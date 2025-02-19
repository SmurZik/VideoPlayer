package com.smurzik.viedoplayer.list.presentation

import com.smurzik.viedoplayer.list.domain.VideoItemDomain
import java.util.Locale

class VideoItemDomainToUi : VideoItemDomain.Mapper<VideoItemUi> {

    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String
    ) = VideoItemUi(id, image, formatDuration(duration), userName, link, title)

    private fun formatDuration(seconds: Int): String {
        val minutes = seconds / 60
        val secondsRemain = seconds % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secondsRemain)
    }
}