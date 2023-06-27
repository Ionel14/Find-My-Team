package com.example.findmyteam.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findmyteam.R
import com.example.findmyteam.activities.AppMain
import com.example.findmyteam.data.UsersManagement.createUser
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.existentUserCheck
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.helpers.signInChecks
import com.example.findmyteam.models.User
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.CompletableFuture


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnItemClickListener {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var firstnameEditText: TextInputEditText
    private lateinit var lastnameEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)
        firstnameEditText = view.findViewById(R.id.firstName)
        lastnameEditText = view.findViewById(R.id.lastName)
        val addButton = view.findViewById<Button>(R.id.create_button)
        addButton.setOnClickListener {
            onClick(it, 0)
        }
    }

    override fun onClick(view: View?, position: Int) {

        if (!signInChecks(requireContext(),
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                firstnameEditText.text.toString(),
                lastnameEditText.text.toString()))
        {
            return
        }
        else{
            existentUserCheck(requireContext(), emailEditText.text.toString()){found ->
                if (!found)
                {
                    val userFuture: CompletableFuture<User> = createUser(context, User(
                        id = "0",
                        email = emailEditText.text.toString(),
                        password =  passwordEditText.text.toString(),
                        firstname = firstnameEditText.text.toString(),
                        lastname = lastnameEditText.text.toString()
                    ))

                    userFuture.thenAccept {

                        openMainActivity()
                    }.exceptionally { ex ->
                        // Error handling logic
                        println("An error occurred: " + ex.message)
                        null
                    }
                }
            }
        }
    }

    private fun openMainActivity()
    {
        val intent = Intent(requireContext(), AppMain::class.java)
        startActivity(intent)
    }
}