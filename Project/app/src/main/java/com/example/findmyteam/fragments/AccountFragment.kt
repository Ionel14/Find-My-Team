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
import com.example.findmyteam.activities.MainActivity
import com.example.findmyteam.data.UsersManagement


class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toFavoritesButton = view.findViewById<Button>(R.id.go_to_favorites)
        toFavoritesButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_favoritesFragment)
        }

        val toMyAnnouncementsButton = view.findViewById<Button>(R.id.user_announcements)
        toMyAnnouncementsButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_myAnnouncementsFragment)
        }

        val logOutButton = view.findViewById<Button>(R.id.log_out_btn)
        logOutButton.setOnClickListener {
            requireActivity().finish()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        val userName = view.findViewById<TextView>(R.id.user_name)
        userName.text = UsersManagement.currentUser.firstname + UsersManagement.currentUser.lastname


    }
}