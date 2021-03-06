package com.example.shogun.universityapplication.requests;

/**
 * Created by Krystian on 2016-11-12.
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetConsultations extends AsyncTask<String, Void, String> {

    private static final String URL = "http://10.7.2.10:8080";
    private Request request;

    @Override
    protected String doInBackground(String[] params) {
        // do above Server call here
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String json = null;
        try {
            json = new JSONObject().put("id_token",params[0]).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, json);
        if(params.length==2) {
             request = new Request.Builder()
                    .url(URL + "/api/consultations/" + params[1])
                    .addHeader("Authorization", "Bearer " + params[0])
                    .build();
        } else {
            request = new Request.Builder()
                    .url(URL + "/api/consultations")
                    .addHeader("Authorization", "Bearer " + params[0])
                    .build();
        }
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
    }
}
