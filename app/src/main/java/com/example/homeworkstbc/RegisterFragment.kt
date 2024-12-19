package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding
import com.example.homeworkstbc.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var binding : FragmentRegisterBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        binding?.goBackToMain?.setOnClickListener {
            goBackToMainFragment()
        }
        binding?.rememberMe?.setOnClickListener {
            binding?.rememberMeCheckBox?.isChecked = !binding?.rememberMeCheckBox?.isChecked!!
        }
        binding?.signIn?.setOnClickListener {
            attachLoginFragment()
        }

        return binding?.root
    }

    private fun goBackToMainFragment() {
//        parentFragmentManager.popBackStack()

//
//        parentFragmentManager.popBackStack(
//            "mainFragment",
//            0
//        )


//        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, MainFragment(), "mainFragment")
        transaction.commit()
    }

    private fun attachLoginFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.main, LogInFragment(),"loginFragment")
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}