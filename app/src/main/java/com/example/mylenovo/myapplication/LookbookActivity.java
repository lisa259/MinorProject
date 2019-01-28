package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class LookbookActivity extends AppCompatActivity {

    GridView GVLookbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookbook);
        GVLookbook = (GridView) findViewById(R.id.GVLookbook);
        GVLookbook.setOnItemClickListener(new LookClickListener());
    }

    public void onResume(){
        super.onResume();
        // Cursor select alle lookbooks uit database
        // cursor toepassen op gv
        // LookbookAdapter implementeren
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
            intent.putExtra("id", item.getInt(item.getColumnIndex("_id")));
            startActivity(intent);
        }
    }
}
