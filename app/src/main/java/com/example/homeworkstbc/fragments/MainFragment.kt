package com.example.homeworkstbc.fragments
import ItemAdapter
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.example.homeworkstbc.viewModels.MainUsersState
import com.example.homeworkstbc.viewModels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()

    private val itemAdapter by lazy { ItemAdapter() }


    override fun start() {


        mainViewModel.fetchUsers()
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainUsersState.collect { state ->
                    when (state) {
                        is MainUsersState.Idle -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.itemRecyclerView.visibility = View.GONE
                        }
                        is MainUsersState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.itemRecyclerView.visibility = View.GONE
                        }
                        is MainUsersState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.itemRecyclerView.visibility = View.VISIBLE
                            itemAdapter.submitList(state.users)
                            if (state.isOffline) {
                                Toast.makeText(requireContext(), R.string.offline, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(requireContext(), R.string.onlineUser, Toast.LENGTH_LONG).show()
                            }
                        }
                        is MainUsersState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.itemRecyclerView.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                            Log.d("MainFragment", state.message)
                        }
                    }
                }
            }
        }
    }
}





//    private fun saveUser ( ) {
//        binding.saveButton.setOnClickListener {
//            val firstName = binding.firstNameInput.text.toString()
//            val lastName = binding.lastNameInput.text.toString()
//            val email = binding.emailInput.text.toString()
//
////            mainViewModel.saveUser(firstName, lastName, email)
//            mainViewModel.addUser(Random.nextInt(100000), firstName, lastName)
//            Log.d("MainFragment", "User added: $firstName $lastName")
//        }
//
//    }

//    private fun readUser ( ) {
//
//
//        binding.readButton.setOnClickListener {
//            mainViewModel.observeUsers()
//            viewLifecycleOwner.lifecycleScope.launch {
////                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
////                    val prefs = mainViewModel.userPrefsFlow.first()
////                    binding.userDetailsText.text = "First Name: ${prefs.firstName}\nLast Name: ${prefs.lastName}\nEmail: ${prefs.email}"
////                }
//                val prefs = mainViewModel.users.first()
////                mainViewModel.users.collect { userList ->
////                    Log.d("MainFragment", "User List: $userList")
////                }
//                Log.d("MainFragment", "User List: $prefs")
//                }
//            }
//
//        }




//curl -location request POST 'https://reqres.in/api/login"
//
//
//
//
//-header "Content-Type: application/json'
//
//--data-raw
//
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