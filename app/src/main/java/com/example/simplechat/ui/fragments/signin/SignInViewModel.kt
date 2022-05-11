package com.example.simplechat.ui.fragments.signin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplechat.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    var requestResult: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun signInWithEmailAndPasswordCredential(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        requestResult.postValue(true)
                    } else {
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        requestResult.postValue(false)
                    }
                }
        }
    }
}