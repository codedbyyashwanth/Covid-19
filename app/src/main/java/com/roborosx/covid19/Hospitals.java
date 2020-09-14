package com.roborosx.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
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
    HospitalAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HospitalInfo> arrayList;
    String url="https://api.rootnet.in/covid19-in/hospitals/beds",State;
    int UHospital,RHospital,UBed,RBed;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        context=this;
        arrayList=new ArrayList<>();
        Data();
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

                            recyclerView=findViewById(R.id.recyclerView);
                            recyclerView.setHasFixedSize(true);
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