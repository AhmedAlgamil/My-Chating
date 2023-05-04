package com.example.mychating.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mychating.api.ChatApiServices
import com.example.mychating.base.BaseViewModel
import com.example.mychating.models.DataInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(application: Application) : BaseViewModel(application) {

    private var loginViwModelScope: Job = Job()
    val phone = MutableLiveData<String?>()

    val password = MutableLiveData<String?>()

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val uiScope = CoroutineScope(Dispatchers.Main + loginViwModelScope)

    init {
//        checkAndMakeLogin()
//        makeLogin()
    }

    fun onClear() {
        phone.value = null
        password.value = null
    }

    private fun checkAndMakeLogin() {
        ChatApiServices.retrofitGetAllUsers.getAllUsers().enqueue(object : Callback<DataInfo> {
            override fun onFailure(call: Call<DataInfo>, t: Throwable) {
//                _response.value = "Failure: " + t.message
                Log.d("this is error ", t.message.toString())
            }

            override fun onResponse(
                call: Call<DataInfo>,
                response: Response<DataInfo>
            ) {
                Log.d("this is user ", response.body()!!.data.get(0).toString())
            }
        })
    }

     fun makeLogin() {
        viewModelScope.launch {
            ChatApiServices.retrofitGetAllUsers.makeLogin(
                phone.value.toString(), password.value.toString()
            ).enqueue(
                object : Callback<DataInfo> {
                    override fun onResponse(call: Call<DataInfo>, response: Response<DataInfo>) {
                        showToast.value = response.body()?.message.toString()
                        var regstr = "(\\+\\d( )?)?([-\\( ]\\d{3}[-\\) ])( )?\\d{3}-\\d{4}"
//            if(regstr.toRegex().find(userdata.phone_number)?.value)
                        println("matched: " + regstr.toRegex().find(response.body()?.data!!.get(0).phone_number)?.value)
                    }

                    override fun onFailure(call: Call<DataInfo>, t: Throwable) {
                        Log.d("this is error ", t.message.toString())
                    }
                }
            )
        }
    }

    fun validateLogin(userdata :DataInfo.UsersInfo)
    {
        if(userdata.phone_number.isEmpty())
        {
            showError.value = "phone number is Required"
        }
        else{
            var regstr = "(\\+\\d( )?)?([-\\( ]\\d{3}[-\\) ])( )?\\d{3}-\\d{4}"
//            if(regstr.toRegex().find(userdata.phone_number)?.value)
            println("matched: " + regstr.toRegex().find(userdata.phone_number)?.value)
        }
        if(userdata.password.isEmpty())
        {
            showError.value = "password number is Required"
        }
    }

}