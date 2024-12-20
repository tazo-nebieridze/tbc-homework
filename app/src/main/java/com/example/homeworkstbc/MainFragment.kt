package com.example.homeworkstbc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainFragment : Fragment() {

    private var binding : FragmentMainFragmentBinding? = null


    private val users = mutableListOf(
        User(
            id = 1,
            firstName = "გრიშა",
            lastName = "ონიანი",
            birthday = "1724647601641",
            address = "სტალინის სახლმუზეუმი",
            email = "grisha@mail.ru"
        ),
        User(
            id = 2,
            firstName = "Jemal",
            lastName = "Kakauridze",
            birthday = "1714647601641",
            address = "თბილისი, ლილოს მიტოვებული ქარხანა",
            email = "jemal@gmail.com"
        ),
        User(
            id = 2,
            firstName = "Omger",
            lastName = "Kakauridze",
            birthday = "1724647701641",
            address = "თბილისი, ასათიანი 18",
            email = "omger@gmail.com"
        ),
        User(
            id =32,
            firstName = "ბორის",
            lastName = "გარუჩავა",
            birthday = "1714947701641",
            address = "თბილისი, იაშვილი 14",
            email = ""
        ),
        User(
            id =34,
            firstName = "აბთო",
            lastName = "სიხარულიძე",
            birthday = "1711947701641",
            address = "ფოთი",
            email = "tebzi@gmail.com",
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainFragmentBinding.inflate(inflater,container,false)


        openInputs()
        saveUsers()


        binding?.searchInput?.doOnTextChanged { text, start, before, count ->
            val userInput = text?.toString() ?: ""

            if (userInput.isNotEmpty()) {
                val filteredUsers = filterUsers(userInput)

                if (filteredUsers.isNotEmpty()) {
                    val foundUser = filteredUsers.first()
                    displayUserInfo(foundUser)
                } else {
                    binding?.answerConteiner?.visibility = View.GONE
                    binding?.errorConteiner?.visibility = View.VISIBLE
                    binding?.errorText?.text = "User Not Found"
                }
            } else {
                binding?.answerConteiner?.visibility = View.GONE

            }
        }


        return binding?.root
    }
    private fun displayUserInfo(user: User) {
        binding?.answerConteiner?.visibility = View.VISIBLE
        binding?.errorConteiner?.visibility = View.GONE

        binding?.emailText?.text = user.email
        binding?.firstNameText?.text = user.firstName
        binding?.lastNameText?.text = user.lastName
        binding?.birthDateText?.text =  formatBirthday( user.birthday)
        binding?.addressText?.text = user.address
        binding?.descriptionText?.text = user.desc

    }

    private fun filterUsers(userInput: String): List<User> {
        val userInputTrim = userInput.trim().lowercase()

        return users.filter { user ->
            user.firstName.lowercase().contains(userInputTrim) ||
                    user.lastName.lowercase().contains(userInputTrim) ||
                    user.email.lowercase().contains(userInputTrim) ||
                    user.address.lowercase().contains(userInputTrim) ||
                    user.birthday.lowercase().contains(userInputTrim) ||
                    user.desc?.lowercase()?.contains(userInputTrim) == true
        }
    }


    private fun openInputs() {
        binding?.addNewUser?.setOnClickListener {
            val currentSearchInput = binding?.searchInput?.text.toString().trim()

            binding?.searchContainer?.visibility = View.GONE
            binding?.newUserContainer?.visibility = View.VISIBLE

            binding?.searchInput?.setText(currentSearchInput)
        }
    }

    private fun clearInputFields() {
        binding?.firstNameInput?.text?.clear()
        binding?.lastNameInput?.text?.clear()
        binding?.birthDateInput?.text?.clear()
        binding?.addressInput?.text?.clear()
        binding?.emailInput?.text?.clear()
    }


    private fun saveUsers () {
        binding?.saveNewUser?.setOnClickListener {
            if(binding?.firstNameInput?.text?.length == 0 ||
                binding?.lastNameInput?.text?.length == 0 ||
                binding?.birthDateInput?.text?.toString()?.length == 0 ||
                binding?.addressInput?.text?.length == 0 ||
                binding?.emailInput?.text?.length == 0 ) {

                binding?.inputErrorText?.text = "enter all text"

            } else {
//                იმეილის ვალიდაციას ვერ ვასწრებ

                binding?.inputErrorText?.visibility = View.GONE

                val newId = users.maxOfOrNull { it.id }?.plus(1) ?: 1

                val searchInput = binding?.searchInput?.text.toString().trim()

                val newUser = User(
                    id = newId,
                    firstName = binding?.firstNameInput?.text.toString(),
                    lastName = binding?.lastNameInput?.text.toString(),
                    birthday = binding?.birthDateInput?.text.toString(),
                    address = binding?.addressInput?.text.toString(),
                    email = binding?.emailInput?.text.toString(),
                    desc = if (searchInput.isNotEmpty())
                        searchInput
                    else  null
                )

                users.add(newUser)
                clearInputFields()
                binding?.searchContainer?.visibility = View.VISIBLE
                binding?.newUserContainer?.visibility = View.GONE

                displayUserInfo(newUser)
            }
        }
    }

    private fun formatBirthday(birthday: String): String {
        val date = Date(birthday.toLong())
        val formatter = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        return formatter.format(date)
    }



    data class User (
        val id: Int,
        val firstName : String,
        val lastName : String,
        val birthday : String,
        val address : String,
        val email : String,
        val desc: String? = null
    )







    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}