package com.mohamedrafat.firebasewithmvvmapp.ui.AuthFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mohamedrafat.firebasewithmvvmapp.R
import com.mohamedrafat.firebasewithmvvmapp.data.model.User
import com.mohamedrafat.firebasewithmvvmapp.databinding.FragmentRegisterBinding
import com.mohamedrafat.firebasewithmvvmapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.tvLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            mNavController.navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this listen to viewModel
        observer()

        binding.registerBtn.setOnClickListener {
            if (validation()) {
                val email = binding.emailEt.text.toString()
                val password = binding.passEt.text.toString()
                val user = getUserObj()

                viewModel.registerUser(email, password, user)
            }
        }

    }


    fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.registerProgress.show()
                    binding.registerBtn.text = ""
                }
                is Resource.Failure -> {
                    binding.registerProgress.hide()
                    toast(resource.error)
                    binding.registerBtn.text = "Register"
                }
                is Resource.Success -> {
                    binding.registerProgress.hide()
                    toast(resource.data)
                    binding.registerBtn.text = "Register"
                    val action = RegisterFragmentDirections.actionRegisterFragmentToNoteListingFragment()
                    mNavController.navigate(action)
                }
            }
        }
    }


    fun getUserObj(): User {
        return User(
            id = "",
            first_name = binding.firstNameEt.text.toString(),
            last_name = binding.lastNameEt.text.toString(),
            job_title = binding.jobTitleEt.text.toString(),
            email = binding.emailEt.text.toString(),
        )
    }


    fun validation(): Boolean {
        var isValid = true

        if (binding.firstNameEt.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_first_name))
        }

        if (binding.lastNameEt.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_last_name))
        }

        if (binding.jobTitleEt.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_job_title))
        }

        if (binding.emailEt.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_email))
        } else {
            if (!binding.emailEt.text.toString().isValidEmail()) {
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passEt.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_password))
        } else {
            if (binding.passEt.text.toString().length < 8) {
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}