package com.httpstest;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.httpstest.httpstoolbox.ExtHttpClientStack;
import com.httpstest.httpstoolbox.SslHttpClient;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView tv_https_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_https_response = findViewById(R.id.tv_https_response);
    }

    public void httpsGet(View v){
        String path = "https://helloworld.com:8088";
        // Replace R.raw.test with your keystore
        InputStream keyStore = getResources().openRawResource(R.raw.my);


        // Usually getting the request queue shall be in singleton like in {@see Act_SimpleRequest}
        // Current approach is used just for brevity
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this,
                new ExtHttpClientStack(new SslHttpClient(keyStore, "jiangBUG", 8088)));

        StringRequest myReq = new StringRequest(Request.Method.GET, path,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        queue.add(myReq);
    }

    private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv_https_response.setText(response);
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_https_response.setText(error.getMessage());
            }
        };
    }
}