package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper
import javax.inject.Inject

interface ProgressLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Int>

    interface Update : LiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base @Inject constructor() : LiveDataWrapper.Abstract<Int>(), Mutable
}