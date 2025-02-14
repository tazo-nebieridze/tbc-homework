package com.example.homeworkstbc.fragments

import Resource
import ItemsDto
import android.util.Log

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homeworkstbc.adapters.ItemsViewPagerAdapter
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.example.homeworkstbc.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun start() {
        observeItems()
        viewModel.fetchItems()
    }

    private fun observeItems() {
        lifecycleScope.launch {
            viewModel.itemsFlow.collect { resource ->
                when (resource) {
                    is Resource.Idle -> {
                        Log.d("MainFragment", "Idle")
                    }
                    is Resource.Loading -> {
                        Log.d("MainFragment", "Loading")
                    }
                    is Resource.Success -> {
                        setupViewPager(resource.data)
                        Log.d("MainFragment", "Success -> ${resource.data}")
                    }
                    is Resource.Error -> {
                        Log.d("MainFragment", "Error -> ${resource.message}")
                    }
                    else ->{

                    }
                }
            }
        }
    }

    private fun setupViewPager(items: List<ItemsDto>) {
        val adapter = ItemsViewPagerAdapter(items)
        with(binding) {
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 3
            viewPager.setPageTransformer { page, position ->
                val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
                page.scaleY = scaleFactor
                page.alpha = 0.5f + (1 - Math.abs(position)) * 0.5f
                val pageMarginPx = 40
                when { position < -1 -> { page.translationX = -page.width.toFloat() }
                    position <= 1 -> { val offset = position * -( pageMarginPx)
                        page.translationX = offset }
                    else -> { page.translationX = page.width.toFloat() } } }}
    }
}



//curl -location request POST 'https://reqres.in/api/login"
//
//
//
//
//-header "Content-Type: application/json'
//
//--data-raw
//eve.holt@reqres.ineve.holt@reqres.in
//{
//    "email": "eve.holt@reqres.in",
//    "password": "cityslicka"
//}
//
//
//
//curl -location request POST 'https://reqres.in/api/register'
//
//header "Content-Type: application/json"
//--data-raw
//
//"email": "eve.holt@rdsdseqres.in",
//"password": "pistol"