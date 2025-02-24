package com.smurzik.videoplayer.player.presentation

import com.smurzik.videoplayer.core.MutableLiveDataWrapper
import javax.inject.Inject

interface SeekBarLiveDataWrapper {
    interface Read : MutableLiveDataWrapper.Read<Int>

    interface Update : MutableLiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base @Inject constructor() : MutableLiveDataWrapper.Abstract<Int>(), Mutable
}