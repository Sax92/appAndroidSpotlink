package com.example.sasha.appspotlink;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
import java.util.HashMap;

public class InfoCouponActivity extends AppCompatActivity {
    UserSessionManager session;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_coupon);
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String idUtente = user.get(UserSessionManager.KEY_ID_USER);
        Intent intent = getIntent();


        final int i = intent.getIntExtra("indice", -1);
        connectionDetector conn = new connectionDetector(getApplicationContext());
        if (conn.isConnectingToInternet()) {


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        final JSONArray jsonarray = new JSONArray(response);
                        if (jsonarray.length() > 0) {

                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String ditta = jsonobject.getString("ditta");

                            String scadenza = jsonobject.getString("scadenza");
                            String tipo = jsonobject.getString("tipo");
                            String descrizione = jsonobject.getString("descrizione");
                            String valore = jsonobject.getString("valore");
                            String stato = "";
                            //converto strinin data
                            String dateString = scadenza;
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(dateString);

                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            //data di oggi
                            Date oggi = new Date(); // Data di oggi

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Qui decido il formato di visualizzazione
                            sdf.format(oggi);
                            //controllo data
                            if (oggi.before(convertedDate) || scadenza == "null") {
                                stato = "attivo";
                            } else {
                                stato = "scaduto";
                            }

                            //controllo spazzi nulli
                            if (descrizione == "null") {
                                descrizione = "-";
                            }
                            if (valore.equals("-1")) {
                                valore = "-";

                            }
                            if (scadenza == "null") {
                                scadenza = "-";
                            }


                            //riempo listview
                            lista = (ListView) findViewById(R.id.listViewInfoCoupon);

                            String[] array = {stato, ditta, scadenza, tipo, descrizione, valore};

                            String[] array2 = {"Stato", "Ditta", "Scadenza", "Tipo", "Descrizione", "Valore"};
                            lista.setAdapter(new CustomAdapter(InfoCouponActivity.this, array, array2));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Non hai coupon associati!!",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            CouponRequest couponrequest = new CouponRequest(idUtente, responseListener);
            RequestQueue queue2 = Volley.newRequestQueue(InfoCouponActivity.this);
            queue2.add(couponrequest);


        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(InfoCouponActivity.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();

        }
    }
}


