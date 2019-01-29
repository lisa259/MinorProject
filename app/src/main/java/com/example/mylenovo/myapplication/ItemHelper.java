package com.example.mylenovo.myapplication;

// HEADERS MET COMMENTS!

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ItemHelper implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private ItemHelper.Callback activity;
    RequestQueue queue;

    public interface Callback {
        void gotItems(JSONArray question);
        void gotItemsError(String message);
    }

    // Constructor
    public ItemHelper (Context inputContext) {
        this.context = inputContext;
    }

    // Get Items from url. Using interface Callback
    void getItems(ItemHelper.Callback inputActivity) {
        this.activity = inputActivity;
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/items";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, this, this);

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
            }
        }
        ){  @Override
        protected Map<String, String> getParams() {
            Map<String, String> data = new HashMap<>();
            data.put("gebruikersnaam", gebruikersnaam);
            data.put("categorie", categorie);
            data.put("foto", foto);
            data.put("merk", merk);
            data.put("locatie", locatie);
            return data;
        }
        };
        postRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(postRequest);
    }

    public void deleteItems(int id){
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/items/" + Integer.toString(id);
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
            }
        }
        ){  @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response){
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        deleteRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(deleteRequest);
    }

    public void putItems(final int id, final  String gebruikersnaam, final String categorie, final String merk, final String foto, final String locatie){
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/items/" + Integer.toString(id);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
            }
        }
        ){  @Override
            public Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("categorie", categorie);
                data.put("merk", merk);
                data.put("foto", foto);

                return data;
            }
        };
        putRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(putRequest);
    }
}