package br.com.thomas.tmdbtop.domain.network

data class ErrorEnvelope(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)