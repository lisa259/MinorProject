/*
  Deze helper maakt contact met de lookbook pagina van de server.
  Het getten, posten, putten en deleten van lookbooks is allemaal mogelijk.
  @author      Lisa
 */

package com.example.mylenovo.myapplication.Helpers;

import android.content.Context;
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

public class LookbookHelper implements Response.Listener<JSONArray>, Response.ErrorListener{
    private Context context;
    private LookbookHelper.Callback activity;
    RequestQueue queue;

    public interface Callback {
        void gotLookbook(JSONArray question);
        void gotLookbookError(String message);
    }

    // Constructor
    public LookbookHelper (Context inputContext) {
        this.context = inputContext;
    }

    // Get lookbooks van server/url. Maak gebruik van callback
    public void getLookbook(LookbookHelper.Callback inputActivity) {
        this.activity = inputActivity;
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/lookbook";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, this, this);

        // Vergroot de responsetijd, om runtime errors zoveel mogelijk te voorkomen
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
        activity.gotLookbook(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotLookbookError(error.getMessage());
    }

    // Post lookbook op server/url
    public void postLookbook(final String gebruikersnaam, final String items) {
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/lookbook";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server",
                        Toast.LENGTH_LONG).show();
            }
        }
        ){  @Override
        protected Map<String, String> getParams() {
            Map<String, String> data = new HashMap<>();
            data.put("gebruikersnaam", gebruikersnaam);
            data.put("items", items);
            return data;
        }
        };
        // Vergroot de responsetijd, om runtime errors zoveel mogelijk te voorkomen
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

    // Delete lookbook van server/url
    public void deleteLookbook(int id){
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/lookbook/" + Integer.toString(id);
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server",
                        Toast.LENGTH_LONG).show();
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
        // Vergroot de responsetijd, om runtime errors zoveel mogelijk te voorkomen
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

    // Put lookbook op server/url
    public void putLookbook(final int id, final String items){
        queue = Volley.newRequestQueue(context);
        String url = "https://ide50-lisabeek.legacy.cs50.io:8080/lookbook/" + Integer.toString(id);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Kan geen verbinding maken met de server",
                        Toast.LENGTH_LONG).show();
            }
        }
        ){  @Override
        public Map<String, String> getParams() {
            Map<String, String> data = new HashMap<String, String>();
            data.put("items", items);

            return data;
        }
        };
        // Vergroot de responsetijd, om runtime errors zoveel mogelijk te voorkomen
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
