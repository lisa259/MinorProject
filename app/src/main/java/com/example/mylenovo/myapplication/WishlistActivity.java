package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import static com.example.mylenovo.myapplication.LoginActivity.db;

public class WishlistActivity extends AppCompatActivity {

    GridView GVWishlist;
    ItemHelper request;
    String gebruikersnaam;
    Spinner SPCategorie;
    String selectCategorie;
    GridFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        GVWishlist = (GridView) findViewById(R.id.GVWishlist);
        GVWishlist.setOnItemClickListener(new ItemClickListener());
        GVWishlist.setOnItemLongClickListener(new ItemLongClickListener());

        SPCategorie = (Spinner) findViewById(R.id.SPCategorie);
        SPCategorie.setOnItemSelectedListener(new SpinnerClickListener());

        request = new ItemHelper(this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");
    }

    public void onResume(){
        super.onResume();
        Cursor cursor = Database.selectCategorieen(db, gebruikersnaam, "wishlist");

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
        SPCategorie.setAdapter(adapter);
    }

    public void ClickNieuwItem(View v){
        Intent intent = new Intent(this, NieuwItemActivity.class);
        intent.putExtra("locatie", "wishlist");
        startActivity(intent);
    }

    public void ClickGarderobe(View v){
        Intent intent = new Intent(this, GarderobeActivity.class);
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
            selectCategorie = SPCategorie.getSelectedItem().toString();
            Cursor cursor = Database.selectItems(db, gebruikersnaam, selectCategorie, "wishlist");
            adapter = new GridFotoAdapter(WishlistActivity.this, cursor);
            GVWishlist.setAdapter(adapter);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // doe niets, methode moet wel bestaan, anders werkt onItemSelected niet..
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor item = (Cursor) adapterView.getItemAtPosition(i);
            int id = item.getInt(item.getColumnIndex("_id"));

            Intent intent = new Intent(WishlistActivity.this, ItemActivity.class);
            intent.putExtra("id", item.getInt(item.getColumnIndex("_id")));
            startActivity(intent);
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor item = (Cursor) adapterView.getItemAtPosition(i);
            int id = item.getInt(item.getColumnIndex("_id"));

            // verwijder geselecteerde item van server
            request.deleteItems(id);

            // verwijder selecteerde item uit database
            db.delete("items", id);

            // update gridview en spinner
            onResume();
            return true;
        }
    }
}
