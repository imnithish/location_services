package com.imnstudios.riafytest.maintest.ui

import com.imnstudios.riafytest.maintest.data.models.PostResponse

interface ListenerInterface {


    fun onSuccess(post: PostResponse?)
    fun onFailure(message: String)
}