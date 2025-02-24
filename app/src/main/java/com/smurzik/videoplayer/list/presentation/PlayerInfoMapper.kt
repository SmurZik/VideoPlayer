package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.player.presentation.PlayerInfoUi

class PlayerInfoMapper : VideoItemUi.Mapper<PlayerInfoUi> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ): PlayerInfoUi = PlayerInfoUi(title, duration * 1000, userName)
}