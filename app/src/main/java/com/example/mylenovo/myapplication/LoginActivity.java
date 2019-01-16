package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginHelper.Callback {

    LoginHelper request;
    String gebruikersnaam;
    String wachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        request = new LoginHelper(this);
    }

    @Override
    public void gotLogins(JSONArray logins) {
        // Check of ingevoerde gegevens overeenkomen met 1 van de logingegevens
        // ieder item in array afgaan
        for (int i = 0; i < logins.length(); i++){
            try {
                JSONObject item = logins.getJSONObject(i);
                if ((gebruikersnaam.equals(item.getString("gebruikersnaam")) || gebruikersnaam.equals(item.getString("email"))) && wachtwoord.equals(item.getString("wachtwoord"))) {

                    // OPSLAAN INGELOGDE GEBRUIKER!
                    Intent intent = new Intent(this, garderobeActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Inloggegevens kloppen niet", Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotLoginsError(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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


}
