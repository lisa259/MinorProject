package com.example.mylenovo.myapplication.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static Database instance;

    public Database(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbCreateItems = "CREATE TABLE items (_id INTEGER PRIMARY KEY, " +
                "gebruikersnaam text, categorie text, merk text, foto text, locatie text);";
        String dbCreateLookbook = "CREATE TABLE lookbook (_id INTEGER PRIMARY KEY, " +
                "items text, gebruikersnaam text);";
        db.execSQL(dbCreateItems);
        db.execSQL(dbCreateLookbook);
    }

    public static Database getInstance(Context context) {
        if (Database.instance == null) {
            Database.instance = new Database(context, "database", null, 1);
        }
        SQLiteDatabase db = instance.getWritableDatabase();
        db.delete("items", null, null);
        db.delete("lookbook", null, null);
        return Database.instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertItem(int id, String gebruikersnaam, String categorie, String merk,
                           String foto, String locatie){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("gebruikersnaam", gebruikersnaam);
        values.put("categorie", categorie);
        values.put("merk", merk);
        values.put("foto", foto);
        values.put("locatie", locatie);
        db.insert("items", null, values);
    }

    public void insertLookbook(int id, String gebruikersnaam, String items){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("gebruikersnaam", gebruikersnaam);
        values.put("items", items);
        db.insert("lookbook", null, values);
    }

    public static Cursor selectItems(Database instance, String gebruikersnaam, String categorie,
                                     String locatie) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT * FROM items WHERE gebruikersnaam = ? AND categorie = ? " +
                "AND locatie = ?", new String[] {gebruikersnaam, categorie, locatie});
    }

    public static Cursor selectLookbook(Database instance, String gebruikersnaam) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT * FROM lookbook WHERE gebruikersnaam = ?",
                new String[]{gebruikersnaam});
    }

    public static Cursor selectAllItems(Database instance, String gebruikersnaam, String categorie) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT * FROM items WHERE gebruikersnaam = ? AND categorie = ?",
                new String[] {gebruikersnaam, categorie});
    }

    public static Cursor selectCategorieen(Database instance, String gebruikersnaam, String locatie) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT DISTINCT categorie FROM items WHERE gebruikersnaam = ? AND" +
                " locatie = ?", new String[] {gebruikersnaam, locatie});
    }

    public static Cursor selectAllCategorieen(Database instance, String gebruikersnaam) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT DISTINCT categorie FROM items WHERE gebruikersnaam = ?",
                new String[] {gebruikersnaam});
    }

    public static Cursor selectMaxIdItems(Database instance) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT MAX(_id) + 1 FROM items", null);
    }

    public static Cursor selectMaxIdLookbook(Database instance) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT MAX(_id) + 1 FROM lookbook", null);
    }

    public static Cursor selectItemById(Database instance, int id) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT * FROM items WHERE _id = ?", new String[] {String.valueOf(id)});
    }

    public static Cursor selectLookbookById(Database instance, int id) {
        SQLiteDatabase database = instance.getWritableDatabase();
        return database.rawQuery("SELECT * FROM lookbook WHERE _id = ?", new String[] {String.valueOf(id)});
    }

    public static Cursor selectMultipleItemsById(Database instance, int aantal, String[] list) {
        SQLiteDatabase database = instance.getWritableDatabase();
        String sql = "SELECT * FROM items WHERE _id IN (?";
        for (int i = 1; i < aantal; i++) {
            sql += ",?";
        }
        sql += ")";
        return database.rawQuery(sql, list);
    }

    public static void updateItems(int id, String categorie, String merk, String foto) {
        SQLiteDatabase database = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("categorie", categorie);
        cv.put("merk", merk);
        cv.put("foto", foto);
        database.update("items", cv, "_id = ?", new String[] {String.valueOf(id)});
    }

    public static void updateLookbook(int id, String items) {
        SQLiteDatabase database = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("items", items);
        database.update("lookbook", cv, "_id = ?", new String[] {String.valueOf(id)});
    }

    public static void delete(String tabel, long id) {
        SQLiteDatabase db = instance.getWritableDatabase();
        db.delete(tabel, "_id = " + id, null);
    }
}
