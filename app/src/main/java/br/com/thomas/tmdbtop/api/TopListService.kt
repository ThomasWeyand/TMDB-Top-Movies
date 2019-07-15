package br.com.thomas.tmdbtop.api

import android.arch.lifecycle.LiveData
import br.com.thomas.tmdbtop.domain.network.DiscoverMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopListService {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int, @Query("language") language: String):
            LiveData<ApiResponse<DiscoverMovieResponse>>

}