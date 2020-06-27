package com.imnstudios.riafytest.maintest.data.network

import com.imnstudios.riafytest.maintest.data.models.ModelDataClass
import com.imnstudios.riafytest.maintest.data.models.ModelDataClassGet
import com.imnstudios.riafytest.maintest.data.models.PostResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {


    @GET("kotlintest")
    suspend fun getPosts(): Response<List<ModelDataClassGet>>


    @POST("kotlintest")
    suspend fun postPost(
        @Body body: ModelDataClass
    ): PostResponse

    companion object {
        operator fun invoke(): NetworkApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://fastingconsole.us-east-1.elasticbeanstalk.com/")
                .build()
                .create(NetworkApi::class.java)
        }
    }

}