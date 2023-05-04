package com.example.mychating.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mychating.base.BaseViewModel
import kotlinx.coroutines.Job

class SignUpViewModel(application: Application) : BaseViewModel(application) {

    private var signUpViwModelScope: Job = Job()

    val phone = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    val fullName = MutableLiveData<String?>()
    val emailAddress = MutableLiveData<String?>()
    val imageURL = MutableLiveData<String?>()



}