package com.example.sasha.appspotlink;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasha on 11/08/16.
 */
public class AggiornaDatiRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://panel.spot-link.it/android/getDati.php";
    private Map<String, String> params;

    public AggiornaDatiRequest(String idUser, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUser", idUser);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
