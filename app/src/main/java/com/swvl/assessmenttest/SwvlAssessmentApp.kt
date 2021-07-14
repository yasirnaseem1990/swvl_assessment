package com.swvl.assessmenttest

import android.app.Application
import com.swvl.assessmenttest.di.databaseModule
import com.swvl.assessmenttest.di.networkModule
import com.swvl.assessmenttest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import org.koin.core.logger.Level

class SwvlAssessmentApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin{
            // set log level to error due to crash in kotlin 1.5 with koin https://github.com/InsertKoinIO/koin/issues/1076
            androidLogger(Level.ERROR)
            androidContext(this@SwvlAssessmentApp)
            modules(listOf(
                networkModule,
                viewModelModule,
                databaseModule))
        }
    }
}