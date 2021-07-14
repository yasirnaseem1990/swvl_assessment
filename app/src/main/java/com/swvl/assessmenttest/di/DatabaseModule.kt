package com.swvl.assessmenttest.di

import android.app.Application
import androidx.room.Room
import com.swvl.assessmenttest.api.db.MoviesDatabase
import com.swvl.assessmenttest.api.db.MoviesListDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): MoviesDatabase {
       return Room.databaseBuilder(application, MoviesDatabase::class.java, "swval_movies_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideMoviesDao(database: MoviesDatabase): MoviesListDao {
        return  database.moviesListDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideMoviesDao(get()) }


}
