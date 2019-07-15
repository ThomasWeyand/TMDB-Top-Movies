package br.com.thomas.tmdbtop.domain.network

data class KeywordListResponse(
    val id: Int,
    val keywords: List<Keyword>
) : NetworkResponseModel