package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.mylenovo.myapplication.LoginActivity.db;

public class ItemActivity extends AppCompatActivity {

    int id;
    Cursor cursor;
    ImageView IVfoto;
    TextView TVmerk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        IVfoto = (ImageView) findViewById(R.id.IVitemfoto);
        TVmerk = (TextView) findViewById(R.id.TVmerk);
    }

    public void onResume(){
        super.onResume();
        cursor = Database.selectItemById(db, id);
        cursor.moveToFirst();

        String fotoString = cursor.getString(cursor.getColumnIndex("foto"));

        // String to bitmap
        byte[] b = Base64.decode(fotoString, Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        IVfoto.setImageBitmap(fotoBitmap);
        TVmerk.setText(cursor.getString(cursor.getColumnIndex("merk")));
    }

    public void ClickAanpassen(View v){
        // open aanpasscherm, vul items in
        Intent intent = new Intent(this, AanpassenItemActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
