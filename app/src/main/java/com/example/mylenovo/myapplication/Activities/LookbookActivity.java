package com.example.mylenovo.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mylenovo.myapplication.Adapters.LookbookAdapter;
import com.example.mylenovo.myapplication.Helpers.LookbookHelper;
import com.example.mylenovo.myapplication.R;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class LookbookActivity extends AppCompatActivity {

    GridView GVLookbook;
    String gebruikersnaam;
    LookbookAdapter adapter;
    LookbookHelper request;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookbook);
        GVLookbook = (GridView) findViewById(R.id.GVLookbook);
        GVLookbook.setOnItemClickListener(new LookClickListener());
        GVLookbook.setOnItemLongClickListener(new LookLongClickListener());

        request = new LookbookHelper(this);
    }

    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

        // Cursor select alle lookbooks uit database
        cursor = db.selectLookbook(db, gebruikersnaam);
        cursor.moveToFirst();

        // cursor toepassen op gv
        adapter = new LookbookAdapter(this, cursor);
        GVLookbook.setAdapter(adapter);

    }

    public void ClickPlus(View v){
        Intent intent = new Intent(this, OutfitActivity.class);
        intent.putExtra("optie", "nieuw");
        startActivity(intent);
    }

    public void ClickGarderobe(View v){
        Intent intent = new Intent(this, GarderobeActivity.class);
        startActivity(intent);
    }

    public void ClickWishlist(View v){
        Intent intent = new Intent(this, WishlistActivity.class);
        startActivity(intent);
    }

    private class LookClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor item = (Cursor) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(LookbookActivity.this, OutfitActivity.class);
            intent.putExtra("optie", "bestaat");
            intent.putExtra("idLookbook", item.getInt(item.getColumnIndex("_id")));
            startActivity(intent);
        }
    }

    private class LookLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Delete look
            Cursor look = (Cursor) adapterView.getItemAtPosition(i);
            int id = look.getInt(look.getColumnIndex("_id"));

            // verwijder geselecteerde item van server
            request.deleteLookbook(id);

            // verwijder selecteerde item uit database
            db.delete("lookbook", id);
            onResume();
            return true;
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
