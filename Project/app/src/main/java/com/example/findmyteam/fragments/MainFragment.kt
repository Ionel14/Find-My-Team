package com.example.findmyteam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyteam.R
import com.example.findmyteam.adapters.AnnouncementsAdapter
import com.example.findmyteam.data.AnnouncementsManagement
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.Announcement
import kotlinx.coroutines.Dispatchers
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

    override fun onClick(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

}