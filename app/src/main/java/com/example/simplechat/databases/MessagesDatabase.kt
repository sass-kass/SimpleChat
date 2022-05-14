package com.example.simplechat.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplechat.databases.daos.MessageDao
import com.example.simplechat.databases.data.MessageItem

@Database(entities = [MessageItem::class], version = 1, exportSchema = false)
abstract class MessagesDatabase(): RoomDatabase() {
    abstract fun messageDatabaseDao(): MessageDao

}