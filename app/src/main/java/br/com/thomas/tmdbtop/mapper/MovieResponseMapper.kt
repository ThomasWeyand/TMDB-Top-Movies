package br.com.thomas.tmdbtop.mapper

import br.com.thomas.tmdbtop.domain.network.DiscoverMovieResponse
import timber.log.Timber

class MovieResponseMapper : NetworkResponseMapper<DiscoverMovieResponse> {
    override fun onLastPage(response: DiscoverMovieResponse): Boolean {
        Timber.d("loadPage : ${response.page}/${response.total_pages}")
        return response.page > response.total_pages
    }
}