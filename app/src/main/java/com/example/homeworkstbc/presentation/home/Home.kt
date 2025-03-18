package com.example.homeworkstbc.presentation.home

import androidx.fragment.app.viewModels
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.presentation.base.BaseFragment
import com.example.homeworkstbc.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun start() {

    }


}
