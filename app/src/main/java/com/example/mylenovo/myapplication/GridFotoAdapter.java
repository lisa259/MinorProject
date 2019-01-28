package com.example.mylenovo.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;

public class GridFotoAdapter extends ResourceCursorAdapter {

    public GridFotoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.grid_foto, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView IVfoto = view.findViewById(R.id.IVfoto);
        String fotoString = cursor.getString(cursor.getColumnIndex("foto"));

        // String to bitmap
        byte[] b = Base64.decode(fotoString, Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        IVfoto.setImageBitmap(fotoBitmap);
    }
}

