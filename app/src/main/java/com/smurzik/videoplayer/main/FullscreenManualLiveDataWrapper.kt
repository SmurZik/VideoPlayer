package com.smurzik.videoplayer.main

import com.smurzik.videoplayer.core.LiveDataWrapper

interface FullscreenManualLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Boolean>

    interface Update : LiveDataWrapper.Update<Boolean>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Boolean>(), Mutable
}