/*
  Deze activity geeft alle items uit een eerder geselecteerde lookbook weer.
  @author      Lisa
 */

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

    GridView gvOutfit;
    int id;
    String optie;
    GridFotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit);
        gvOutfit = (GridView) findViewById(R.id.GVOutfit);
        gvOutfit.setOnItemClickListener(new ItemClickListener());
    }

    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        // Optie geeft aan of een lookbook al bestaat, of het een nieuw lookbook wordt
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

            // Items van string naar String[], zodat het ingevuld kan worden in select in database
            int index = 0;
            for (String field : items.split(",")) {
                itemIds[index] = String.valueOf(field);
                index++;
            }

            // select alle items waarvan het id in itemsIds zit (dubbele items worden 1x opgehaald)
            Cursor cursor2 = db.selectMultipleItemsById(db, aantalItems, itemIds);
            cursor2.moveToFirst();

            // set cursor met adapter op grid
            adapter = new GridFotoAdapter(this, cursor2);
            gvOutfit.setAdapter(adapter);
        }
    }

    public void clickPlus2(View v){
        Intent intent = new Intent(this, OutfitItemActivity.class);
        intent.putExtra("optie", optie);
        if (optie.equals("bestaat")) {
            intent.putExtra("idLookbook", id);
        }
        startActivity(intent);
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Geselecteerde item achterhalen
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
