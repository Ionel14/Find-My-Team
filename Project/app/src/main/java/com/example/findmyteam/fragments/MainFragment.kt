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
import com.example.findmyteam.data.UsersManagement
import com.example.findmyteam.helpers.OnItemClickListener
import com.example.findmyteam.helpers.UsersListener
import com.example.findmyteam.helpers.showInvalidDialog
import com.example.findmyteam.models.Announcement
import com.example.findmyteam.models.Cities
import com.example.findmyteam.models.User
import com.example.findmyteam.user.UserDataBase
import com.example.findmyteam.user.UserDb
import kotlinx.coroutines.*
import java.util.concurrent.CompletableFuture

class MainFragment : Fragment(), OnItemClickListener {
    private var announcements = ArrayList<Announcement>()
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

                val announcementsFuture: CompletableFuture<List<Announcement>> = AnnouncementsManagement.getAnnouncements(context)

                announcementsFuture.thenAccept { pAnnouncements ->
                    if (pAnnouncements != null) {
                        announcements = pAnnouncements as ArrayList<Announcement>
                        setAdapter()
                    } else {
                        showInvalidDialog( "No Announcements","", requireContext())
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
                args.putString("location", Cities.fromNumber(announcement.locationId.toInt()).toString())

                var owner: UserDb?

                val getUsersListener = object : UsersListener {
                    override fun onUsersReceived(users: List<User>?) {
                        if (users != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                val userDao = UserDataBase.getInstance(context).userDao()
                                for (user in users) {
                                    withContext(Dispatchers.IO) {
                                        userDao.insertUser(UserDb(user.firstname, user.lastname, user.email, user.id))
                                    }

                                    if (announcement.ownerId == user.id) {
                                        owner = UserDb(user.firstname, user.lastname, user.email, user.id)
                                        args.putString(
                                            "ownerName",
                                            (owner?.firstName ?: "") + (owner?.lastName ?: "")
                                        )
                                    }
                                }

                                // Navigation code after the block is ready
                                withContext(Dispatchers.Main) {
                                    findNavController().navigate(R.id.action_favoritesFragment_to_moreInfoFragment, args)
                                }
                            }
                        } else {
                            // Handle case when users list is null
                        }
                    }

                    override fun onError(error: Throwable) {
                        // Error handling logic
                        println("An error occurred: ${error.message}")
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val userDao = UserDataBase.getInstance(context).userDao()
                    owner = userDao.getUser(announcement.ownerId)

                    if (owner == null) {
                        userDao.deleteAllUsers()
                        UsersManagement.getUsersWithListener(context, getUsersListener)
                    } else {
                        args.putString(
                            "ownerName",
                            (owner?.firstName ?: "") + " " + (owner?.lastName ?: "")
                        )
                        // Navigation code after the block is ready
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(R.id.action_mainFragment_to_moreInfoFragment, args)
                        }
                    }
                }
            }
        }
    }

}