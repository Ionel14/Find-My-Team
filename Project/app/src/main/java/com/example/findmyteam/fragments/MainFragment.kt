package com.example.findmyteam.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyteam.R
import com.example.findmyteam.adapters.AnnouncementsAdapter
import com.example.findmyteam.data.AnnouncementsManagement
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.Announcement
import com.example.findmyteam.models.Cities.Companion.fromNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture

class MainFragment : Fragment(), OnItemClickListener {
    var announcements = ArrayList<Announcement>()
    private lateinit var adapter: AnnouncementsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val recycleView = view.findViewById<RecyclerView>(R.id.rv_announcements)

        runBlocking {
            launch(Dispatchers.IO) {

                val announcementsFuture: CompletableFuture<List<Announcement>> = AnnouncementsManagement.getAnnouncements(context);

                announcementsFuture.thenAccept { pAnnouncements ->
                    if (pAnnouncements != null) {
                        announcements = pAnnouncements as ArrayList<Announcement>
                        setAdapter()
                    } else {
                        showInvalidDialog( "No Announcements","", requireContext());
                    }
                }.exceptionally { ex ->
                    // Error handling logic
                    println("An error occurred: " + ex.message)
                    null
                }
            }
        }

    }

    private fun setAdapter(){
        val recycleView = view?.findViewById<RecyclerView>(R.id.rv_announcements)
        adapter = AnnouncementsAdapter(announcements)

        if (recycleView != null) {
            recycleView.adapter = adapter
        }

        adapter.setClickListener(this)
        val layoutManager = LinearLayoutManager(context)

        if (recycleView != null) {
            recycleView.layoutManager = layoutManager
        }
    }

    private fun addAnnouncementToSharedPreferences(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val sharedPreferences = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (!sharedPreferences.contains(id))
            {
                editor.putString(id, id)
            }
            else
            {
                editor.remove(id)
            }
            editor.apply()
        }

    }
    override fun onClick(view: View?, position: Int) {
        val announcement = announcements[position]
        when(view?.id){
            R.id.add_to_favorite -> {
                addAnnouncementToSharedPreferences(announcement.id)
            }
            R.id.announcement_details -> {
                val args = Bundle()

                args.putString("title", announcement.title)
                args.putString("description", announcement.description)
                args.putString("location", fromNumber(announcement.locationId.toInt()).toString())
                args.putString("ownerName", "Giany")


                findNavController().navigate(R.id.action_mainFragment_to_moreInfoFragment, args)
            }
        }
    }

}