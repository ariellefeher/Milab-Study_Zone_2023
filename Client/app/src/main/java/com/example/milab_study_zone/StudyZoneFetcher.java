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

public class StudyZoneFetcher {

    private RequestQueue _queue;
    private final static String REQUEST_URL = "http://10.0.2.2:3000/";
    private final static String UPLOAD_RES_URL = "createreservation";
    private final static String GET_RES_URL = "buildingsreservation";

    public class StudyZoneResponse {
        public boolean isError;
        public String username;
        public Boolean success;
        public JSONArray study_reservations;


        public StudyZoneResponse(boolean isError, String username, Boolean success, JSONArray study_reservations) {
            this.isError = isError;
            this.username = username;
            this.success = success;
            this.study_reservations = study_reservations;

        }
    }

    public interface StudyZoneResponseListener {
        public void onResponse(StudyZoneResponse response);
    }

    public StudyZoneFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private StudyZoneResponse createErrorResponse() {
        return new StudyZoneResponse(true, null, null, null);
    }

    public void dispatchRequest(final String username, final String password, final StudyZoneResponseListener listener, final String requestType) {
       int requestMethod = -1;
       String full_url = "";

        if(requestType == "GET") {
            requestMethod = 0;
            full_url = REQUEST_URL + GET_RES_URL;
        }
       else if(requestType == "POST") {
            requestMethod = 1;
            full_url = REQUEST_URL + UPLOAD_RES_URL;
        }
        JsonObjectRequest req = new JsonObjectRequest(requestMethod, full_url + "?username=" + username + "&password=" + password, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            StudyZoneResponse res = new StudyZoneResponse(false,
                                    response.getString("username"),
                                    response.getBoolean("success"),
                                   response.getJSONArray("study-reservations") );

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
