package com.example.mylenovo.myapplication;

import java.io.Serializable;

public class GridFotoItem implements Serializable {
    private String gebruikersnaam;
    private String categorie;
    private String foto;
    private String merk;
    private String locatie;

    // Constructor
    public GridFotoItem(String gebruikersnaam, String categorie, String foto, String merk, String locatie) {
        this.gebruikersnaam = gebruikersnaam;
        this.categorie = categorie;
        this.foto = foto;
        this.merk = merk;
        this.locatie = locatie;
    }

    // Getters
    public String getGebruikersnaam() { return gebruikersnaam; }

    public String getCategorie() {
        return categorie;
    }

    public String getFoto() {
        return foto;
    }

    public String getMerk() {
        return merk;
    }

    public String getLocatie() {
        return locatie;
    }
}