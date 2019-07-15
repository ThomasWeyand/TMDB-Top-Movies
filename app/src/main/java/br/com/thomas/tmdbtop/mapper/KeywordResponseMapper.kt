package br.com.thomas.tmdbtop.mapper

import br.com.thomas.tmdbtop.domain.network.KeywordListResponse

class KeywordResponseMapper : NetworkResponseMapper<KeywordListResponse> {
    override fun onLastPage(response: KeywordListResponse): Boolean {
        return true
    }
}