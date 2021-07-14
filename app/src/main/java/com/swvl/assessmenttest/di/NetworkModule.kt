package com.swvl.assessmenttest.di

import com.moczul.ok2curl.CurlInterceptor
import com.swvl.assessmenttest.BuildConfig
import com.swvl.assessmenttest.api.endpoints.MoviesPicturesEndPoint
import com.swvl.assessmenttest.repository.MoviesPictureRepository
import com.swvl.assessmenttest.repository.MoviesRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

val networkModule = module {
        fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            chain.proceed(requestBuilder.build())
        }.addInterceptor(CurlInterceptor { message -> Timber.d(message) })

        return httpClient.build()
    }

    single { provideOkHttpClient() }



    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        provideRetrofit(get())
    }

    fun provideMoviesPictureApi(retrofit: Retrofit): MoviesPicturesEndPoint {
        return retrofit.create(MoviesPicturesEndPoint::class.java)
    }

    single { provideMoviesPictureApi(get()) }

    single { MoviesRepository( get(), get()) }

    single { MoviesPictureRepository( get()) }


}