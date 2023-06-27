package com.example.findmyteam.data;

import static com.example.findmyteam.helpers.Constants.ANNOUNCEMENTS_ENDPOINT;
import static com.example.findmyteam.helpers.Constants.BASE_URL;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.findmyteam.helpers.VolleyConfigSingleton;
import com.example.findmyteam.models.Announcement;

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
                        List<Announcement> announcements = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject announcementObj = response.getJSONObject(i);

                                String id = announcementObj.getString("id");
                                String title = announcementObj.getString("title");
                                String categoryId = announcementObj.getString("categoryId");
                                String description = announcementObj.getString("description");
                                String locationId = announcementObj.getString("locationId");
                                String ownerId = announcementObj.getString("ownerId");
                                announcements.add(new Announcement(id, title, categoryId, locationId, description, ownerId));
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


    }

    public static CompletableFuture<Announcement> addAnnouncement(Context context, Announcement announcement) {
        CompletableFuture<Announcement> completableFuture = new CompletableFuture<>();

        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(context);
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = BASE_URL + ANNOUNCEMENTS_ENDPOINT;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", announcement.getId());
            jsonBody.put("title", announcement.getTitle());
            jsonBody.put("categoryId", announcement.getCategoryId());
            jsonBody.put("description", announcement.getDescription());
            jsonBody.put("locationId", announcement.getLocationId());
            jsonBody.put("ownerId", announcement.getOwnerId());
        } catch (JSONException e) {
            e.printStackTrace();
            completableFuture.completeExceptionally(e);
            return completableFuture;
        }

        JsonObjectRequest createAnnouncementRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    try {
                        announcement.setId(response.get("id").toString());
                        completableFuture.complete(announcement);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                completableFuture::completeExceptionally
        );

        queue.add(createAnnouncementRequest);

        return completableFuture;
    }
}
