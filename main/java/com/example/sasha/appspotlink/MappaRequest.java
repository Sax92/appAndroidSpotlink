package com.example.sasha.appspotlink;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasha on 04/07/16.
 */
public class MappaRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://panel.spot-link.it/android/markerMappa.php";
    private Map<String, String> params;

    public MappaRequest(String idUser, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUser", idUser);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
