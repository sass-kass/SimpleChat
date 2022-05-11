package com.example.simplechat.ui.fragments.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.firebase.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(): ViewModel() {
    var usersList: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("users").get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        usersList.postValue(document.toObjects(User::class.java))
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
        }
    }
}