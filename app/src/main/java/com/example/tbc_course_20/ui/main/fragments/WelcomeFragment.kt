package com.example.tbc_course_20.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbc_course_20.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {



    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            loginBtn.setOnClickListener{
              findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
            }
            registerBtn.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}