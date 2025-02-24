package com.smurzik.videoplayer.player.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper
import javax.inject.Inject

interface OrientationLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Int>

    interface Update : LiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base @Inject constructor() : LiveDataWrapper.Abstract<Int>(), Mutable
}