package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class NieuwItemActivity extends AppCompatActivity {

    ImageView IVItem;
    EditText ETMerk;
    EditText ETCategorie;
    GarderobeHelper request;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String gebruikersnaam;
    String merk;
    String categorie;
    String fotoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_item);

        IVItem = (ImageView) findViewById(R.id.IVitem);
        ETMerk = (EditText) findViewById(R.id.ETMerk);
        ETCategorie = (EditText) findViewById(R.id.ETCategorie);

        request = new GarderobeHelper(this);
    }

    public void ClickUpload2(View v){
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

    public void ClickToevoegen2(View v){
        // Opslaan in database
        // checken of alles is ingevuld
        categorie = ETCategorie.getText().toString();
        merk = ETMerk.getText().toString();
        if (categorie.equals("")) {
            Toast.makeText(this, "Voer categorie in", Toast.LENGTH_LONG).show();
        }
        if (merk.equals("")) {
            Toast.makeText(this, "Voer merk in", Toast.LENGTH_LONG).show();
        }

        // NOG CHECKEN OF URI NIET LEEG IS  >?  if (!Uri.EMPTY.equals(followUri))

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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

        // ALLEEN POSTEN ALS NIKS LEEG IS

        // LOCATIE NU HARDCODED, MOET NOG VARIABEL WORDEN MET INTENT
        request.postItems(gebruikersnaam, categorie, fotoString, merk, "garderobe");
        onBackPressed();
    }
}
