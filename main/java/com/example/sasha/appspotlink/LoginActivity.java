package com.example.sasha.appspotlink;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {


    EditText editEmail;
    EditText editPsw;
    TextView registerLink;
    Button bLogin;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail=(EditText)findViewById(R.id.email);
        editPsw=(EditText)findViewById(R.id.psw);
        bLogin=(Button)findViewById(R.id.log);
        bLogin=(Button)findViewById(R.id.log);
        registerLink=(TextView)findViewById(R.id.tvRegisterHere);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());



        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editEmail.getText().toString();
                final String password = editPsw.getText().toString();

                connectionDetector conn = new connectionDetector(getApplicationContext());
                if (conn.isConnectingToInternet()) {

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String Email = jsonResponse.getString("email");
                                    String IdUser = jsonResponse.getString("idUser");
                                    String Nome = jsonResponse.getString("nome");
                                    String Cognome = jsonResponse.getString("cognome");
                                    String Indirizzo = jsonResponse.getString("indirizzo");
                                    String Citta = jsonResponse.getString("citta");
                                    String Cf = jsonResponse.getString("CF");
                                    String Cel = jsonResponse.getString("cellulare");
                                    String Tel = jsonResponse.getString("telefono");
                                    String DNascita = jsonResponse.getString("data_nascita");
                                    String Ditta = jsonResponse.getString("ditta");
                                    String Piva = jsonResponse.getString("pIva");
                                    String comune = jsonResponse.getString("comune");
                                    String provincia = jsonResponse.getString("provincia");
                                    String cap = jsonResponse.getString("CAP");

                                    //converto stringa data in data data
                                    Date date;
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        date = sdf.parse(DNascita);
                                        //converto da data a stringa
                                        String currentData = format.format(date);
                                        DNascita = currentData;
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }


                                    session.createUserLoginSession(Email, IdUser, Nome, Cognome, Indirizzo, Citta, Cf, Cel, Tel, DNascita, Ditta, Piva, comune, provincia, cap);
                                    Intent intent = new Intent(getApplicationContext(), userAreaActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //intent.putExtra("email", Email);
                                    LoginActivity.this.startActivity(intent);

                                    finish();
                                } else {
                                    // username / password doesn't match&
                                    Toast.makeText(getApplicationContext(),
                                            "email/password non sono corrette",
                                            Toast.LENGTH_LONG).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Connessione di rete assente")
                            .setNegativeButton("Riprova", null)
                            .create()
                            .show();
                }
            }
        });


        }
    }