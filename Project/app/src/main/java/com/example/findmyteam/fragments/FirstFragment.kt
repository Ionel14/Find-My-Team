package com.example.findmyteam.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findmyteam.R
import com.example.findmyteam.activities.AppMain
import com.example.findmyteam.data.UsersManagement.findUser
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.logInChecks
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnItemClickListener {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)
        val addButton = view.findViewById<Button>(R.id.login_button)
        addButton.setOnClickListener {
            onClick(it, 0)
        }
        val toRegister=view.findViewById<TextView>(R.id.to_register)
        toRegister.setOnClickListener {findNavController().navigate(R.id.SecondFragment)

        }

    }


    override fun onClick(view: View?, position: Int) {

        if (!logInChecks(requireContext(), emailEditText.text.toString(), passwordEditText.text.toString()))
        {}
        else if (currentUser != null && currentUser!!.email == emailEditText.toString())
        {
            if (currentUser!!.password == passwordEditText.text.toString())
            {
                //Go to next page
                showInvalidDialog( "OK","Logged in", requireContext())
            }
            else
            {
                showInvalidDialog( "Password is wrong","Please try again", requireContext())
            }
        }
        else{
            runBlocking {
                launch(Dispatchers.IO) {

                    val userFuture: CompletableFuture<User> =
                        findUser(context, emailEditText.text.toString())

            userFuture.thenAccept { user ->
                if (user != null) {
                    if (user.password == passwordEditText.text.toString())
                    {
                        //Go to next page
                        openMainActivity()
                    }
                    else
                    {
                        currentUser = user
                        showInvalidDialog( "Password is wrong","Please try again", requireContext())
                    }
                } else {
                    // User not found logic
                    showInvalidDialog( "User not found","Please try again", requireContext())
                }
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
        requireActivity().finish()
        val intent = Intent(requireContext(), AppMain::class.java)
        startActivity(intent)
    }
}