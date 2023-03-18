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

public class PostStudyZoneFetcher {
    private RequestQueue _queue;
    private final static String REQUEST_URL = "http://10.0.2.2:3000/buildingreservations";

    public class PostStudyZoneResponse {
        public boolean isError;
        public String location;
        public Boolean success;
        public JSONArray study_reservations;


        public PostStudyZoneResponse(boolean isError, String location, Boolean success, JSONArray study_reservations) {
            this.isError = isError;
            this.location = location;
            this.success = success;
            this.study_reservations = study_reservations;

        }
    }

    public interface PostStudyZoneResListener {
        public void onResponse(PostStudyZoneResponse response);
    }

    public PostStudyZoneFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private PostStudyZoneResponse createErrorResponse() {
        return new PostStudyZoneResponse(true, null, null, null);
    }

    public void dispatchRequest(final String username, final PostStudyZoneResListener listener) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL + "?location=" + username, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PostStudyZoneResponse res = new PostStudyZoneResponse(false,
                                    response.getString("location"),
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
