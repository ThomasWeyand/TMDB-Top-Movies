package br.com.thomas.tmdbtop.utils

import android.arch.lifecycle.LiveData

class AbsentLiveData<T> : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create() = AbsentLiveData<T>()
    }
}