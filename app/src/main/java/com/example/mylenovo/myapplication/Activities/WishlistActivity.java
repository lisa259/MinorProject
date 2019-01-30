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
import com.example.mylenovo.myapplication.Helpers.ItemHelper;
import com.example.mylenovo.myapplication.R;

import java.util.ArrayList;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class WishlistActivity extends AppCompatActivity {

    GridView gvWishlist;
    ItemHelper request;
    String gebruikersnaam;
    Spinner spCategorie;
    String selectCategorie;
    GridFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        gvWishlist = (GridView) findViewById(R.id.GVWishlist);
        gvWishlist.setOnItemClickListener(new ItemClickListener());
        gvWishlist.setOnItemLongClickListener(new ItemLongClickListener());

        spCategorie = (Spinner) findViewById(R.id.SPCategorie);
        spCategorie.setOnItemSelectedListener(new SpinnerClickListener());

        request = new ItemHelper(this);
    }

    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

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
        spCategorie.setAdapter(adapter);
    }

    public void clickNieuwItem(View v){
        Intent intent = new Intent(this, NieuwItemActivity.class);
        intent.putExtra("locatie", "wishlist");
        startActivity(intent);
    }

    public void clickGarderobe(View v){
        Intent intent = new Intent(this, GarderobeActivity.class);
        startActivity(intent);
    }

    public void clickLookbook(View v){
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }

    private class SpinnerClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
            // vul gridview met alle items in selecteerde categorie
            selectCategorie = spCategorie.getSelectedItem().toString();
            Cursor cursor = Database.selectItems(db, gebruikersnaam, selectCategorie, "wishlist");
            cursor.moveToFirst();

            adapter = new GridFotoAdapter(WishlistActivity.this, cursor);
            gvWishlist.setAdapter(adapter);
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

    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
