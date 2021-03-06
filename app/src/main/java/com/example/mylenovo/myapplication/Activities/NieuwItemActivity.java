/*
  Deze activity geeft de optie een nieuw item toe te voegen.
  Deze wordt opgeslagen op de server en in de database.
  @author      Lisa
 */

package com.example.mylenovo.myapplication.Activities;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mylenovo.myapplication.Databases.Database;
import com.example.mylenovo.myapplication.Helpers.ItemHelper;
import com.example.mylenovo.myapplication.R;

import java.io.ByteArrayOutputStream;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class NieuwItemActivity extends AppCompatActivity {

    ImageView ivItem;
    EditText etMerk;
    EditText etCategorie;
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

        ivItem = (ImageView) findViewById(R.id.IVitem);
        etMerk = (EditText) findViewById(R.id.ETMerk);
        etCategorie = (EditText) findViewById(R.id.ETCategorie);

        // ItemHelper voor het posten van het nieuwe item
        request = new ItemHelper(this);
    }

    public void onResume(){
        super.onResume();
        // Ophalen locatie van nieuwe item (garderobe/wishlist)
        Intent intent = getIntent();
        locatie = intent.getStringExtra("locatie");
    }

    public void clickUpload2(View v){
        // Open gallerij
        openGallery();
    }

    private void openGallery(){
        // Openen van de gallerij
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            // Toekennen gekozen foto
            imageUri = data.getData();
            ivItem.setImageURI(imageUri);
        }
    }

    public void clickToevoegen2(View v){
        categorie = etCategorie.getText().toString();
        merk = etMerk.getText().toString();

        // is alles ingevuld?
        if (!categorie.equals("") && !merk.equals("") && imageUri != null) {
            // convert Uri naar bitmap naar string
            try {
                Bitmap fotoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                fotoString = Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
            } catch (Exception e) {
                Toast.makeText(this, "foto opslaan mislukt", Toast.LENGTH_LONG).show();
            }

            // Achterhaal gebruikersnaam ingelogde gebruiker
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            gebruikersnaam = sharedPref.getString("gebruikersnaam", "default");

            // Post item op server
            request.postItems(gebruikersnaam, categorie, fotoString, merk, locatie);

            // Als server leeg is, wordt het id 1, want het is het eerste item
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
