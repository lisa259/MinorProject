package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AanpassenItemActivity extends AppCompatActivity {

    GridFotoItem item;
    ImageView IVItem;
    EditText ETMerk;
    EditText ETCategorie;
    GarderobeHelper request;
    private static final int PICK_IMAGE = 100;
    Uri imageUri = null;
    String gebruikersnaam;
    String merk;
    String categorie;
    String fotoString;
    String locatie;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aanpassen_item);

        Intent intent = getIntent();
        item = (GridFotoItem) intent.getSerializableExtra("item");

        IVItem = (ImageView) findViewById(R.id.IVitem2);
        ETMerk = (EditText) findViewById(R.id.ETMerk2);
        ETCategorie = (EditText) findViewById(R.id.ETCategorie2);

        id = item.getId();
        gebruikersnaam = item.getGebruikersnaam();
        locatie = item.getLocatie();

        // Foto van string naar bitmap
        byte[] b = Base64.decode(item.getFoto(), Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        Toast.makeText(this, "2", Toast.LENGTH_LONG).show();

        IVItem.setImageBitmap(fotoBitmap);
        ETMerk.setText(item.getMerk());
        ETCategorie.setText(item.getCategorie());

        request = new GarderobeHelper(this);
    }

    public void ClickUpload3(View v){
        // Open gallerij
        openGallery();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            IVItem.setImageURI(imageUri);
        }
    }

    public void ClickAanpassenItem(View v){
        categorie = ETCategorie.getText().toString();
        merk = ETMerk.getText().toString();

        // Checken of alles is ingevuld, foto niet checken, want die kan niet verwijderd worden.
        if (!categorie.equals("") && !merk.equals("")) {
            // convert Uri to bitmap to string
            try {
                Bitmap fotoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                fotoString = Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // server updaten, put request
            request.putItems(id, gebruikersnaam, categorie, merk, fotoString, locatie);
        } else {
            Toast.makeText(this, "Vul alle velden in", Toast.LENGTH_LONG).show();
        }
    }
}
