package com.imnstudios.riafytest.maintest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imnstudios.riafytest.maintest.data.repositories.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(

    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return com.imnstudios.riafytest.maintest.ui.ViewModel(repository) as T

    }
}