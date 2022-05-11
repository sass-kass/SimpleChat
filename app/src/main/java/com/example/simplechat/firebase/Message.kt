package com.example.simplechat.firebase

import java.util.*

data class Message(
    var createdAt: Date = Date(),
    var from: String = "",
    var text: String = "",
    var to: String = ""
)
