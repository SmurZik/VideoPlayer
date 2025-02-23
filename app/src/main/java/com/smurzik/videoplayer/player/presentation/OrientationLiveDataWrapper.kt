package com.smurzik.videoplayer.player.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper

interface OrientationLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Int>

    interface Update : LiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Int>(), Mutable
}