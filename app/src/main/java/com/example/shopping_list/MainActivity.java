package com.example.shopping_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  String[] items;
  ArrayAdapter adapter;
  ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Make request inside this method
    listView = (ListView) findViewById(R.id.listview);
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    String url = "https://andreashopping.herokuapp.com/api/items";
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        System.out.println(response);
        try {
          JSONArray array = new JSONArray(response);
          items = new String[array.length()];
          adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, items);
          for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            items[i] = object.getString("name");
          }
          listView.setAdapter(adapter);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });
    requestQueue.add(stringRequest);
  }
}
