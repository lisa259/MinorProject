package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class OutfitActivity extends AppCompatActivity {

    GridView GVOutfit;
    int id;
    String optie;

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
            id = intent.getIntExtra("id", 0);

            // krijg alle items. cursor select items where id = id
            // string met id's van deze items afgaan
            // iedere item select en toevoegen aan cursor? kan dit?
            // set cursor met adapter to grid
        }
    }

    public void ClickPlus2(View v){
        Intent intent = new Intent(this, OutfitItemActivity.class);
        intent.putExtra("optie", optie);
        if (optie.equals("bestaat")) {
            intent.putExtra("id", id);
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
            Intent intent = new Intent(OutfitActivity.this, ItemActivity.class);
            intent.putExtra("optie", optie);
            startActivity(intent);
        }
    }

    //Longclick om 1 item uit look te verwijderen ?

    public void onBackPressed() {
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }
}
