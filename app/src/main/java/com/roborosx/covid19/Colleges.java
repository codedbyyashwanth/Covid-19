package com.roborosx.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class Colleges extends AppCompatActivity {

    RecyclerView recyclerView;
    CollegesAdapter adapter,adapter2;
    ArrayList<CollegesInfo> arrayList,arrayList2;
    ArrayList<String> state,ownership;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    String url="https://api.rootnet.in/covid19-in/hospitals/medical-colleges",State,College,City,OwnerShip;
    int Admission,Beds;
    Spinner spinner1,spinner2;
    ArrayAdapter<String> arrayAdapter1,arrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleges);
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        state=new ArrayList<>();
        ownership=new ArrayList<>();
        spinner1=findViewById(R.id.stateSpin);
        spinner2=findViewById(R.id.ownershipSpin);
        context=this;
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
                            JSONArray array=JsonObject.getJSONArray("medicalColleges");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                State=object.getString("state");
                                College=object.getString("name");
                                City=object.getString("city");
                                OwnerShip=object.getString("ownership");
                                Admission=object.getInt("admissionCapacity");
                                Beds=object.getInt("hospitalBeds");
                                if(state!=null)
                                {
                                    if(!state.contains(State)){
                                        state.add(State);
                                    }
                                }

                                if(ownership!=null)
                                {
                                    if(!ownership.contains(OwnerShip))
                                    {
                                        ownership.add(OwnerShip);
                                    }
                                }
                                arrayList.add(new CollegesInfo(State,College,City,OwnerShip,Admission,Beds));
                            }

                            arrayAdapter1 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, state);
                            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(arrayAdapter1);
                            arrayAdapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,ownership);
                            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(arrayAdapter2);

                            recyclerView.setHasFixedSize(true);
                            adapter=new CollegesAdapter(arrayList,context);
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
                        Toast.makeText(context, ""  + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        requestQueue.add(jsonObjectRequest);
    }

    public void Back(View view) {
        finish();
    }

    public void Search(View view) {
        String spinState = spinner1.getSelectedItem().toString();
        String spinType = spinner2.getSelectedItem().toString();
        boolean condition=false;
        if(!(spinState.equals("") && spinType.equals("")))
        {
            arrayList2.clear();
            for(int i=0;i<arrayList.size();i++)
            {
                CollegesInfo currentList = arrayList.get(i);
                if(currentList.getState().contains(spinState) && currentList.getType().contains(spinType))
                {
                    condition=true;
                    arrayList2.add(new CollegesInfo(currentList.getState(),currentList.getCollege(),currentList.getCity(),currentList.getType(),
                    currentList.getCapacity(),currentList.getBeds()));
                    adapter2=new CollegesAdapter(arrayList2,this);
                    recyclerView.setAdapter(adapter2);
                }
            }

            if(!condition)
                Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();

        }
    }

    public void Clear(View view) {
        recyclerView.setAdapter(adapter);
        spinner1.setAdapter(arrayAdapter1);
        spinner2.setAdapter(arrayAdapter2);
    }
}