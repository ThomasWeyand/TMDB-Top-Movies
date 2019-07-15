package br.com.thomas.tmdbtop.api

import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", "9382e3ae2d2b1d5d41d483e3f7b69b91")
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}