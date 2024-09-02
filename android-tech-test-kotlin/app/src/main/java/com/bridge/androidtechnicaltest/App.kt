package com.bridge.androidtechnicaltest

import android.app.Application
import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilRepository
import com.bridge.androidtechnicaltest.di.DatabaseFactory
import com.bridge.androidtechnicaltest.di.databaseModule
import com.bridge.androidtechnicaltest.di.networkModule
import com.bridge.androidtechnicaltest.di.repositoryModule
import com.bridge.androidtechnicaltest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class App : Application() {

    private val appComponent : MutableList<Module> = mutableListOf(networkModule, databaseModule,  repositoryModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
//            modules(databaseModule, networkModule, viewModelModule)
            modules(listOf(networkModule, databaseModule, repositoryModule, viewModelModule))

        }
    }
}


