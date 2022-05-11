package com.example.simplechat.ui.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simplechat.databinding.StartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment: Fragment() {

    private var _binding: StartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StartFragmentBinding.inflate(inflater, container, false)

        binding.loginIn.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        binding.registerIn.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}