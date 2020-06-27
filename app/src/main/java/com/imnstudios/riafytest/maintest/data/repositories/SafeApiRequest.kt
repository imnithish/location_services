package com.imnstudios.riafytest.maintest.data.repositories

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

const val DEBUG_TAG = "ERORORORO"

abstract class SafeApiRequest {


    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody().toString()
            val message = StringBuilder()

            error.let {
                try {
                    message.append(JSONObject(it).getString("message"))

                } catch (e: JSONException) {
                }
                message.append("\n")

            }
            message.append("Error Code :${response.code()}")
            Log.d(DEBUG_TAG, message.toString())

            throw  ApiException(
                message.toString()
            )
        }

    }

    suspend fun <T> postApiRequest(responseFunction: suspend () -> T): T? {
        return try {
            responseFunction.invoke()
        } catch (e: Exception) {
            Log.d(DEBUG_TAG, e.toString())
            e.printStackTrace()
            null
        }
    }
}


class ApiException(message: String) : IOException(message) {
    override fun printStackTrace() {
        super.printStackTrace()
        Log.d(DEBUG_TAG, message.toString())
    }
}