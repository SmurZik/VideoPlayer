package com.smurzik.videoplayer.core

interface SharedDurationLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<Int>

    interface Update : LiveDataWrapper.Update<Int>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Int>(), Mutable
}