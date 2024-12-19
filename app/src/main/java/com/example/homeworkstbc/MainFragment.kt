package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding


class MainFragment : Fragment() {

    private var binding : FragmentMainFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainFragmentBinding.inflate(inflater,container,false)

        binding?.signUpButtonMain?.setOnClickListener {
            attachRegisterFragment()
        }


        binding?.signInWithPassword?.setOnClickListener {
            attachLoginFragment()
        }


        return binding?.root
    }

    private fun attachRegisterFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.main, RegisterFragment(),"registerFragment")
        transaction.addToBackStack("registerFragment")
        transaction.commit()
    }

    private fun attachLoginFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        transaction.replace(R.id.main, LogInFragment(),"loginFragment")
        transaction.addToBackStack("loginFragment")
        transaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}