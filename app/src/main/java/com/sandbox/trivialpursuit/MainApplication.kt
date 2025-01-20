package com.sandbox.trivialpursuit

import android.app.Application
import com.sandbox.data.di.categoryDataModule
import com.sandbox.domain.di.categoryDomainModule
import com.sandbox.presentation.di.categoryPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(
                categoryPresentationModule,
                categoryDomainModule,
                categoryDataModule
            )
        }
    }

}