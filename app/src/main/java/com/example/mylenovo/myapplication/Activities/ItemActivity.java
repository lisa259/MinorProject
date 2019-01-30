package com.example.mylenovo.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylenovo.myapplication.Databases.Database;
import com.example.mylenovo.myapplication.R;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class ItemActivity extends AppCompatActivity {

    int id;
    Cursor cursor;
    ImageView ivFoto;
    TextView tvMerk;
    String optie;
    int idLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ivFoto = (ImageView) findViewById(R.id.IVitemfoto);
        tvMerk = (TextView) findViewById(R.id.TVmerk);
    }

    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        Button aanpassen = (Button) findViewById(R.id.BTNaanpassen);
        aanpassen.setVisibility(View.VISIBLE);
        if(intent.hasExtra("optie")) {
            optie = intent.getStringExtra("optie");
            idLook = intent.getIntExtra("idLook", 0);
            // Aanpasknop op invisible
            aanpassen.setVisibility(View.INVISIBLE);
        }

        cursor = Database.selectItemById(db, id);
        cursor.moveToFirst();

        String fotoString = cursor.getString(cursor.getColumnIndex("foto"));

        // String to bitmap
        byte[] b = Base64.decode(fotoString, Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        ivFoto.setImageBitmap(fotoBitmap);
        tvMerk.setText(cursor.getString(cursor.getColumnIndex("merk")));
    }

    public void clickAanpassen(View v){
        // open aanpasscherm, vul items in
        Intent intent;
        if (optie != null) {
            intent = new Intent(this, OutfitActivity.class);
            intent.putExtra("optie", optie);
            intent.putExtra("id", idLook);
        } else {
            intent = new Intent(this, AanpassenItemActivity.class);
            intent.putExtra("id", id);
        }
        startActivity(intent);
    }
}
