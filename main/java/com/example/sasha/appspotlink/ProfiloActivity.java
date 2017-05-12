package com.example.sasha.appspotlink;


import android.content.Intent;

import android.graphics.Color;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ProfiloActivity extends AppCompatActivity {

    UserSessionManager session;
    ImageView foto;
    DownloadImageTask dFoto;
    ListView lista;
    TextView modifica;
    TextView contenuto;
    ImageButton carica;
    String nome, cognome, email, data, indirizzo, tel, cel, piva, cf, provincia, comune, cap;

    private int PICK_IMAGE_REQUEST = 1;

    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);
        //int color = Color.WHITE;
        //carica = (ImageButton) findViewById(R.id.caricaFoto);
        //carica.setBackgroundColor(color);
        foto = (ImageView) findViewById(R.id.fotoProfilo);
        session = new UserSessionManager(getApplicationContext());
        dFoto = new DownloadImageTask(foto);
        user = session.getUserDetails();
        //scarico immagine del profilo
        connectionDetector conn = new connectionDetector(getApplicationContext());
        if(conn.isConnectingToInternet()) {
            dFoto.execute("http://panel.spot-link.it/public/img/usersPhoto/" + user.get(UserSessionManager.KEY_ID_USER));


            //aggiorno dati di sessione
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


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            AggiornaDatiRequest loginRequest = new AggiornaDatiRequest(user.get(UserSessionManager.KEY_ID_USER), responseListener);
            RequestQueue queue = Volley.newRequestQueue(ProfiloActivity.this);
            queue.add(loginRequest);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfiloActivity.this);
            builder.setMessage("Connessione di rete assente")
                    .setNegativeButton("Riprova", null)
                    .create()
                    .show();
        }


        // recupero email dalla shared della sessione
        nome = user.get(UserSessionManager.KEY_NOME);
        cognome = user.get(UserSessionManager.KEY_COGNOME);
        email = user.get(UserSessionManager.KEY_EMAIL);
        data = user.get(UserSessionManager.KEY_DATA_NASCITA);
        indirizzo = user.get(UserSessionManager.KEY_INDIRIZZO);

        tel = user.get(UserSessionManager.KEY_TEL);
        cel = user.get(UserSessionManager.KEY_CEL);
        piva = user.get(UserSessionManager.KEY_PIVA);
        cf = user.get(UserSessionManager.KEY_CF);
        provincia = user.get(UserSessionManager.KEY_PROVINCIA);
        comune = user.get(UserSessionManager.KEY_COMUNE);
        cap = user.get(UserSessionManager.KEY_CAP);


        //controllo valori nulli
        if (data.equals("null")) {
            data = "-";
        }
        if (indirizzo.equals( "null")) {
            indirizzo = "-";
        }

        if (tel.equals( "null")) {
            tel = "-";
        }
        if (cel.equals( "null")) {
            cel = "-";
        }
        if (piva.equals( "null")) {
            piva = "-";
        }
        if (cf.equals( "null")) {
            cf = "-";
        }
        if (provincia.equals( "null")) {
            provincia = "-";
        }
        if (comune.equals( "null")) {
            comune = "-";
        }
        if (cap.equals( "null")) {
            cap = "-";
        }



        lista = (ListView) findViewById(R.id.listaProfilo);

        String[] array = {nome, cognome, email, data, provincia, comune, cap, indirizzo, tel, cel, cf, piva};

        String[] array2 = {"Nome", "Cognome", "E-mail", "Data di nascita", "Provincia", "Comune", "CAP", "Indirizzo", "Telefono", "Cellulare", "C.Fiscale", "P.Iva"};
        lista.setAdapter(new CustomAdapter(ProfiloActivity.this, array, array2));

        // adapter = new Adapter(ProfiloActivity.this, array, array2);
        // lista.setAdapter(adapter);


        //click sull'item della lista con intent su una nuova activity per la modifica del contenuto

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                modifica = (TextView) arg1.findViewById(R.id.titolo);
                contenuto = (TextView) arg1.findViewById(R.id.campoP);
                String campo_modifica = modifica.getText().toString();
                String campo_contenuto= contenuto.getText().toString();

                if(campo_modifica==("Provincia") || campo_modifica==("Comune") ){
                    Intent newModifica = new Intent(ProfiloActivity.this, ModificaProvCom.class);
                    startActivity(newModifica);




                }else {
                    Intent invia = new Intent(ProfiloActivity.this, ModificaProfilo.class);
                    invia.putExtra("campo_contenuto", campo_contenuto);
                    invia.putExtra("campo_modifica", campo_modifica);
                    invia.putExtra("nome", nome);
                    invia.putExtra("cognome", cognome);
                    invia.putExtra("email", email);
                    invia.putExtra("data", data);

                    invia.putExtra("cap", cap);
                    invia.putExtra("indirizzo", indirizzo);
                    invia.putExtra("tel", tel);
                    invia.putExtra("cel", cel);
                    invia.putExtra("cf", cf);
                    invia.putExtra("piva", piva);

                    startActivity(invia);


                }


            }
        });


        //click carica foto
       /* carica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });*/

    }




}
























