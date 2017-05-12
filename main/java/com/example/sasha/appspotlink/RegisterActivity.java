package com.example.sasha.appspotlink;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


public class RegisterActivity extends AppCompatActivity {
    String data2;
    String item;
    String item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nome = (EditText) findViewById(R.id.rNome);
        final EditText cogn = (EditText) findViewById(R.id.rCognome);
        final EditText email = (EditText) findViewById(R.id.rEmail);
        final EditText psw = (EditText) findViewById(R.id.rPsw);
        final EditText psw2 = (EditText) findViewById(R.id.rPsw2);
        final EditText tel = (EditText) findViewById(R.id.rTel);
        final EditText cel = (EditText) findViewById(R.id.rCel);
        final EditText data = (EditText) findViewById(R.id.rData);
        final EditText indirizzo = (EditText) findViewById(R.id.rIndirizzo);
        final EditText piva = (EditText) findViewById(R.id.rPiva);
        final EditText cf = (EditText) findViewById(R.id.rCf);
        final EditText citta = (EditText) findViewById(R.id.rCitta);
        final Spinner comune = (Spinner) findViewById(R.id.rComune);
        final Spinner provincia = (Spinner) findViewById(R.id.rProvincia);
        final Button bRegistrati = (Button) findViewById(R.id.bReg);
        final EditText cap = (EditText) findViewById(R.id.rCap);


        connectionDetector conn = new connectionDetector(getApplicationContext());
        if (conn.isConnectingToInternet()) {

            //riempo le province
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonarray = new JSONArray(response);
                    String[] provi = new String[jsonarray.length()];
                    provi[0]="Seleziona provincia";
                    if (jsonarray.length() > 0) {

                        for (int i = 1; i < jsonarray.length(); i++) {

                            //String province = jsonobject.getString("nomeP");
                            String prov2 = jsonarray.getString(i);
                            provi[i] = prov2;

                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                (getApplicationContext(), android.R.layout.simple_spinner_item, provi);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        provincia.setAdapter(dataAdapter);

                        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                // TODO Auto-generated method stub
                                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                                ((TextView) arg0.getChildAt(0)).setTextSize(18);




                                item = provincia.getSelectedItem().toString();



                                //riempo i comuni

                                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONArray jsonarray = new JSONArray(response);
                                            String[] comun = new String[jsonarray.length()];


                                            if (jsonarray.length() > 0) {

                                                for (int i = 0; i < jsonarray.length(); i++) {

                                                    //String province = jsonobject.getString("nomeP");
                                                    String comu2 = jsonarray.getString(i);
                                                    comun[i] = comu2;

                                                }

                                                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>
                                                        (getApplicationContext(), android.R.layout.simple_spinner_item, comun);
                                                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                comune.setAdapter(dataAdapter2);
                                                //manca il click
                                                comune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                                        // TODO Auto-generated method stub

                                                        item2 = comune.getSelectedItem().toString();
                                                        ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                                                        ((TextView) arg0.getChildAt(0)).setTextSize(18);



                                                        //riempo i comuni

                                                    }

                                                    public void onNothingSelected(AdapterView<?> arg0) {
                                                        // TODO Auto-generated method stub



                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        "Non ci sono province nel db",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };


                                    ComuniRequest comuniRequest = new ComuniRequest(item, responseListener2);
                                    RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity.this);
                                    queue2.add(comuniRequest);

                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub


                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Non ci sono province nel db",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ProvinceRequest provinceRequest = new ProvinceRequest(responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity.this);
        queue2.add(provinceRequest);
    }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();
    }

        //click pulsante registrati
        bRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome2 = nome.getText().toString();
                final String cogn2 = cogn.getText().toString();
                final String email2 = email.getText().toString();
                final String password2 = psw.getText().toString();
                final String password3 = psw2.getText().toString();
                final String tel2 = tel.getText().toString();
                final String cel2 = cel.getText().toString();
                 data2 = data.getText().toString();
                final String indirizzo2 = indirizzo.getText().toString();
                final String piva2 = piva.getText().toString();
                final String cf2 = cf.getText().toString();
                final String citta2 =citta.getText().toString();
                final String cap2 =cap.getText().toString();
                 String comune2 =item2;
                 String provincia2 =item;
                if(item.equals("Seleziona provincia")){
                    comune2 ="";
                    provincia2 ="";

                }

                if(data2!="") {
                    //converto stringa data in data data
                    Date date;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = format.parse(data2);
                        //converto da data a stringa
                        String currentData = sdf.format(date);
                        data2 = currentData;
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


                if(nome2.isEmpty()==false && cogn2.isEmpty()==false && email2.isEmpty()==false && password2.isEmpty()==false && password3.isEmpty()==false) {
                    if(password2.length()>=5 && password2.length()<=10){


                        if (password2.equals(password3)) {


                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                        Toast.makeText(getApplicationContext(),
                                                "Registrazione effettuata con successo!",
                                                Toast.LENGTH_LONG).show();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage("Registrazione fallita")
                                                .setNegativeButton("Riprova", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        RegisterRequest registerRequest = new RegisterRequest(nome2, cogn2, email2, password2, tel2, cel2, data2, indirizzo2, piva2, cf2, citta2,comune2,provincia2,cap2, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                         } else {
                        Toast.makeText(getApplicationContext(),
                                "Le password inserite sono diverse",
                                Toast.LENGTH_LONG).show();
                        psw2.setText("");
                        psw.setText("");
                         }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "La password deve contenere tra 5-10 caratteri",
                                Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "I campi con l'asterisco sono obbligatori",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }



}

