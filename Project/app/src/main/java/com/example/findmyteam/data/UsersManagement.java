package com.example.findmyteam.data;

import static com.example.findmyteam.helpers.Constants.BASE_URL;
import static com.example.findmyteam.helpers.Constants.USERS_ENDPOINT;
import static java.security.AccessController.getContext;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.findmyteam.helpers.VolleyConfigSingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class UsersManagement {
//    private void getUsers() {
//        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(getContext());
//        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
//        String url = BASE_URL + USERS_ENDPOINT;
//
//        // Create a JSONObject containing the email and password
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("email", "ew");
//            jsonBody.put("password", "d");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest getUsersRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonBody,
//                response -> {
//                    // Handle the response from the server
//                    try {
//                        // Extract user data from the response JSON
//                        String username = response.getString("username");
//                    } catch (JSONException e) {
//                        Toast.makeText(
//                                getContext(),
//                                "ERROR: Failed to parse response",
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                    hideLoading();
//                },
//                error -> {
//                    // Handle the error response
//                    Toast.makeText(
//                            getContext(),
//                            "ERROR: " + error.getMessage(),
//                            Toast.LENGTH_SHORT
//                    ).show();
//                    hideLoading();
//                }
//        );
//        queue.add(getUsersRequest);
//    }
}
