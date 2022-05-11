package com.example.simplechat.ui.fragments.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.firebase.User
import com.example.simplechat.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val dataSource: ChatRepository) : ViewModel() {

    var requestResult: MutableLiveData<Boolean> = MutableLiveData<Boolean>();

    fun signUpWithEmailAndPasswordCredential(email: String, password: String, nickname: String, image: String = "R.drawable.ic_baseline_broken_image_24") {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "createUserWithEmail:success")
                        Firebase.firestore.collection("users").document()
                            .set(User(email, image, nickname), SetOptions.merge())
                            .addOnCompleteListener {
                                requestResult.postValue(true)
                            }
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        requestResult.postValue(false)
                    }
                }
        }
    }
}