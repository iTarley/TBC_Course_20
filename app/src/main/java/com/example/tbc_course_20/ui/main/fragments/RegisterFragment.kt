package com.example.tbc_course_20.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbc_course_20.R
import com.example.tbc_course_20.databinding.FragmentRegisterBinding
import com.example.tbc_course_20.extensions.Resource
import com.example.tbc_course_20.viewModel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {


    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.finalRegisterBtn.setOnClickListener {
            if (binding.emailEditText.text!!.isNotEmpty() || binding.passwordEditText.text!!.isNotEmpty()) {
                viewModel.register(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            } else {
                Snackbar.make(view, getString(R.string.fill_inputs), Snackbar.LENGTH_SHORT).show()
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    when (it) {
                        is Resource.Error -> {
                            Snackbar.make(view, it.errorData, Snackbar.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            Log.d("loading", "${it.loader}")
                        }
                        is Resource.Success -> {
                            Snackbar.make(
                                view,
                                getString(R.string.success_register) + binding.emailEditText.text.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
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