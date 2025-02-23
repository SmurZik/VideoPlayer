package com.smurzik.videoplayer.main

import com.smurzik.videoplayer.core.LiveDataWrapper

interface Navigation {

    interface Read : LiveDataWrapper.Read<Screen>

    interface Update : LiveDataWrapper.Update<Screen>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<Screen>(), Mutable
}