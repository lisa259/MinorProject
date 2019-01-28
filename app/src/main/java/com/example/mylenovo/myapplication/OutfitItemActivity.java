package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

import static com.example.mylenovo.myapplication.LoginActivity.db;

public class OutfitItemActivity extends AppCompatActivity {

    Spinner SPItems;
    GridView GVItems;
    String gebruikersnaam;
    String selectCategorie;
    GridFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfititem);
        SPItems = (Spinner) findViewById(R.id.SPItems);
        SPItems.setOnItemSelectedListener(new SpinnerClickListener());
        GVItems = (GridView) findViewById((R.id.GVItems));
        GVItems.setOnItemClickListener(new GVListener());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");
    }

    public void onResume(){
        super.onResume();
        // spinner vullen met categorieen van beide locaties
        Cursor cursor = Database.selectAllCategorieen(db, gebruikersnaam);
        ArrayList<String> categorieen = new ArrayList<String>();
        try {
            while (cursor.moveToNext()) {
                categorieen.add(cursor.getString(cursor.getColumnIndex("categorie")));
            }
        } finally {
            cursor.close();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorieen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPItems.setAdapter(adapter);
    }

    private class SpinnerClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // vul gridview met alle items in selecteerde categorie, uit zowel garderobe als wishlist
            selectCategorie = SPItems.getSelectedItem().toString();
            Cursor cursor = Database.selectAllItems(db, gebruikersnaam, selectCategorie);
            adapter = new GridFotoAdapter(OutfitItemActivity.this, cursor);
            GVItems.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Doe niets
        }
    }

    private class GVListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Item toevoegen aan look
            // bestaat de look al? of is dit het 1e item in de look
            Intent intentGet = getIntent();
            String optie = intentGet.getStringExtra("optie");

            if (optie.equals("nieuw")) {
                // post request voor nieuwe look
                // toevoegen aan database
            } else {
                int id = intentGet.getIntExtra("id", 0);
                // update server
                // update database
            }

            Intent intent = new Intent(OutfitItemActivity.this, OutfitActivity.class);
            intent.putExtra("optie", optie);
            //intent.putExtra("id", id);
            startActivity(intent);
        }
    }
}
