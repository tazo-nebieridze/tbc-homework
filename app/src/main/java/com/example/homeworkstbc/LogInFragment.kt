package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.homeworkstbc.databinding.FragmentLogInBinding
import com.example.homeworkstbc.databinding.FragmentRegisterBinding

class LogInFragment : Fragment() {

    private var binding : FragmentLogInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater,container,false)


        binding?.goBackToMain?.setOnClickListener {
            goBackToMainFragment()
        }
        binding?.signUp?.setOnClickListener {
            attachRegisterFragment()
        }


        return binding?.root
    }
    private fun goBackToMainFragment() {

//        parentFragmentManager.popBackStack()

//        parentFragmentManager.popBackStack(
//            "mainFragment",
//            0
//        )

//        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, MainFragment(), "mainFragment")
        transaction.commit()
    }


    private fun attachRegisterFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.main, RegisterFragment(),"registerFragment")
        transaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}