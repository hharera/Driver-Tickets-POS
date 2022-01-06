package com.englizya.repository.di

import com.englizya.repository.UserRepository
import com.englizya.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
}