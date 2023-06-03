package com.example.findmyteam.data;

import static com.example.findmyteam.helpers.Constants.BASE_URL;
import static com.example.findmyteam.helpers.Constants.USERS_ENDPOINT;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.findmyteam.helpers.VolleyConfigSingleton;
import com.example.findmyteam.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class UsersManagement {

    public static User currentUser;
    public static CompletableFuture<User> findUser(Context context, String email) {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();

        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(context);
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = BASE_URL + USERS_ENDPOINT;

        JsonArrayRequest getUsersRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject userObj = response.getJSONObject(i);

                            String emailU = userObj.getString("email");
                            if (emailU.equals(email)) {
                                String id = userObj.getString("id");
                                String password = userObj.getString("password");
                                String firstname = userObj.getString("firstname");
                                String lastname = userObj.getString("lastname");
                                User user = new User(id, email, password, firstname, lastname);
                                currentUser = user;
                                completableFuture.complete(user);
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

    public static CompletableFuture<User> createUser(Context context, User user) {
        CompletableFuture<User> completableFuture = new CompletableFuture<>();

        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(context);
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = BASE_URL + USERS_ENDPOINT;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", user.getEmail());
            jsonBody.put("password", user.getPassword());
            jsonBody.put("firstname", user.getFirstname());
            jsonBody.put("lastname", user.getLastname());
        } catch (JSONException e) {
            e.printStackTrace();
            completableFuture.completeExceptionally(e);
            return completableFuture;
        }

        JsonObjectRequest createUserRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    try {
                        user.setId(response.get("id").toString());
                        currentUser = user;
                        completableFuture.complete(null);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    completableFuture.completeExceptionally(error);
                }
        );

        queue.add(createUserRequest);

        return completableFuture;
    }



}
