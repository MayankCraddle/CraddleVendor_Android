package com.cradlevendor.viewmodel

import androidx.lifecycle.*
import com.cradlevendor.repository.QuoteRepository


class MainViewModelFactory (private val repository: QuoteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}