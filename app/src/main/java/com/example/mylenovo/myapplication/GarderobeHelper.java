package com.example.mylenovo.myapplication;

// HEADERS MET COMMENTS!

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

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

    public void postItems(final String gebruikersnaam, final String categorie, final String foto, final String merk, final String locatie) {
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/items";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
            }
        }
        ){  @Override
        protected Map<String, String> getParams() {
            Map<String, String> data = new HashMap<>();

            //data.put("itemID", itemID); KRIJGT VANZELF EEN ID TOEGEWEZEN
            data.put("gebruikersnaam", gebruikersnaam);
            data.put("categorie", categorie);
            data.put("foto", foto);
            data.put("merk", merk);
            data.put("locatie", locatie);

            return data;
        }
        };
        queue.add(postRequest);
    }
}
