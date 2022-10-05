package com.colisa.quick.core.data.service.di

import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.LogService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.data.service.impl.AccountServiceImpl
import com.colisa.quick.core.data.service.impl.LogServiceImpl
import com.colisa.quick.core.data.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun providesLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun providesStorageService(impl: StorageServiceImpl): StorageService
}