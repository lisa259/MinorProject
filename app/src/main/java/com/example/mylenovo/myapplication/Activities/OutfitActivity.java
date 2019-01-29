package com.example.mylenovo.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mylenovo.myapplication.Adapters.GridFotoAdapter;
import com.example.mylenovo.myapplication.R;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class OutfitActivity extends AppCompatActivity {

    GridView GVOutfit;
    int id;
    String optie;
    GridFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit);
        GVOutfit = (GridView) findViewById(R.id.GVOutfit);
        GVOutfit.setOnItemClickListener(new ItemClickListener());
    }

    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        optie = intent.getStringExtra("optie");

        if (optie.equals("bestaat")) {
            id = intent.getIntExtra("idLookbook", 0);

            // krijg alle items van de look
            Cursor cursor = db.selectLookbookById(db, id);
            cursor.moveToFirst();

            String items = cursor.getString(cursor.getColumnIndex("items"));

            // Aantal items bepalen
            int aantalItems = items.split(",").length;
            String[] itemIds = new String[aantalItems];

            // Van string naar String[]
            int index = 0;
            for (String field : items.split(",")) {
                itemIds[index] = String.valueOf(field);
                index++;
            }

            // select alle items waarvan het id in itemsIds zit
            Cursor cursor2 = db.selectMultipleItemsById(db, aantalItems, itemIds);
            cursor2.moveToFirst();

            // set cursor met adapter to grid
            adapter = new GridFotoAdapter(this, cursor2);
            GVOutfit.setAdapter(adapter);
        }
    }

    public void ClickPlus2(View v){
        Intent intent = new Intent(this, OutfitItemActivity.class);
        intent.putExtra("optie", optie);
        if (optie.equals("bestaat")) {
            intent.putExtra("idLookbook", id);
        }
        startActivity(intent);
    }

    public void ClickVerwijderen2(View v){
        // Of dit ook met longclick doen???
        // Verwijder van server
        // Verwijder uit database
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor item = (Cursor) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(OutfitActivity.this, ItemActivity.class);
            intent.putExtra("optie", optie);
            intent.putExtra("id", item.getInt(item.getColumnIndex("_id")));
            intent.putExtra("idLook", id);

            startActivity(intent);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }
}
