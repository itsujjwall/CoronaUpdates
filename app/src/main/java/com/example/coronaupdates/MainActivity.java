package com.example.coronaupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Corona corona=new Corona();
    private TextView g_confirmed_cases,g_death,g_recovered;
    private RecyclerView recyclerView;
    private List<Countries> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Corona Updates");
        getSupportActionBar().setSubtitle("by: Ujjwal Kumar");

        list=new ArrayList<>();
        init();
        loadData();
    }

    private void init() {
        g_confirmed_cases=findViewById(R.id.global_total_confirmed_Cases);
        g_death=findViewById(R.id.global_total_confirmed_death);
        g_recovered=findViewById(R.id.global_total_confirmed_recovered);
        recyclerView=findViewById(R.id.country_wise_list);

        adapter=new MyAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url="https://api.covid19api.com/summary";

        //for making new request
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //success
                Log.e("Response",response.toString());

                Gson gson=new Gson();
                corona=gson.fromJson(response.toString(),Corona.class);
                String str1=corona.getGlobal().getTotalConfirmed();
               // Log.e("total cases",str1);
                g_confirmed_cases.setText("Total Confirmed Cases : "+corona.getGlobal().getTotalConfirmed());
                g_death.setText("Total Death : "+corona.getGlobal().getTotalDeaths());
                g_recovered.setText("Total Recovered : "+corona.getGlobal().getTotalRecovered());
                list.clear();
                for (Countries c:corona.getCountries()){
                    list.add(c);
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //error
              Log.e("Error",error.toString());
            }
        });
        //adding request to request queue
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.refresh){
            Toast.makeText(this, "Reloading....", Toast.LENGTH_LONG).show();
            loadData();
        }
        return super.onOptionsItemSelected(item);
    }
}
