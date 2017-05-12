package com.example.sasha.appspotlink;

import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RifornimentiActivity extends AppCompatActivity {
    ListView lista;
    UserSessionManager session;
    HashMap<String, String> user;
    String idUtente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifornimenti);
        session=new UserSessionManager(getApplicationContext());
        user = session.getUserDetails();
        lista=(ListView)findViewById(R.id.listViewRifornimenti);
        idUtente=user.get(UserSessionManager.KEY_ID_USER);

        connectionDetector conn = new connectionDetector(getApplicationContext());
        if (conn.isConnectingToInternet()) {
            //query per lista rifornimenti
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        final JSONArray jsonarray = new JSONArray(response);
                        String[] data = new String[jsonarray.length()];
                        String[] indirizzo2 = new String[jsonarray.length()];
                        if (jsonarray.length() > 0) {
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String datai = jsonobject.getString("dataStart");
                                String dataf = jsonobject.getString("dataStop");
                                String wcaricati = jsonobject.getString("kwTot");
                                String importo = jsonobject.getString("importoTot");
                                String indirizzo = jsonobject.getString("address");

                                data[i] = datai;
                                indirizzo2[i] = indirizzo;

                            }

                            lista.setAdapter(new RifornimentiAdapter(RifornimentiActivity.this, indirizzo2, data));
                            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                                    // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter

                                    int p = pos;
                                    Intent i = new Intent(getApplicationContext(), InfoRifornimentiActivity.class);
                                    i.putExtra("indice", p);

                                    startActivity(i);
                                }
                            });


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
            RequestQueue queue2 = Volley.newRequestQueue(RifornimentiActivity.this);
            queue2.add(rifornimentirequest);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(RifornimentiActivity.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();
        }
    }
}

