package com.example.tbc_course_20.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_course_20.extensions.Resource
import com.example.tbc_course_20.models.login.Login
import com.example.tbc_course_20.models.login.LoginModel
import com.example.tbc_course_20.models.register.Register
import com.example.tbc_course_20.models.register.RegisterModel
import com.example.tbc_course_20.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Register>>(Resource.Loading(true))
    val registerState = _registerState.asStateFlow()


    fun register(email: String,password: String) {
        viewModelScope.launch {
            loginResponse(email = email, password = password).collect{
                _registerState.value = it
            }
        }
    }

    private fun loginResponse(email: String,password: String) = flow<Resource<Register>> {
        emit(Resource.Loading(true))
        try {
            val response = RetrofitClient.getInformation().getRegister(RegisterModel(email = email,password = password))
            if(response.isSuccessful){
                val body = response.body()
                Log.d("response", "login: ${body}")
                emit(Resource.Success(body!!))
            }
            else{
                val error = response.errorBody()?.string()
                Log.d("responseER", "login: $error")
                emit(Resource.Error(error!!))

            }

        }catch (e:Throwable){
            emit(Resource.Error(e.toString()))
        }


    }
}