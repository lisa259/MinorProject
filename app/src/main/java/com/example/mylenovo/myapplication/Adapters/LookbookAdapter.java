/*
  Deze adapter set meerdere bitmaps op meerdere imageviews.
  @author      Lisa
 */

package com.example.mylenovo.myapplication.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;

import com.example.mylenovo.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mylenovo.myapplication.Activities.LoginActivity.db;

public class LookbookAdapter extends ResourceCursorAdapter {

    public LookbookAdapter(Context context, Cursor cursor) {
        super(context, R.layout.grid_lookbook, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView ivLookbook1 = view.findViewById(R.id.IVLookbook1);
        ImageView ivLookbook2 = view.findViewById(R.id.IVLookbook2);
        ImageView ivLookbook3 = view.findViewById(R.id.IVLookbook3);
        ImageView ivLookbook4 = view.findViewById(R.id.IVLookbook4);

        // Id's van de items van het lookbook ophalen
        String items = cursor.getString(cursor.getColumnIndex("items"));

        // Aantal items bepalen
        int aantalItems = items.split(",").length;
        List<String> list = new ArrayList<String>(Arrays.asList(items.split(",")));

        // Er is altijd minimaal 1 item, dus 1e item/foto altijd setten
        // Select item met 1e id in list
        Cursor cursor1 = db.selectItemById(db, Integer.valueOf(list.get(0)));
        cursor1.moveToFirst();
        String foto1 = cursor1.getString(cursor1.getColumnIndex("foto"));

        // String to bitmap
        byte[] b1 = Base64.decode(foto1, Base64.URL_SAFE);
        Bitmap fotoBitmap1 = BitmapFactory.decodeByteArray(b1, 0, b1.length);

        ivLookbook1.setImageBitmap(fotoBitmap1);

        if (aantalItems > 1) {
            // select item met 2e id in list
            Cursor cursor2 = db.selectItemById(db, Integer.valueOf(list.get(1)));
            cursor2.moveToFirst();
            String foto2 = cursor2.getString(cursor2.getColumnIndex("foto"));

            // String to bitmap
            byte[] b2 = Base64.decode(foto2, Base64.URL_SAFE);
            Bitmap fotoBitmap2 = BitmapFactory.decodeByteArray(b2, 0, b2.length);

            ivLookbook2.setImageBitmap(fotoBitmap2);
        }
        if (aantalItems > 2) {
            // select item met 3e id in list
            Cursor cursor3 = db.selectItemById(db, Integer.valueOf(list.get(2)));
            cursor3.moveToFirst();
            String foto3 = cursor3.getString(cursor3.getColumnIndex("foto"));

            // String to bitmap
            byte[] b3 = Base64.decode(foto3, Base64.URL_SAFE);
            Bitmap fotoBitmap3 = BitmapFactory.decodeByteArray(b3, 0, b3.length);

            ivLookbook3.setImageBitmap(fotoBitmap3);
        }
        if (aantalItems > 3) {
            // select item met 4e id in list
            Cursor cursor4 = db.selectItemById(db, Integer.valueOf(list.get(3)));
            cursor4.moveToFirst();
            String foto4 = cursor4.getString(cursor4.getColumnIndex("foto"));

            // String to bitmap
            byte[] b4 = Base64.decode(foto4, Base64.URL_SAFE);
            Bitmap fotoBitmap4 = BitmapFactory.decodeByteArray(b4, 0, b4.length);

            ivLookbook4.setImageBitmap(fotoBitmap4);
        }
    }
}
