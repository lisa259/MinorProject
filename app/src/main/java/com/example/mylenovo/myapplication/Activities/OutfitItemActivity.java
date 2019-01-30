package com.example.mylenovo.myapplication.Activities;

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

import com.example.mylenovo.myapplication.Adapters.GridFotoAdapter;
import com.example.mylenovo.myapplication.Databases.Database;
import com.example.mylenovo.myapplication.Helpers.LookbookHelper;
import com.example.mylenovo.myapplication.R;

import java.util.ArrayList;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class OutfitItemActivity extends AppCompatActivity {

    Spinner spItems;
    GridView gvItems;
    String gebruikersnaam;
    String selectCategorie;
    GridFotoAdapter adapter;
    LookbookHelper request;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfititem);
        spItems = (Spinner) findViewById(R.id.SPItems);
        spItems.setOnItemSelectedListener(new SpinnerClickListener());

        gvItems = (GridView) findViewById((R.id.GVItems));
        gvItems.setOnItemClickListener(new GvListener());

        request = new LookbookHelper(this);
    }

    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

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
        spItems.setAdapter(adapter);
    }

    private class SpinnerClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // vul gridview met alle items in selecteerde categorie, uit zowel garderobe als wishlist
            selectCategorie = spItems.getSelectedItem().toString();
            Cursor cursor = Database.selectAllItems(db, gebruikersnaam, selectCategorie);
            cursor.moveToFirst();
            adapter = new GridFotoAdapter(OutfitItemActivity.this, cursor);
            gvItems.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Doe niets
        }
    }

    private class GvListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Item toevoegen aan look, id van item
            Cursor item = (Cursor) adapterView.getItemAtPosition(i);
            int idItem = item.getInt(item.getColumnIndex("_id"));

            // bestaat de look al? of is dit het 1e item in de look
            Intent intentGet = getIntent();
            String optie = intentGet.getStringExtra("optie");

            if (optie.equals("nieuw")) {
                // post request voor nieuwe look
                request.postLookbook(gebruikersnaam, Integer.toString(idItem));

                // Als server niet leeg is, vind eerste ongebruike id
                Cursor cursor = Database.selectMaxIdLookbook(db);
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext()) {
                            id = cursor.getInt(0);
                        }
                    } finally {
                        cursor.close();
                    }
                } else {
                    // Als server leeg is, wordt het id 1, want 1e item
                    id = 1;
                }

                // toevoegen aan database
                db.insertLookbook(id, gebruikersnaam, Integer.toString(idItem));

            } else {
                // Item toevoegen aan bestaande look
                id = intentGet.getIntExtra("idLookbook", 0); // geeft 0??

                Cursor cursor = db.selectLookbookById(db, id);
                cursor.moveToFirst();

                String items = cursor.getString(cursor.getColumnIndex("items"));
                String itemsnieuw = items + "," + Integer.toString(idItem);

                // update server
                request.putLookbook(id, itemsnieuw);

                // update database
                db.updateLookbook(id, itemsnieuw);
            }

            Intent intent = new Intent(OutfitItemActivity.this, OutfitActivity.class);
            intent.putExtra("optie", "bestaat");
            intent.putExtra("idLookbook", id);
            startActivity(intent);
        }
    }
}
