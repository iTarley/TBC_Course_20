package com.example.tbc_course_20.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbc_course_20.R
import com.example.tbc_course_20.databinding.FragmentLoginBinding
import com.example.tbc_course_20.databinding.FragmentWelcomeBinding
import com.example.tbc_course_20.extensions.Resource
import com.example.tbc_course_20.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginFragment : Fragment() {


    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.loginBtnConfirm.setOnClickListener {
            viewModel.logIn(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){

                viewModel.loginState.collect{
                    when(it){
                        is Resource.Error -> {
                            Snackbar.make(view, it.errorData, Snackbar.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Log.d("loading", "${it.loader}")
                        }
                        is Resource.Success -> {
                            Snackbar.make(view,
                                getString(R.string.welcome_login) + binding.emailEditText.text.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}