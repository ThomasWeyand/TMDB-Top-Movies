package br.com.thomas.tmdbtop.mapper

import br.com.thomas.tmdbtop.domain.network.NetworkResponseModel

interface NetworkResponseMapper<in FROM : NetworkResponseModel> {
    fun onLastPage(response: FROM): Boolean
}