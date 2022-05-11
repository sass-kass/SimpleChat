package com.example.simplechat.ui.fragments.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.firebase.Message
import com.example.simplechat.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(): ViewModel() {
    var chatsList: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

    var profileEmail: String = FirebaseAuth.getInstance().currentUser?.email!!

    fun getChats() {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("messages").whereEqualTo("from", profileEmail).get()
                .addOnSuccessListener { fromMessages ->
                    if (fromMessages != null) {
                        val listOfFromMessages: MutableList<Message> = fromMessages.toObjects(Message::class.java)
                        val uniqueChats = mutableSetOf<String>()
                        listOfFromMessages.forEach {
                            uniqueChats.add(it.from)
                        }
                        Firebase.firestore.collection("messages").whereEqualTo("to", profileEmail).get()
                            .addOnSuccessListener { toMessages ->
                                if (toMessages != null) {
                                    val listOfToMessages: MutableList<Message> = toMessages.toObjects(Message::class.java)
                                    listOfToMessages.forEach {
                                        uniqueChats.add(it.to)
                                    }
                                }
                            }
                        Firebase.firestore.collection("users").whereIn("email", uniqueChats.toList()).get()
                            .addOnSuccessListener { uniqueUsers ->
                                chatsList.postValue(uniqueUsers.toObjects(User::class.java))
                            }
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
        }
    }
}