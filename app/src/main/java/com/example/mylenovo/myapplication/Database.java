package com.example.mylenovo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static Database instance;

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbCreateItems = "CREATE TABLE items (id INTEGER PRIMARY KEY, " +
                "gebruikersnaam text, categorie text, merk text, foto text, locatie text);";
        String dbCreateLookbook = "CREATE TABLE lookbook (id INTEGER PRIMARY KEY, " +
                "items text, gebruikersnaam text);";
        db.execSQL(dbCreateItems);
        db.execSQL(dbCreateLookbook);
    }

    public static Database getInstance(Context context) {
//        if (Database.instance != null) {
//            return Database.instance;
//        } else {
            Database.instance = new Database(context, "database", null, 1);
            return Database.instance;
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Verwijder de data in de tabellen
//        db.execSQL("DELETE FROM items;");
//        db.execSQL("DELETE FROM lookbook;");
    }

    public void insertItem(GridFotoItem item){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("gebruikersnaam", item.getGebruikersnaam());
        values.put("categorie", item.getCategorie());
        values.put("merk", item.getMerk());
        values.put("foto", item.getFoto());
        values.put("locatie", item.getLocatie());
        db.insert("items", null, values);
    }

    public void insertLookbook(int id, String gebruikersnaam, String items){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
//        String listString = "";
//        for (Integer i : list) {
//            if (listString.equals("")) {
//                listString += Integer.toString(i);
//            } else {
//                listString += ",";
//                listString += Integer.toString(i);
//            }
//        }
        values.put("id", id);
        values.put("gebruikersnaam", gebruikersnaam);
        values.put("items", items);
        db.insert("lookbook", null, values);
    }

    public static Cursor selectitems(Database instance, String gebruikersnaam, String locatie) {
        SQLiteDatabase database = instance.getWritableDatabase();
        String sqlString = "SELECT * FROM entries WHERE gebruikersnaam = " + gebruikersnaam +
                " AND locatie = " + locatie;
        return database.rawQuery(sqlString, null);
    }

    static void delete(String tabel, long id) {
        SQLiteDatabase db = instance.getWritableDatabase();
        db.delete(tabel, "id = " + id, null);
    }
}
