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

public class Hospitals extends AppCompatActivity {

    RecyclerView recyclerView;
    HospitalAdapter adapter,adapter2;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HospitalInfo> arrayList,arrayList2;
    String url="https://api.rootnet.in/covid19-in/hospitals/beds",State;
    int UHospital,RHospital,UBed,RBed;
    Context context;
    ProgressBar progressBar;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        context=this;
        searchView=findViewById(R.id.searchView1);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
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
                HospitalInfo info = arrayList.get(i);
                if (info.getState().toLowerCase().contains(newText.trim().toLowerCase())) {
                    arrayList2.add(new HospitalInfo(info.getUHospital(), info.getUBed(), info.getRHospital(), info.getRBed(), info.getState()));
                    adapter2 = new HospitalAdapter(arrayList2, context);
                    recyclerView.setAdapter(adapter2);
                    condition = true;
                }
            }
        }

        if(!condition) {
            Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();
            arrayList2.clear();
            adapter2=new HospitalAdapter(arrayList2,context);
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
                            JSONObject JsonObject=response.getJSONObject("data");
                            JSONArray array=JsonObject.getJSONArray("regional");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                UHospital = object.getInt("urbanHospitals");
                                UBed = object.getInt("urbanBeds");
                                RHospital = object.getInt("ruralHospitals");
                                RBed = object.getInt("ruralBeds");
                                State = object.getString("state");

                                arrayList.add(new HospitalInfo(UHospital,UBed,RHospital,RBed,State));
                            }

                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            layoutManager=new LinearLayoutManager(context);
                            adapter=new HospitalAdapter(arrayList,context);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(Hospitals.this, ""  + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void Back(View view) {
        finish();
    }
}