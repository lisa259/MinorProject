package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class garderobeActivity extends AppCompatActivity implements GarderobeHelper.Callback  {

    GridView GVGarderobe;
    GarderobeHelper request;
    String gebruikersnaam;
    Spinner SPCategorie;
    String categorie;
    ArrayList<String> categorieen;
    Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garderobe);

        GVGarderobe = (GridView) findViewById(R.id.GVGarderobe);
        GVGarderobe.setOnItemClickListener(new EntryClickListener());

        SPCategorie = (Spinner) findViewById(R.id.SPCategorie);
        SPCategorie.setOnItemSelectedListener(new SpinnerClickListener());

        request = new GarderobeHelper(this);
        request.getItems(this);
    }

    @Override
    public void gotItems(JSONArray items) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

        if (first) {
            // Alle categorieen van gebruiker verzamelen
            categorieen = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = items.getJSONObject(i);
                    // geen dubbele categorieen
                    if (!categorieen.contains(item.getString("categorie")) && gebruikersnaam.equals(item.getString("gebruikersnaam"))) {
                        categorieen.add(item.getString("categorie"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // spinner vullen
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorieen);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SPCategorie.setAdapter(adapter);
        } else {
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = items.getJSONObject(i);
                    // alle items uit geselecteerde categorie
                    if (!categorieen.contains(item.getString("categorie")) && gebruikersnaam.equals(item.getString("gebruikersnaam"))) {
                        categorieen.add(item.getString("categorie"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void gotItemsError(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void ClickInstellingen(View v){
        Intent intent = new Intent(this, InstellingenActivity.class);
        startActivity(intent);
    }

    public void ClickNieuwItem(View v){
        Intent intent = new Intent(this, NieuwItemActivity.class);
        startActivity(intent);
    }

    public void ClickWishlist(View v){
        Intent intent = new Intent(this, WishlistActivity.class);
        startActivity(intent);
    }

    public void ClickVrienden(View v){
        Intent intent = new Intent(this, vriendenActivity.class);
        startActivity(intent);
    }

    public void ClickLookbook(View v){
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }

    private class SpinnerClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // vul gridview met alle items in selecteerde categorie
            Toast.makeText(garderobeActivity.this, "select", Toast.LENGTH_LONG).show();
            categorie = SPCategorie.getSelectedItem().toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // doe niets
            Toast.makeText(garderobeActivity.this, "non", Toast.LENGTH_LONG).show();
        }
    }

    private class EntryClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(garderobeActivity.this, ItemActivity.class);
            startActivity(intent);
        }
    }
}
