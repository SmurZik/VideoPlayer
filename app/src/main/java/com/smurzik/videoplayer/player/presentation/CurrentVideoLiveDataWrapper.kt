package com.smurzik.videoplayer.player.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper
import com.smurzik.videoplayer.list.presentation.VideoItemUi

interface CurrentVideoLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<VideoItemUi>

    interface Update : LiveDataWrapper.Update<VideoItemUi>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<VideoItemUi>(), Mutable
}