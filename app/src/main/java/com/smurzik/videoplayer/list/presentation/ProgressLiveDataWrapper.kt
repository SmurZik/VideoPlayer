package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper

interface ProgressLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Int>

    interface Update : LiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Int>(), Mutable
}