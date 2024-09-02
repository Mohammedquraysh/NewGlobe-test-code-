package com.bridge.androidtechnicaltest.di

import com.bridge.androidtechnicaltest.db.IPupilRepository
import com.bridge.androidtechnicaltest.db.PupilRepository
import com.bridge.androidtechnicaltest.viewmodel.PupilViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module



val networkModule = module {
    factory { PupilAPIFactory.retrofitPupil() }
}

val databaseModule = module {
    single { DatabaseFactory.getDBInstance(get()).pupilDao } // Provide PupilDao
}

val repositoryModule = module {
    single<IPupilRepository> { PupilRepository(get(), get()) }
    // Passing both PupilDao and PupilAPI
}

val viewModelModule = module {
    viewModel { PupilViewModel(get()) }
    // PupilViewModel will get the repository injected
}
