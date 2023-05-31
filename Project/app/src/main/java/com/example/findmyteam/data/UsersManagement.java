package com.example.findmyteam.data;

import static com.example.findmyteam.helpers.Constants.BASE_URL;
import static com.example.findmyteam.helpers.Constants.USERS_ENDPOINT;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.findmyteam.helpers.VolleyConfigSingleton;
import com.example.findmyteam.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class UsersManagement {
    private CompletableFuture<User> findUser(Context context, String email) {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();

        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(context);
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = BASE_URL + USERS_ENDPOINT;

        JsonObjectRequest getUsersRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray usersArray = response.getJSONArray("users");

                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject userObj = usersArray.getJSONObject(i);

                            String emailU = userObj.getString("email");
                            if (emailU.equals(email)) {
                                String password = userObj.getString("password");
                                User currentUser = new User(email, password);
                                completableFuture.complete(currentUser);
                                return; // Exit the loop and function since user is found
                            }
                        }

                        // User not found
                        completableFuture.complete(null);
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

        queue.add(getUsersRequest);

        return completableFuture;
    }


}
