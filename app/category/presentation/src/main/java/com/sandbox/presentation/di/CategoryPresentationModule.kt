package com.sandbox.presentation.di

import co.touchlab.kermit.Logger
import com.sandbox.presentation.CategoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoryPresentationModule = module {

    single<Logger> { Logger }

    viewModelOf(::CategoryViewModel)

}