package com.imnstudios.riafytest.maintest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imnstudios.riafytest.maintest.data.models.ModelDataClass
import com.imnstudios.riafytest.maintest.data.repositories.ApiException
import com.imnstudios.riafytest.maintest.data.repositories.Repository
import com.imnstudios.riafytest.utils.Coroutines
import kotlinx.coroutines.Job

class ViewModel(
    private val repository: Repository
) : ViewModel() {

    private lateinit var job: Job

    private val _posts = MutableLiveData<List<ModelDataClass>>()
    val posts: LiveData<List<ModelDataClass>>
        get() = _posts

    var listenerInterface: ListenerInterface? = null


    fun getPosts() {
        try {
            job = Coroutines.ioThenMain(
                { repository.getPosts() },
                {
                    _posts.value = it
                }
            )
        } catch (e: Exception) {
            listenerInterface?.onFailure(e.toString())
        }

    }


    fun postPost(model: ModelDataClass) {
        try {
            job = Coroutines.main {
                val resp = repository.postPost(model)
                resp.let {
                    //if return it is success
                    listenerInterface?.onSuccess(resp)
                    return@main
                }
            }
        } catch (e: ApiException) {
            listenerInterface?.onFailure(e.toString())
        }

    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}
