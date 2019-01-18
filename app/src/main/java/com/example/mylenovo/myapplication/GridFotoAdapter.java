package com.example.mylenovo.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridFotoAdapter extends ArrayAdapter<Uri> {

    private ArrayList<Uri> fotos;

    public GridFotoAdapter(Context context, ArrayList<Uri> fotos) {
        super(context, 0, fotos);
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_foto, parent, false);
        }

        ImageView IVfoto = convertView.findViewById(R.id.IVfoto);
        IVfoto.setImageURI(fotos.get(position));

        return convertView;
    }
}

