package com.example.findmyteam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findmyteam.R
import com.example.findmyteam.data.UsersManagement

class AddAnnouncementFragment : Fragment() {

    lateinit var title:TextView
    lateinit var description:TextView
    lateinit var location:Spinner
    lateinit var category:Spinner


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_announcement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addButton = view.findViewById<Button>(R.id.add_announcement_button)
        addButton.setOnClickListener {
            onClick()
        }

        title = view.findViewById<TextView>(R.id.title_field)
        description = view.findViewById<TextView>(R.id.description_field)
        location = view.findViewById<Spinner>(R.id.location)
        category = view.findViewById<Spinner>(R.id.category)

//        val adapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.locations,
//            android.R.layout.simple_spinner_item
//        )
//
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//// Set the adapter to the spinner
//        spinner.adapter = adapter


    }

    private fun onClick()
    {
        findNavController().navigate(R.id.action_addAnnouncementFragment_to_mainFragment)
    }
}