package com.example.sasha.appspotlink;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasha on 16/06/16.
 */
public class RegisterRequest extends StringRequest {

   private static final String REGISTER_REQUEST_URL="http://panel.spot-link.it/android/registrazione.php";


    private Map<String, String> params;

    public RegisterRequest(String nome, String cogn, String email, String password, String tel, String cel, String data, String indirizzo,String piva,String cf,String citta,String comune,String provincia,String cap, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nome", nome);
        params.put("cognome", cogn);
        params.put("email", email);
        params.put("password", password);
        params.put("fisso", tel);
        params.put("cel", cel);
        params.put("data_nascita", data);
        params.put("indirizzo", indirizzo);
        params.put("pIva", piva);
        params.put("CF", cf);
        params.put("citta", citta);
        params.put("comune", comune);
        params.put("provincia", provincia);
        params.put("cap", cap);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
