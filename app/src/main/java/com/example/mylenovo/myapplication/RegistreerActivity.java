package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistreerActivity extends AppCompatActivity implements LoginHelper.Callback {

    String gebruikersnaam;
    String email;
    String wachtwoord1;
    String wachtwoord2;
    LoginHelper request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);

        request = new LoginHelper(this);
    }

    public void ClickAanmelden2(View v){
        EditText ETgebruikersnaam = findViewById(R.id.ETgebruikersnaam2);
        EditText ETemail = findViewById(R.id.ETemail2);
        EditText ETwachtwoord1 = findViewById(R.id.ETwachtwoord2);
        EditText ETwachtwoord2 = findViewById(R.id.ETwachtwoord3);

        gebruikersnaam = ETgebruikersnaam.getText().toString();
        email = ETemail.getText().toString();
        wachtwoord1 = ETwachtwoord1.getText().toString();
        wachtwoord2 = ETwachtwoord2.getText().toString();

        // checken of alles is ingevuld
        if (!gebruikersnaam.isEmpty() && !email.isEmpty() && !wachtwoord1.isEmpty() && !wachtwoord2.isEmpty()) {
            // checken of wachtwoorden gelijk zijn
            if (wachtwoord1.equals(wachtwoord2)) {
                // checken of gebruikersnaam en email nog niet gebruikt zijn
                request.getLogins(this);
            } else {
                Toast.makeText(this, "wachtwoorden komen niet overeen", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Voer eerst alle gegevens in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void gotLogins(JSONArray logins) {
        boolean bruikbaar = true;
        // checken of gebruikersnaam en email nog niet gebruikt zijn
        for (int i = 0; i < logins.length(); i++) {
            try {
                JSONObject item = logins.getJSONObject(i);
                if (gebruikersnaam.equals(item.getString("gebruikersnaam"))) {
                    Toast.makeText(this, "Gebruikersnaam al in gebruik", Toast.LENGTH_LONG).show();
                    bruikbaar = false;
                }
                if (email.equals(item.getString("email"))) {
                    Toast.makeText(this, "Email al in gebruik", Toast.LENGTH_LONG).show();
                    bruikbaar = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (bruikbaar) {
            // toevoegen aan server mbv post request
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("gebruikersnaam", gebruikersnaam);
            editor.commit();

            request.postLogins(gebruikersnaam, email, wachtwoord1);
            Intent intent = new Intent(this, GarderobeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void gotLoginsError(String message){
        Toast.makeText(this, "Kan geen verbinding maken met de server", Toast.LENGTH_LONG).show();
    }
}
