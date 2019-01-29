package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static com.example.mylenovo.myapplication.LoginActivity.db;


public class NieuwItemActivity extends AppCompatActivity {

    ImageView IVItem;
    EditText ETMerk;
    EditText ETCategorie;
    ItemHelper request;
    private static final int PICK_IMAGE = 100;
    Uri imageUri = null;
    String gebruikersnaam;
    String merk;
    String categorie;
    String fotoString;
    String locatie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_item);

        IVItem = (ImageView) findViewById(R.id.IVitem);
        ETMerk = (EditText) findViewById(R.id.ETMerk);
        ETCategorie = (EditText) findViewById(R.id.ETCategorie);

        request = new ItemHelper(this);
    }

    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        locatie = intent.getStringExtra("locatie");
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
        categorie = ETCategorie.getText().toString();
        merk = ETMerk.getText().toString();

        // is alles ingevuld?
        if (!categorie.equals("") && !merk.equals("") && imageUri != null) {
            // convert Uri to bitmap to string
            try {
                Bitmap fotoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                fotoString = Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
            } catch (Exception e) {
                Toast.makeText(this, "foto opslaan mislukt", Toast.LENGTH_LONG).show();
            }

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

            // Post item op
            request.postItems(gebruikersnaam, categorie, fotoString, merk, locatie);

            // Als server leeg is, wordt het id 1, want 1e item
            int id = 1;

            // Als server niet leeg is, vind eerste ongebruike id
            Cursor cursor = Database.selectMaxIdItems(db);
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(0);
                    }
                } finally {
                    cursor.close();
                }
            }
            // Toevoegen aan database
            db.insertItem(id, gebruikersnaam, categorie, merk, fotoString, locatie);

            onBackPressed();

        } else {
            Toast.makeText(this, "Vul alle velden in", Toast.LENGTH_LONG).show();
        }
    }
}
