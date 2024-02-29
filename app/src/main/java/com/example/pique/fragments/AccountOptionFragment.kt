package com.example.pique.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pique.R
import com.example.pique.databinding.FragmentAccountOptionBinding

class AccountOptionFragment : Fragment(R.layout.fragment_account_option) {

    private lateinit var binding: FragmentAccountOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAccountOptionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAccountLogin.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionFragment_to_loginFragment)
        }
        binding.btnAccountRegister.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionFragment_to_registerFragment)
        }
    }

}