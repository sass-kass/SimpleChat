package com.example.simplechat.ui.fragments.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.simplechat.R
import com.example.simplechat.databinding.SignUpFragmentBinding
import com.example.simplechat.ui.fragments.signin.SignInFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment() {
    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    private val REQUEST_CODE = 1

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            binding.usersImage.load(data?.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)

        binding.chooseImageButton.setOnClickListener {
            openGalleryForImage()
        }

        binding.registerButton.setOnClickListener {
            binding.registerButton.isClickable = false
            viewModel.signUpWithEmailAndPasswordCredential(binding.emailRegister.text.toString(), binding.usersImage.drawable.toString(), binding.nicknameRegister.text.toString())
        }

        viewModel.requestResult.observe(viewLifecycleOwner) {
            if (viewModel.requestResult.value == true) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToChatActivity()
                findNavController().navigate(action)
            } else {
                Toast.makeText( this.context, "Error, try again later", Toast.LENGTH_SHORT).show()
                binding.registerButton.isClickable = true
            }
        }

        return binding.root
    }
}