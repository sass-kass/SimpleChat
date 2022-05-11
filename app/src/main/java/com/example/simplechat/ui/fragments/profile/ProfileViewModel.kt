package com.example.simplechat.ui.fragments.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {
    var requestResult: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    var profileResult: MutableLiveData<User> = MutableLiveData<User>()

    var profileEmail: String = FirebaseAuth.getInstance().currentUser?.email!!

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance().signOut()
            requestResult.postValue(true)
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("users").whereEqualTo("email", profileEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        profileResult.postValue(document.first().toObject(User::class.java))
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
        }
    }
}