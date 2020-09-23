package com.roborosx.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

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

public class Stats extends AppCompatActivity {

    RecyclerView recyclerView;
    StatsAdapter adapter,adapter2;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<StatsInfo> arrayList,arrayList2;
    ProgressBar progressBar;
    SearchView searchView;
    Context context;
    String state,url="https://api.rootnet.in/covid19-in/stats/latest";
    int confirmed=0,discharged=0,death=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        context=this;
        Data();
        searchMethod();
    }

    private void searchMethod() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                changeAdapter(newText);
                return true;
            }

        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setAdapter(adapter);
                return true;
            }
        });

    }

    private void changeAdapter(String newText) {
        boolean condition=false;
        arrayList2.clear();
        if(newText.isEmpty()) {
            recyclerView.setAdapter(adapter);
            condition=true;
        }
        else {
            for (int i = 0; i < arrayList.size(); i++) {
                StatsInfo info = arrayList.get(i);
                if (info.getSState().toLowerCase().contains(newText.trim().toLowerCase())) {
                    arrayList2.add(new StatsInfo(info.getSState(), info.getConfirmed(), info.getDischarged(), info.getDeath()));
                    adapter2 = new StatsAdapter(arrayList2, context);
                    recyclerView.setAdapter(adapter2);
                    condition = true;
                }
            }
        }

        if(!condition) {
            Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();
            arrayList2.clear();
            adapter2=new StatsAdapter(arrayList2,context);
            recyclerView.setAdapter(adapter2);
        }
    }


    public void Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("data");
                            JSONArray array=jsonObject.getJSONArray("regional");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                state = object.getString("loc");
                                confirmed = object.getInt("confirmedCasesIndian");
                                discharged = object.getInt("discharged");
                                death = object.getInt("deaths");
                                arrayList.add(new StatsInfo(state,confirmed,discharged,death));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.VISIBLE);
                        adapter=new StatsAdapter(arrayList,context);
                        layoutManager=new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(context, ""  + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void Back(View view) {
        finish();
    }
}