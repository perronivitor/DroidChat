package com.example.droidchat.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.droidchat.data.database.DroidChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): DroidChatDatabase {
        return Room.databaseBuilder(
            context,
            DroidChatDatabase::class.java,
            "droidchat_database"
        ).build()
    }
}