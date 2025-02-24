package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.core.MutableLiveDataWrapper
import javax.inject.Inject

interface ErrorLiveDataWrapper {
    interface Read : MutableLiveDataWrapper.Read<String>

    interface Update : MutableLiveDataWrapper.Update<String>

    interface Mutable : Read, Update

    class Base @Inject constructor() : MutableLiveDataWrapper.Abstract<String>(), Mutable
}