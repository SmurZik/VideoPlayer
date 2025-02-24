package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.core.LiveDataWrapper
import javax.inject.Inject

interface ListLiveDataWrapper {
    interface Read : LiveDataWrapper.Read<List<VideoItemUi>>

    interface Update : LiveDataWrapper.Update<List<VideoItemUi>>

    interface Mutable : Read, Update

    class Base @Inject constructor() : LiveDataWrapper.Abstract<List<VideoItemUi>>(), Mutable
}