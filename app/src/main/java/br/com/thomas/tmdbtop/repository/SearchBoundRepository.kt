package br.com.thomas.tmdbtop.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.Nullable
import android.support.annotation.WorkerThread
import br.com.thomas.tmdbtop.api.ApiResponse
import br.com.thomas.tmdbtop.domain.Resource
import br.com.thomas.tmdbtop.domain.network.NetworkResponseModel
import br.com.thomas.tmdbtop.mapper.NetworkResponseMapper
import timber.log.Timber

abstract class SearchBoundRepository <ResultType,
        RequestType : NetworkResponseModel,
        Mapper : NetworkResponseMapper<RequestType>>
internal constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        Timber.d("Injection NetworkBoundRepository")
        fetchSearchMovies()
    }

    private fun fetchSearchMovies() {
        setValue(Resource.loading(null))
        val apiResponse = fetchService()
        result.addSource(apiResponse) { response ->
            response?.let {
                when (response.isSuccessful){
                    true -> {
                        response.body?.let{
                            val loaded = loadFromWeb(it)
                            result.addSource(loaded) { newData ->
                                newData?.let {
                                    setValue(Resource.success(newData, mapper().onLastPage(response.body)))
                                }
                            }
                        }
                    }
                    false -> {
                        onFetchFailed(response.message)
                        response.message?.let {
                            val failed = loadFromWeb(null)
                            result.addSource<ResultType>(failed) { newData ->
                                setValue(Resource.error(it, newData))
                            }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun loadFromWeb(@Nullable items: RequestType?) : LiveData<ResultType>

    @MainThread
    protected abstract fun mapper(): Mapper

    @MainThread
    protected abstract fun onFetchFailed(message: String?)
}