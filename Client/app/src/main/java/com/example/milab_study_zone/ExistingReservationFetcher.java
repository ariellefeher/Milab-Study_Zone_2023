package com.example.milab_study_zone;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExistingReservationFetcher {
    private RequestQueue _queue;
    private final static String REQUEST_URL = "http://10.0.2.2:3000/getreservations";

    public class ExisResResponse {
        public boolean isError;
        public String username;
        public Boolean success;
        public JSONArray study_reservations;


        public ExisResResponse(boolean isError, String username, Boolean success, JSONArray study_reservations) {
            this.isError = isError;
            this.username = username;
            this.success = success;
            this.study_reservations = study_reservations;

        }
    }

    public interface ExisResResponseListener {
        public void onResponse(ExisResResponse response);
    }

    public ExistingReservationFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private ExisResResponse createErrorResponse() {
        return new ExisResResponse(true, null, null, null);
    }

    public void dispatchRequest(final String username, final String password, final ExisResResponseListener listener) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL + "?username=" + username + "&password=" + password, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ExisResResponse res = new ExisResResponse(false,
                                    response.getString("username"),
                                    response.getBoolean("success"),
                                    response.getJSONArray("study_reservations"));

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
