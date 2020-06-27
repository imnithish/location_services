package com.imnstudios.riafytest.maintest.data.network

import com.imnstudios.riafytest.maintest.data.models.ModelDataClass
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {


    @GET("posts")
    suspend fun getPosts(): Response<List<ModelDataClass>>

    @FormUrlEncoded
    @POST("post")
    suspend fun postPost(
        @Field("title") title: String,
        @Field("desc") desc: String
    ): Response<ModelDataClass>


    companion object {
        operator fun invoke(): NetworkApi {

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://fastingconsole.us-east-1.elasticbeanstalk.com/kotlintest/")
                .build()
                .create(NetworkApi::class.java)
        }
    }

}