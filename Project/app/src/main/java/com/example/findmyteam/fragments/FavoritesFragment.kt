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
import com.example.findmyteam.models.Announcement
import com.example.findmyteam.models.Cities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(), OnItemClickListener {

    private lateinit var adapter: AnnouncementsAdapter
    var announcements = ArrayList<Announcement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)

        announcements = AnnouncementsManagement.allAnnouncements.filter { announcement -> sharedPreferences.contains(announcement.id) } as ArrayList<Announcement>
        setAdapter()
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

    private fun deleteAnnouncementFromSharedPreferences(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val sharedPreferences = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove(id)
            editor.apply()
        }

    }
    override fun onClick(view: View?, position: Int) {
        val announcement = announcements[position]
        when(view?.id){
            R.id.add_to_favorite -> {
                deleteAnnouncementFromSharedPreferences(announcement.id)
                announcements.remove(announcement)
                adapter.notifyItemRemoved(position)
            }
            R.id.announcement_details -> {
                val args = Bundle()

                args.putString("title", announcement.title)
                args.putString("description", announcement.description)
                args.putString("location", Cities.fromNumber(announcement.locationId.toInt()).toString())
                args.putString("ownerName", "Giany")

                findNavController().navigate(R.id.action_favoritesFragment_to_moreInfoFragment, args)
            }
        }
    }
}