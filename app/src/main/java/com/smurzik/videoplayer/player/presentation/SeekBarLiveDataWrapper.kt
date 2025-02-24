package com.smurzik.videoplayer.player.presentation

import com.smurzik.videoplayer.core.MutableLiveDataWrapper

interface SeekBarLiveDataWrapper {
    interface Read : MutableLiveDataWrapper.Read<Int>

    interface Update : MutableLiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base : MutableLiveDataWrapper.Abstract<Int>(), Mutable
}