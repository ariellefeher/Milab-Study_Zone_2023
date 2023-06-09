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
    private final static String REQUEST_URL = "http://10.0.2.2:3000/createreservation";

    public class PostStudyZoneResponse {
        public boolean isError;
        public String location;
        public String day;
        public String username;
        public Boolean success;



        public PostStudyZoneResponse(boolean isError, String location, String day, String username, Boolean success) {
            this.isError = isError;
            this.location = location;
            this.username = username;
            this.day = day;
            this.success = success;


        }
    }

    public interface PostStudyZoneResListener {
        public void onResponse(PostStudyZoneResponse response);
    }

    public PostStudyZoneFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private PostStudyZoneResponse createErrorResponse() {
        return new PostStudyZoneResponse(true, null, null, null, null);
    }

    public void dispatchRequest(final String location, final String day, final String username, final PostStudyZoneResListener listener) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL + "?location=" + location + "&day=" + day + "&username=" + username, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PostStudyZoneResponse res = new PostStudyZoneResponse(false,
                                    response.getString("location"),
                                    response.getString("day"),
                                    response.getString("username"),
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
