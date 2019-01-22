package com.example.mylenovo.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class GridFotoAdapter extends ArrayAdapter<GridFotoItem> {

    private ArrayList<GridFotoItem> itemsList;

    public GridFotoAdapter(Context context, int resource, @NonNull ArrayList<GridFotoItem> itemsList) {
        super(context, 0, itemsList);
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_foto, parent, false);
        }

        ImageView IVfoto = convertView.findViewById(R.id.IVfoto);
        String fotoString = itemsList.get(position).getFoto();

        // String to bitmap
        byte[] b = Base64.decode(fotoString, Base64.URL_SAFE);
        Bitmap fotoBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

        IVfoto.setImageBitmap(fotoBitmap);

        return convertView;
    }
}

