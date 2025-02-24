package com.smurzik.videoplayer.main

import com.smurzik.videoplayer.core.LiveDataWrapper
import javax.inject.Inject

interface Navigation {

    interface Read : LiveDataWrapper.Read<Screen>

    interface Update : LiveDataWrapper.Update<Screen>

    interface Mutable : Read, Update

    class Base @Inject constructor() : LiveDataWrapper.Abstract<Screen>(), Mutable
}