package com.example.simplechat.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplechat.databases.MessagesDatabase
import com.example.simplechat.databases.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideUsersDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            UsersDatabase::class.java,
            "messages_table"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMessageDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app.applicationContext,
            MessagesDatabase::class.java,
            "messages_table"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUsersDao(db: UsersDatabase) = db.usersDatabaseDao()

    @Singleton
    @Provides
    fun provideMessageDao(db: MessagesDatabase) = db.messageDatabaseDao()

}