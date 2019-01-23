package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemActivity extends AppCompatActivity {

    int id;
    GridFotoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        item = (GridFotoItem) intent.getSerializableExtra("item");

        ImageView IVfoto = (ImageView) findViewById(R.id.IVitemfoto);
        TextView TVmerk = (TextView) findViewById(R.id.TVmerk);

        String fotoString = item.getFoto();

        // String to bitmap
        byte[] b = Base64.decode(fotoString, Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        IVfoto.setImageBitmap(fotoBitmap);
        TVmerk.setText(item.getMerk());

    }

    public void ClickAanpassen(View v){
        // open aanpasscherm, vul items in
        Intent intent = new Intent(this, AanpassenItemActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
