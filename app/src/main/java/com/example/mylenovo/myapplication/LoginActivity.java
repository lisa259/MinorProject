package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginHelper.Callback, ItemHelper.Callback, LookbookHelper.Callback  {

    LoginHelper request;
    ItemHelper requestItem;
    LookbookHelper requestLookbook;
    String gebruikersnaam;
    String wachtwoord;
    boolean inloggen = false;
    static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        request = new LoginHelper(this);
        requestItem = new ItemHelper(this);
        requestLookbook = new LookbookHelper(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        db = Database.getInstance(getApplicationContext());
        requestItem.getItems(this);
        requestLookbook.getLookbook(this);
    }

    @Override
    public void gotLogins(JSONArray logins) {
        // Check of ingevoerde gegevens overeenkomen met 1 van de logingegevens
        // ieder item in array afgaan
        for (int i = 0; i < logins.length(); i++){
            try {
                JSONObject item = logins.getJSONObject(i);
                if ((gebruikersnaam.equals(item.getString("gebruikersnaam")) || gebruikersnaam.equals(item.getString("email"))) && wachtwoord.equals(item.getString("wachtwoord"))) {

                    // Opslaan ingelogde gebruiker
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("gebruikersnaam", item.getString("gebruikersnaam"));
                    editor.commit();

                    inloggen = true;

                    Intent intent = new Intent(this, GarderobeActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Inloggen is op het moment niet mogelijk", Toast.LENGTH_LONG).show();
            }
        }
        if (!inloggen) {
            Toast.makeText(this, "Inloggegevens kloppen niet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void gotLoginsError(String message){
        Toast.makeText(this, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
    }

    public void ClickLogin(View v){
        // Is er iets ingevuld?
        EditText ETgebruikersnaam = (EditText) findViewById(R.id.ETgebruikersnaam);
        EditText ETwachtwoord = (EditText) findViewById(R.id.ETwachtwoord);
        gebruikersnaam = ETgebruikersnaam.getText().toString();
        wachtwoord = ETwachtwoord.getText().toString();

        if (!gebruikersnaam.isEmpty() && !wachtwoord.isEmpty()){
            request.getLogins(this);
        } else {
            Toast.makeText(this, "Voer eerst alle gegevens in", Toast.LENGTH_LONG).show();
        }
    }

    public void ClickAanmelden1(View v){
        Intent intent = new Intent(this, RegistreerActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotItems(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                // items opslaan in database
                db.insertItem(item.getInt("id"), item.getString("gebruikersnaam"), item.getString("categorie"), item.getString("merk"), item.getString("foto"), item.getString("locatie"));
            } catch (JSONException e) {
                Toast.makeText(this, "Kan items niet ontvangen van server", Toast.LENGTH_LONG).show();
            }
        }
        Log.d("zoeken", "items klaar");
    }

    @Override
    public void gotItemsError(String message) {
        Toast.makeText(this, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotLookbook(JSONArray looks) {
        for (int i = 0; i < looks.length(); i++) {
            try {
                JSONObject look = looks.getJSONObject(i);
                // look opslaan in database
                db.insertLookbook(look.getInt("id"), look.getString("gebruikersnaam"), look.getString("items"));
            } catch (JSONException e) {
                Toast.makeText(this, "Kan lookbook niet ontvangen van server", Toast.LENGTH_LONG).show();
            }
        }
        Log.d("zoeken", "lookbook klaar");
    }

    @Override
    public void gotLookbookError(String message) {
        Toast.makeText(this, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
    }
}
