package com.example.sasha.appspotlink;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;

public class ModificaProvCom extends AppCompatActivity {

    String item3;
    String item2;
    UserSessionManager session;
    String nome, cognome, email, data, indirizzo, tel, cel, piva, cf, cap,idUser;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_prov_com);
        final Spinner comune = (Spinner) findViewById(R.id.newCom);
        final Spinner provincia = (Spinner) findViewById(R.id.newProv);
        session = new UserSessionManager(getApplicationContext());
        user = session.getUserDetails();
        // recupero email dalla shared della sessione
        idUser = user.get(UserSessionManager.KEY_ID_USER);
        nome = user.get(UserSessionManager.KEY_NOME);
        cognome = user.get(UserSessionManager.KEY_COGNOME);
        email = user.get(UserSessionManager.KEY_EMAIL);
        data = user.get(UserSessionManager.KEY_DATA_NASCITA);
        indirizzo = user.get(UserSessionManager.KEY_INDIRIZZO);

        tel = user.get(UserSessionManager.KEY_TEL);
        cel = user.get(UserSessionManager.KEY_CEL);
        piva = user.get(UserSessionManager.KEY_PIVA);
        cf = user.get(UserSessionManager.KEY_CF);

        cap = user.get(UserSessionManager.KEY_CAP);

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




                                    item3 = provincia.getSelectedItem().toString();



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


                                    ComuniRequest comuniRequest = new ComuniRequest(item3, responseListener2);
                                    RequestQueue queue2 = Volley.newRequestQueue(ModificaProvCom.this);
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
            RequestQueue queue2 = Volley.newRequestQueue(ModificaProvCom.this);
            queue2.add(provinceRequest);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ModificaProvCom.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();
        }

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.modifica_profilo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ok_modifica) {
            String comune2 =item2;
            String provincia2 =item3;
            if(item.equals("Seleziona provincia")){
                comune2 ="";
                provincia2 ="";

            }


            connectionDetector conn = new connectionDetector(getApplicationContext());
            if (conn.isConnectingToInternet()) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Intent intent = new Intent(ModificaProvCom.this, ProfiloActivity.class);
                                ModificaProvCom.this.startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Modifica completata!",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaProvCom.this);
                                builder.setMessage("Modifica fallita")
                                        .setNegativeButton("Riprova", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                UpdateProfiloRequest updateRequest = new UpdateProfiloRequest(idUser, nome, cognome, email, data, indirizzo, cel, tel, cf, piva,provincia2, comune2, cap, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ModificaProvCom.this);
                queue.add(updateRequest);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaProvCom.this);
                builder.setMessage("Connessione di rete assente")
                        .setNegativeButton("Riprova", null)
                        .create()
                        .show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
