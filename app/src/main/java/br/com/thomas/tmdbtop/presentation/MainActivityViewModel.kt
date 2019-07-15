package br.com.thomas.tmdbtop.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import br.com.thomas.tmdbtop.domain.Resource
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.repository.DiscoverRepository
import br.com.thomas.tmdbtop.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel(){

    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
    val movieListLiveData: LiveData<Resource<List<Movie>>>

    init {
        Timber.d("injection MainActivityViewModel")

        movieListLiveData = moviePageLiveData.switchMap {
            moviePageLiveData.value?.let { discoverRepository.loadMovies(it) }
                ?: AbsentLiveData.create()
        }
    }
    inline fun <X, Y> LiveData<X>.switchMap(
        crossinline transform: (X) -> LiveData<Y>
    ): LiveData<Y> = Transformations.switchMap(this) { transform(it) }

    public fun getMovieListValues() : LiveData<Resource<List<Movie>>>{
        return movieListLiveData
    }

    fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

}