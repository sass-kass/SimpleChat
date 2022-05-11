package com.example.simplechat.ui.fragments.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.firebase.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {
    var messageList: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()

    var profileEmail: String = FirebaseAuth.getInstance().currentUser?.email!!

    lateinit var companionEmail: String

    fun setEmail(email: String) {
        companionEmail = email
    }

    fun getMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("messages").whereEqualTo("from", profileEmail).whereEqualTo("to", companionEmail).get()
                .addOnSuccessListener { messagesFrom ->
                    if (messagesFrom != null) {
                        val listOfMessages: MutableList<Message> = messagesFrom.toObjects(Message::class.java)
                        Firebase.firestore.collection("messages").whereEqualTo("from", companionEmail).whereEqualTo("to", profileEmail).get()
                            .addOnSuccessListener { messagesTo ->
                                listOfMessages.addAll(messagesTo.toObjects(Message::class.java))
                                messageList.postValue(listOfMessages)
                            }
                    }
                }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("messages").document().set(Message(Calendar.getInstance().time, profileEmail, message, companionEmail))
        }
    }
}