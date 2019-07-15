package br.com.thomas.tmdbtop.di

import android.support.annotation.NonNull
import br.com.thomas.tmdbtop.api.LiveDataCallAdapterFactory
import br.com.thomas.tmdbtop.api.RequestInterceptor
import br.com.thomas.tmdbtop.api.TopListService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideDiscoverService(@NonNull retrofit: Retrofit): TopListService {
        return retrofit.create(TopListService::class.java)
    }

}