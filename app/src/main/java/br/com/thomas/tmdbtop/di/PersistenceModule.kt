package br.com.thomas.tmdbtop.di

import android.app.Application
import android.arch.persistence.room.Room
import android.support.annotation.NonNull
import br.com.thomas.tmdbtop.room.AppDatabase
import br.com.thomas.tmdbtop.room.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "TheMovies.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull database: AppDatabase): MovieDao {
        return database.movieDao()
    }
}