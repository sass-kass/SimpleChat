package com.example.simplechat.databases.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplechat.databases.data.MessageItem
import com.example.simplechat.databases.data.UserItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: List<UserItem>)

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Flow<List<UserItem>>

    @Query("SELECT * FROM users_table WHERE email IN(:users)")
    suspend fun getUsers(users: List<String>)

    @Query("SELECT * FROM users_table WHERE email=:userEmail")
    suspend fun getUser(userEmail: String): Flow<UserItem>
}