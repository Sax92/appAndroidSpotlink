package com.example.sasha.appspotlink;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasha on 11/08/16.
 */
public class UpdateProfiloRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://panel.spot-link.it/android/updateProfile.php";


    private Map<String, String> params;

    public UpdateProfiloRequest(String idUser, String nome, String cogn, String email, String data, String indirizzo, String cel, String tel, String cf, String piva, String provincia, String comune, String cap, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUser", idUser);
        params.put("nome", nome);
        params.put("cognome", cogn);
        params.put("email", email);
        params.put("telefono", tel);
        params.put("cellulare", cel);
        params.put("data_nascita", data);
        params.put("indirizzo", indirizzo);
        params.put("pIva", piva);
        params.put("cf", cf);
        params.put("comune", comune);
        params.put("provincia", provincia);
        params.put("cap", cap);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
