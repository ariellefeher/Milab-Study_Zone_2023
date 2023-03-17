package com.example.milab_study_zone;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFetcher {
    private RequestQueue _queue;
    private final static String REQUEST_URL = "http://10.0.2.2:3000/login";

    public class LoginResponse {
        public boolean isError;
        public String username;
        public String password;
       // public String lwtToken;
        public Boolean success;


        public LoginResponse(boolean isError, String username, String password, Boolean success) {
            this.isError = isError;
            this.username = username;
            this.password = password;
            this.success = success;
         //   this.lwtToken = lwtToken;
        }
    }

    public interface LoginResponseListener {
        public void onResponse(LoginResponse response);
    }

    public LoginFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private LoginResponse createErrorResponse() {
        return new LoginResponse(true, null, null, null);
    }

    public void dispatchRequest(final String username, final String password, final LoginResponseListener listener) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL + "?username=" + username + "&password=" + password, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            LoginResponse res = new LoginResponse(false,
                                    response.getString("username"),
                                    response.getString("password"),
                                    response.getBoolean("success"));

                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }
}
//ariellefeher
//123456