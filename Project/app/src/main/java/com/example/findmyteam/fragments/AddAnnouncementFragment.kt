package com.example.findmyteam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findmyteam.R
import com.example.findmyteam.adapters.CategoriesAdapter
import com.example.findmyteam.adapters.LocationsAdapter
import com.example.findmyteam.data.AnnouncementsManagement
import com.example.findmyteam.data.UsersManagement
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.Announcement
import com.example.findmyteam.models.Categories
import com.example.findmyteam.models.Cities
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddAnnouncementFragment : Fragment() {

    lateinit var title: TextInputEditText
    private lateinit var description:TextInputEditText
    private lateinit var location:Spinner
    private lateinit var category:Spinner
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var locationsAdapter: LocationsAdapter


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

        title = view.findViewById(R.id.title_field)
        description = view.findViewById(R.id.description_field)
        location = view.findViewById(R.id.location)
        category = view.findViewById(R.id.category)

        val categoriesList: List<Categories> = enumValues<Categories>().toList()
        categoriesAdapter= CategoriesAdapter(requireContext(),categoriesList)
        category.adapter=categoriesAdapter

        val cities:List<Cities> = enumValues<Cities>().toList()
        locationsAdapter= LocationsAdapter(requireContext(),cities)
        location.adapter=locationsAdapter

        val defaultSelectionIndex=1
        location.setSelection(defaultSelectionIndex)
        category.setSelection(defaultSelectionIndex)
    }

    private fun fieldsChecks(): Boolean {

        if (title.text.isNullOrEmpty())
        {
            showInvalidDialog( "Title Field is empty","Please enter a title", requireContext())
            return false
        }

        if (description.text.isNullOrEmpty())
        {
            showInvalidDialog( "Description Field is empty","Please enter a description", requireContext())
            return false
        }

        if (location.selectedItem == null)
        {
            showInvalidDialog( "You didn't selected any location","Please choose a location", requireContext())
            return false
        }

        if (category.selectedItem == null)
        {
            showInvalidDialog( "You didn't selected any category","Please choose a category", requireContext())
            return false
        }

        return true
    }

    private fun onClick()
    {
        if (fieldsChecks())
        {
            runBlocking {
            launch(Dispatchers.IO) {

                 val addAn = AnnouncementsManagement.addAnnouncement(requireContext(),
                    Announcement("", title.text.toString(),
                        (category.selectedItem as Categories).ordinal.toString(),
                        (location.selectedItem as Cities).ordinal.toString(),
                        description.text.toString(),
                        UsersManagement.currentUser.id))


                addAn.thenAccept { added ->
                    if (!added) {
                        showInvalidDialog( "There was an error","The announcement wasn't added", requireContext())
                    }
                }.exceptionally { ex ->
                    // Error handling logic
                    println("An error occurred: " + ex.message)
                    null
                }
                }
            }

            findNavController().navigate(R.id.action_addAnnouncementFragment_to_mainFragment)
        }
    }
}