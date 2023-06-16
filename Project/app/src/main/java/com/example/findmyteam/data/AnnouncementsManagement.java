package com.example.findmyteam.data;

import static com.example.findmyteam.helpers.Constants.ANNOUNCEMENTS_ENDPOINT;
import static com.example.findmyteam.helpers.Constants.BASE_URL;
import static com.example.findmyteam.helpers.Constants.USERS_ENDPOINT;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.findmyteam.helpers.VolleyConfigSingleton;
import com.example.findmyteam.models.Announcement;
import com.example.findmyteam.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnnouncementsManagement {

    public static CompletableFuture<List<Announcement>> getAnnouncements(Context context) {
        CompletableFuture<List<Announcement>> completableFuture = new CompletableFuture<>();

        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(context);
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = BASE_URL + ANNOUNCEMENTS_ENDPOINT;

        JsonArrayRequest getAnnouncementsRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        List<Announcement> announcements = new ArrayList<Announcement>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject announcementObj = response.getJSONObject(i);

                                String id = announcementObj.getString("id");
                                String title = announcementObj.getString("title");
                                String categoryId = announcementObj.getString("categoryId");
                                String description = announcementObj.getString("description");
                                String location = announcementObj.getString("location");
                                announcements.add(new Announcement(id, title, categoryId, location, description));
                        }

                        completableFuture.complete(announcements);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle the error response
                        completableFuture.completeExceptionally(e);
                    }
                },
                error -> {
                    error.printStackTrace();
                    // Handle the error response
                    completableFuture.completeExceptionally(error);
                }
        );

        queue.add(getAnnouncementsRequest);

        return completableFuture;

//        val announcementsFuture: CompletableFuture<List<Announcement>> = getAnnouncements(context);
//
//        announcementsFuture.thenAccept { announcements ->
//            if (announcements != null) {
//                //some code
//            } else {
//                showInvalidDialog( "No Announcements","", requireContext());
//            }
//        }.exceptionally { ex ->
//                // Error handling logic
//                println("An error occurred: " + ex.message)
//            null
//        }
    }

}
