package com.example.droidchat.data.database.di

import com.example.droidchat.data.database.DataBaseDataSource
import com.example.droidchat.data.database.DataBaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseDataSourceModule {

    @Binds
    @Singleton
    fun bindDataBaseDataSource(
        dataBaseDataSourceImpl: DataBaseDataSourceImpl
    ): DataBaseDataSource

}