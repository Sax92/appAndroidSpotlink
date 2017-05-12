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

import java.util.HashMap;

public class InfoRifornimentiActivity extends AppCompatActivity {
    UserSessionManager session;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rifornimenti);
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String idUtente = user.get(UserSessionManager.KEY_ID_USER);
        Intent intent = getIntent();

        final int i = intent.getIntExtra("indice", -1);

        connectionDetector conn = new connectionDetector(getApplicationContext());
        if (conn.isConnectingToInternet()) {

            //query per lista rifornimenti
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        final JSONArray jsonarray = new JSONArray(response);

                        if (jsonarray.length() > 0) {

                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String datai = jsonobject.getString("dataStart");
                            String dataf = jsonobject.getString("dataStop");
                            String wcaricati = jsonobject.getString("kwTot");
                            String importo = jsonobject.getString("importoTot");
                            String indirizzo = jsonobject.getString("address");


                            //riempo listview
                            lista = (ListView) findViewById(R.id.listViewInfoRifornimenti);

                            String[] array = {datai, dataf, wcaricati, importo, indirizzo};

                            String[] array2 = {"Data e ora inizio", "Data e ora fine", "W ricaricati", "Importo € ", "Indirizzo"};
                            lista.setAdapter(new CustomAdapter(InfoRifornimentiActivity.this, array, array2));


                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Non hai effettuato ricariche!!",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RifornimentiRequest rifornimentirequest = new RifornimentiRequest(idUtente, responseListener);
            RequestQueue queue2 = Volley.newRequestQueue(InfoRifornimentiActivity.this);
            queue2.add(rifornimentirequest);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(InfoRifornimentiActivity.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();
        }
    }
}

