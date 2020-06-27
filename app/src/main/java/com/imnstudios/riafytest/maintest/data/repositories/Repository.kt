package com.imnstudios.riafytest.maintest.data.repositories

import com.imnstudios.riafytest.maintest.data.models.ModelDataClass
import com.imnstudios.riafytest.maintest.data.network.NetworkApi

class Repository(
    private val api: NetworkApi

) : SafeApiRequest() {

    suspend fun getPosts() = apiRequest { api.getPosts() }

    suspend fun postPost(model: ModelDataClass) =
        apiRequest { api.postPost(model.title, model.desc) }

}
