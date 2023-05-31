package com.example.findmyteam.fragments

import android.R.attr.password
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.findmyteam.R
import com.example.findmyteam.data.UsersManagement.findUser
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.User
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.CompletableFuture


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnItemClickListener {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private var currentUser: User? = null;

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onClick(view: View?, position: Int) {

        if (emailEditText.text.isNullOrEmpty())
        {
            showInvalidDialog( "Email Field is empty","Please enter the email", requireContext());
        }
        else if (!emailEditText.text!!.contains('@'))
        {
            showInvalidDialog( "This is not a valid email","Please enter a valid email", requireContext());
        }
        else if (passwordEditText.text.isNullOrEmpty())
        {
            showInvalidDialog( "Password Field is empty","Please enter the password", requireContext());
        }
        else if (currentUser != null && currentUser!!.email == emailEditText.toString())
        {
            if (currentUser!!.password == passwordEditText.text.toString())
            {
                //Go to next page
                showInvalidDialog( "OK","Logged in", requireContext());
            }
            else
            {
                showInvalidDialog( "Password is wrong","Please try again", requireContext());
            }
        }
        else{
            val userFuture: CompletableFuture<User> = findUser(context, emailEditText.text.toString())

            userFuture.thenAccept { user ->
                if (user != null) {
                    if (user.password == passwordEditText.text.toString())
                    {
                        //Go to next page
                        showInvalidDialog( "OK","Logged in", requireContext());
                    }
                    else
                    {
                        currentUser = user;
                        showInvalidDialog( "Password is wrong","Please try again", requireContext());
                    }
                } else {
                    // User not found logic
                    showInvalidDialog( "User not found","Please try again", requireContext());
                }
            }.exceptionally { ex ->
                // Error handling logic
                println("An error occurred: " + ex.message)
                null
            }

        }
    }
}