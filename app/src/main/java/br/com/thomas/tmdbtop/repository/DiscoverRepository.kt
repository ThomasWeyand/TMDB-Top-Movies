package br.com.thomas.tmdbtop.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.com.thomas.tmdbtop.api.ApiResponse
import br.com.thomas.tmdbtop.api.TopListService
import br.com.thomas.tmdbtop.domain.Resource
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.domain.models.SearchModel
import br.com.thomas.tmdbtop.domain.network.DiscoverMovieResponse
import br.com.thomas.tmdbtop.mapper.MovieResponseMapper
import br.com.thomas.tmdbtop.room.MovieDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepository @Inject
constructor(
    val topListService: TopListService,
    val movieDao: MovieDao
) : Repository {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    fun loadMovies(page: Int): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, DiscoverMovieResponse, MovieResponseMapper>() {

            override fun saveFetchData(items: DiscoverMovieResponse) {
                for (item in items.results) {
                    item.page = page
                }
                movieDao.insertMovieList(movies = items.results)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return movieDao.getMovieList(page_ = page)
            }

            override fun fetchService(): LiveData<ApiResponse<DiscoverMovieResponse>> {
                return topListService.getTopRatedMovies(page = page, language = "pt-BR")
            }

            override fun mapper(): MovieResponseMapper {
                return MovieResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed $message")
            }
        }.asLiveData()
    }

    fun loadSearchedMovies(searchModel: SearchModel) : LiveData<Resource<List<Movie>>> {
        return object : SearchBoundRepository<List<Movie>, DiscoverMovieResponse, MovieResponseMapper>() {
            override fun fetchService(): LiveData<ApiResponse<DiscoverMovieResponse>> {
                return topListService.getSearchedMovies(searched = searchModel.searched, include = true,
                    page = searchModel.page, language = "pt-BR")
            }

            override fun loadFromWeb(items: DiscoverMovieResponse?): LiveData<List<Movie>> {
                var itemsLive: MutableLiveData<List<Movie>> = MutableLiveData()
                itemsLive.setValue(items?.results)
                return itemsLive
            }

            override fun mapper(): MovieResponseMapper {
                return MovieResponseMapper()
            }

            override fun onFetchFailed(message: String?) {
                Timber.d("onFetchFailed $message")
            }
        }.asLiveData()
    }
}