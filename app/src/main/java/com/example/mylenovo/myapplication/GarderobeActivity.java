package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class GarderobeActivity extends AppCompatActivity implements GarderobeHelper.Callback  {

    GridView GVGarderobe;
    GarderobeHelper request;
    String gebruikersnaam;
    Spinner SPCategorie;
    String selectCategorie;
    ArrayList<String> categorieen;
    Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garderobe);
        Log.d("zoeken", "start oncreate garderobe");

        GVGarderobe = (GridView) findViewById(R.id.GVGarderobe);
        GVGarderobe.setOnItemClickListener(new ItemClickListener());
        GVGarderobe.setOnItemLongClickListener(new ItemLongClickListener());

        SPCategorie = (Spinner) findViewById(R.id.SPCategorie);
        SPCategorie.setOnItemSelectedListener(new SpinnerClickListener());

        request = new GarderobeHelper(this);
        request.getItems(this);
    }

    @Override
    public void gotItems(JSONArray items) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

        // Alleen bij het opnieuw enteren van garderobe layout, spinner vullen
        if (first) {
            // Alle categorieen van gebruiker verzamelen
            categorieen = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = items.getJSONObject(i);
                    // geen dubbele categorieen
                    if (!categorieen.contains(item.getString("categorie")) && gebruikersnaam.equals(item.getString("gebruikersnaam")) && item.getString("locatie").equals("garderobe")) {
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
            first = false;

        // Wanneer er een categorie geselecteerd is in de spinner
        } else {
            Log.d("zoeken", "1: else");
            ArrayList<GridFotoItem> itemsList = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = items.getJSONObject(i);
                    // alle items uit geselecteerde categorie
                    if (selectCategorie.equals(item.getString("categorie")) && gebruikersnaam.equals(item.getString("gebruikersnaam")) && item.getString("locatie").equals("garderobe")) {
                        // object maken en toevoegen aan lijst
                        itemsList.add(new GridFotoItem(item.getInt("id"), item.getString("gebruikersnaam"), item.getString("categorie"), item.getString("foto"), item.getString("merk"), item.getString("locatie")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d("zoeken", "2: objects gemaakt");
            // Lijst toekennen aan adapter
            GridFotoAdapter adapter = new GridFotoAdapter(this, R.layout.grid_foto, itemsList);
            GVGarderobe.setAdapter(adapter);
            Log.d("zoeken", "3: adapter geset");
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
        intent.putExtra("locatie", "garderobe");
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
            Toast.makeText(GarderobeActivity.this, "select", Toast.LENGTH_LONG).show();
            selectCategorie = SPCategorie.getSelectedItem().toString();
            request.getItems(GarderobeActivity.this);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // doe niets
            Toast.makeText(GarderobeActivity.this, "non", Toast.LENGTH_LONG).show();
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            GridFotoItem item = (GridFotoItem) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(GarderobeActivity.this, ItemActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("zoeken", "4: long click");
            GridFotoItem item = (GridFotoItem) adapterView.getItemAtPosition(i);
            int id = item.getId();
            // verwijder geselecteerde item
            request.deleteItems(id);
            // update gridview en spinner?
            return true;
        }
    }
}
