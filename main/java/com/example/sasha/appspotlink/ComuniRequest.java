package com.example.sasha.appspotlink;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasha on 31/07/16.
 */
public class ComuniRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="http://panel.spot-link.it/android/getComune.php";


    private Map<String, String> params;

    public ComuniRequest(  String prov,Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("prov", prov);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
