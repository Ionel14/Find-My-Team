package com.example.findmyteam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyteam.R
import com.example.findmyteam.adapters.MyAnnouncementsAdapter
import com.example.findmyteam.data.AnnouncementsManagement
import com.example.findmyteam.data.UsersManagement
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.Announcement
import com.example.findmyteam.models.Cities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyAnnouncementsFragment : Fragment(), OnItemClickListener {

    private lateinit var adapter: MyAnnouncementsAdapter
    private var announcements = ArrayList<Announcement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_announcements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        announcements = AnnouncementsManagement
            .allAnnouncements.filter { announcement -> announcement.ownerId == UsersManagement.currentUser.id } as ArrayList<Announcement>
        setAdapter()
    }

    private fun setAdapter(){
        val recycleView = view?.findViewById<RecyclerView>(R.id.rv_announcements)
        adapter = MyAnnouncementsAdapter(announcements)

        if (recycleView != null) {
            recycleView.adapter = adapter
        }

        adapter.setClickListener(this)
        val layoutManager = LinearLayoutManager(context)

        if (recycleView != null) {
            recycleView.layoutManager = layoutManager
        }
    }

    override fun onClick(view: View?, position: Int) {
        val announcement = announcements[position]
        when(view?.id){
            R.id.delete -> {
                announcements.remove(announcement)
                adapter.notifyItemRemoved(position)

                runBlocking {
                    launch(Dispatchers.IO) {
                        val delAn = AnnouncementsManagement.deleteAnnouncement(requireContext(),announcement)

                        delAn.thenAccept { deleted ->
                            if (!deleted) {
                                showInvalidDialog( "There was an error","The announcement wasn't deleted", requireContext())
                            }
                        }.exceptionally { ex ->
                            // Error handling logic
                            println("An error occurred: " + ex.message)
                            null
                        }
                    }
                }
            }

            R.id.announcement_details -> {

                val args = Bundle()
                args.putString("title", announcement.title)
                args.putString("description", announcement.description)
                args.putString("location", Cities.fromNumber(announcement.locationId.toInt()).toString())
                args.putString("ownerName", (UsersManagement.currentUser.firstname) + " " + (UsersManagement.currentUser.lastname))

                findNavController().navigate(R.id.action_myAnnouncementsFragment_to_moreInfoFragment, args)
            }
        }
    }
}