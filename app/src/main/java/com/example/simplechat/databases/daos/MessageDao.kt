package com.example.simplechat.databases.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplechat.databases.data.MessageItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: List<MessageItem>)

    @Query("SELECT * FROM messages_table")
    fun getAllMessages(): Flow<List<MessageItem>>

    @Query("SELECT * FROM messages_table WHERE `to`=:userEmail OR `from`=:userEmail")
    suspend fun getMessagesByChatUserEmail(userEmail: String): Flow<List<MessageItem>>

    @Query("SELECT `from` FROM messages_table WHERE `to`=:userEmail")
    suspend fun getUsersByEmailFrom(userEmail: String): Flow<List<String>>

    @Query("SELECT `to` FROM messages_table WHERE `from`=:userEmail")
    suspend fun getUsersByEmailTo(userEmail: String): Flow<List<String>>
}