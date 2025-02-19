package com.smurzik.viedoplayer.player.presentation

import com.smurzik.viedoplayer.core.LiveDataWrapper
import com.smurzik.viedoplayer.list.presentation.VideoItemUi

interface CurrentVideoLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<VideoItemUi>

    interface Update : LiveDataWrapper.Update<VideoItemUi>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<VideoItemUi>(), Mutable
}