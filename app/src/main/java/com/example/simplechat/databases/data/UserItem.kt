package com.example.simplechat.databases.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserItem (
    @PrimaryKey
    var email: String = "",
    @ColumnInfo(name = "image")
    var image: String = "",
    @ColumnInfo(name = "nickname")
    var nickname: String = ""
        )