package com.example.mychating.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mychating.base.BaseFragment
import com.example.mychating.base.BaseViewModel
import com.example.mychating.databinding.FragmentLoginBinding
import com.example.mychating.databinding.FragmentLoginBinding.inflate

class LoginFragment : BaseFragment() {

    private lateinit var binding : FragmentLoginBinding
    private val usersViewModel: UsersViewModel by lazy {
        ViewModelProvider(this).get(UsersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
        usersViewModel.onClear()
    }

    override val _viewModel: UsersViewModel by lazy {
        ViewModelProvider(this).get(UsersViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate(inflater,container,false)
        binding.loginViewModel = usersViewModel
        binding.goToSignUp.setOnClickListener {
            this.findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            )
        }
        binding.btnLogin.setOnClickListener {
            usersViewModel.makeLogin()
        }
        return binding.root
    }

}