package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class NieuwItemActivity extends AppCompatActivity {

    ImageView IVItem;
    EditText ETMerk;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_item);

        IVItem = (ImageView) findViewById(R.id.IVitem);
        ETMerk = (EditText) findViewById(R.id.ETMerk);
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
        onBackPressed();
    }
}
