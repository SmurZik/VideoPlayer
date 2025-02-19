package com.smurzik.viedoplayer.list.presentation

import com.smurzik.viedoplayer.core.LiveDataWrapper

interface ListLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<List<VideoItemUi>>

    interface Update : LiveDataWrapper.Update<List<VideoItemUi>>

    interface Mutable : Read, Update

    class Base : LiveDataWrapper.Abstract<List<VideoItemUi>>(), Mutable
}