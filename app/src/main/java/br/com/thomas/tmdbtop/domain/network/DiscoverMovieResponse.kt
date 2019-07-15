package br.com.thomas.tmdbtop.domain.network

import br.com.thomas.tmdbtop.domain.models.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel