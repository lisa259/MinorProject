package com.example.mylenovo.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class GarderobeHelper implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private GarderobeHelper.Callback activity;
    RequestQueue queue;

    public interface Callback {
        void gotItems(JSONArray question);
        void gotItemsError(String message);
    }

    // Constructor
    public GarderobeHelper (Context inputContext) {
        this.context = inputContext;
    }

    // Get Items from url. Using interface Callback
    void getItems(GarderobeHelper.Callback inputActivity) {
        this.activity = inputActivity;
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/items";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONArray response) {
        activity.gotItems(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotItemsError(error.getMessage());
    }
}
