package com.example.findmyteam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.findmyteam.R

class MoreInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_more_info, container, false)
        val titleTextView = rootView.findViewById<TextView>(R.id.title)
        val ownerNameTextView = rootView.findViewById<TextView>(R.id.owner_name)
        val locationTextView = rootView.findViewById<TextView>(R.id.city)
        val descriptionTextView = rootView.findViewById<TextView>(R.id.description)

        titleTextView.text = arguments?.getString("title")
        ownerNameTextView.text = arguments?.getString("ownerName")
        locationTextView.text = arguments?.getString("location")
        descriptionTextView.text = arguments?.getString("description")

        return rootView
    }


}