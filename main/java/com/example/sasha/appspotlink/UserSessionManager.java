package com.example.sasha.appspotlink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by sasha on 18/06/16.
 *
 * funzioni per la gestione della sessione del login\logout
 */
public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "SpotLink";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL ="email";
    public static final String KEY_ID_USER="id";
    public static final String KEY_NOME="nome";
    public static final String KEY_COGNOME="cognome";
    public static final String KEY_INDIRIZZO="indirizzo";
    public static final String KEY_CITTA="citta";
    public static final String KEY_CF="cf";
    public static final String KEY_CEL="cel";
    public static final String KEY_TEL="tel";
    public static final String KEY_COMUNE="com";
    public static final String KEY_PROVINCIA="prov";
    public static final String KEY_DATA_NASCITA="data";
    public static final String KEY_DITTA="ditta";
    public static final String KEY_PIVA="piva";
    public static final String KEY_CAP="cap";


    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    //Crea sessione di login
    public void createUserLoginSession(String email,String idUser,String nome,String cognome,String indirizzo,String citta,String cf,String cel,String tel,String dNascita,String ditta,String piva,String comune,String provincia,String cap){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_ID_USER,idUser);
        editor.putString(KEY_NOME,nome);
        editor.putString(KEY_COGNOME,cognome);
        editor.putString(KEY_INDIRIZZO,indirizzo);
        editor.putString(KEY_CITTA,citta);
        editor.putString(KEY_CF,cf);
        editor.putString(KEY_CEL,cel);
        editor.putString(KEY_TEL,tel);
        editor.putString(KEY_DATA_NASCITA,dNascita);
        editor.putString(KEY_DITTA,ditta);
        editor.putString(KEY_PIVA,piva);
        editor.putString(KEY_COMUNE,comune);
        editor.putString(KEY_PROVINCIA,provincia);
        editor.putString(KEY_CAP,cap);

        // commit changes
        editor.commit();

    }

    //verifica lo stato di login
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    //vengono memorizzati i dati di sessione
    public HashMap<String,String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String,String> user = new HashMap<String,String>();

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID_USER, pref.getString(KEY_ID_USER, null));
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));
        user.put(KEY_COGNOME, pref.getString(KEY_COGNOME, null));
        user.put(KEY_INDIRIZZO, pref.getString(KEY_INDIRIZZO, null));
        user.put(KEY_CITTA, pref.getString(KEY_CITTA, null));
        user.put(KEY_CF, pref.getString(KEY_CF, null));
        user.put(KEY_CEL, pref.getString(KEY_CEL, null));
        user.put(KEY_TEL, pref.getString(KEY_TEL, null));
        user.put(KEY_DATA_NASCITA, pref.getString(KEY_DATA_NASCITA, null));
        user.put(KEY_PIVA, pref.getString(KEY_PIVA, null));
        user.put(KEY_COMUNE, pref.getString(KEY_COMUNE, null));
        user.put(KEY_PROVINCIA, pref.getString(KEY_PROVINCIA, null));
        user.put(KEY_CAP, pref.getString(KEY_CAP, null));
        user.put(KEY_DITTA, pref.getString(KEY_DITTA, null));


        // return user
        return user;
    }

    //funzione di logout
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    //controllo per il login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
