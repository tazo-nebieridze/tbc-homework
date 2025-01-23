package com.example.homeworkstbc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.homeworkstbc.databinding.FragmentMainBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val inflater : (LayoutInflater, ViewGroup?, Boolean ) -> VB
) : Fragment () {

    private var _binding: VB? = null
    protected val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    protected abstract fun start ( )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = inflater(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}