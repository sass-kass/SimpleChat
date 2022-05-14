package com.example.simplechat.repository

import com.example.simplechat.databases.daos.MessageDao
import com.example.simplechat.databases.daos.UserDao
import com.example.simplechat.databases.data.MessageItem
import com.example.simplechat.databases.data.UserItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val userDao: UserDao, private val messageDao: MessageDao) {

    suspend fun insertUsers(users: List<UserItem>) = userDao.insert(users)

    fun getAllUsers() = userDao.getAllUsers()

    suspend fun getUsers(users: List<String>) = userDao.getUsers(users)

    suspend fun getUser(user: String) = userDao.getUser(user)

    suspend fun insertMessages(messages: List<MessageItem>) = messageDao.insert(messages)

    fun getAllMessages() = messageDao.getAllMessages()

    suspend fun getMessagesByChatUserEmail(email: String) = messageDao.getMessagesByChatUserEmail(email)

    suspend fun getUsersByEmailFrom(email: String) = messageDao.getUsersByEmailFrom(email)

    suspend fun getUsersByEmailTo(email: String) = messageDao.getUsersByEmailTo(email)

}