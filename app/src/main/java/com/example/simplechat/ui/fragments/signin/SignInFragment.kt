package com.example.simplechat.ui.fragments.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.simplechat.databinding.SignInFragmentBinding
import com.example.simplechat.ui.activities.ChatActivity
import com.example.simplechat.ui.fragments.signup.SignUpFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment: Fragment() {
    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            binding.loginButton.isClickable = false
            viewModel.signInWithEmailAndPasswordCredential(binding.emailLogin.text.toString(), binding.passwordLogin.text.toString())
        }

        viewModel.requestResult.observe(viewLifecycleOwner) {
            if (viewModel.requestResult.value == true) {
                val action = SignInFragmentDirections.actionSignInFragmentToChatActivity()
                findNavController().navigate(action)
            } else {
                Toast.makeText( this.context, "Error, try again later", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}