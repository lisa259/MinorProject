package com.example.mylenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity implements LoginHelper.Callback {

    LoginHelper request;

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
        // match: gebruikersnaam opslaan, intent
        // geen match:

        Intent intent = new Intent(this, garderobeActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotLoginsError(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    public void ClickLogin(View v){
        // Is er iets ingevuld?
        // ja:
        request.getLogins(this);
        // nee: toast

    }

    public void ClickAanmelden1(View v){
        Intent intent = new Intent(this, RegistreerActivity.class);
        startActivity(intent);
    }


}
