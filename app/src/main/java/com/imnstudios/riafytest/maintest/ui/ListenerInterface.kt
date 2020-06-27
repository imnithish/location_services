package com.imnstudios.riafytest.maintest.ui

import com.imnstudios.riafytest.maintest.data.models.ModelDataClass

interface ListenerInterface {


    fun onSuccess(post: ModelDataClass)
    fun onFailure(message: String)
}