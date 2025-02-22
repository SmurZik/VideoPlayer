package com.smurzik.viedoplayer.main

import com.smurzik.viedoplayer.core.LiveDataWrapper

interface FullscreenManualLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Boolean>

    interface Update : LiveDataWrapper.Update<Boolean>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Boolean>(), Mutable
}