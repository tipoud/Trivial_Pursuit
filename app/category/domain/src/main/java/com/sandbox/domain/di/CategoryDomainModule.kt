package com.sandbox.domain.di

import com.sandbox.domain.GetCategoryUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val categoryDomainModule = module {
    factoryOf(::GetCategoryUseCase)
}