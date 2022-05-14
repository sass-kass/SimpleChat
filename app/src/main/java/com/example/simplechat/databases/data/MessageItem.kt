package com.example.simplechat.databases.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "messages_table")
data class MessageItem (
    @PrimaryKey
    var createdAt: Date = Date(),
    @ColumnInfo(name = "from")
    var from: String = "",
    @ColumnInfo(name = "text")
    var text: String = "",
    @ColumnInfo(name = "to")
    var to: String = ""
        )