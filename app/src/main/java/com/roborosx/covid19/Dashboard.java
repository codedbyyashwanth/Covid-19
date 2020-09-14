package com.roborosx.covid19;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends Fragment {

    View view;
    String url="https://api.rootnet.in/covid19-in/hospitals/beds";
    int UBed,RBed,UHospital,RHospital;
    TextView BedText,HospitalText,TotalBeds,TotalHospitals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard,container,false);
        loadIds();
        Data();
        clickListener();
        return view;
    }

    private void clickListener() {
        Button BHospital=view.findViewById(R.id.Hospitals);
        Button BColleges=view.findViewById(R.id.collages);

        BHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),Hospitals.class);
                startActivity(intent);
            }
        });

        BColleges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),Colleges.class);
                startActivity(intent);
            }
        });
    }

    private void loadIds() {
        BedText=view.findViewById(R.id.bed);
        HospitalText=view.findViewById(R.id.hospital);
        TotalBeds=view.findViewById(R.id.totalBeds);
        TotalHospitals=view.findViewById(R.id.totalHospitals);
    }

    public void Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object=response.getJSONObject("data").getJSONObject("summary");
                            UBed = object.getInt("urbanBeds");
                            RBed = object.getInt("ruralBeds");
                            UHospital = object.getInt("urbanHospitals");
                            RHospital = object.getInt("ruralHospitals");
                            BedText.setText("Beds\n" + "Rural: " + RBed + "\nUrban: " + UBed);
                            HospitalText.setText("Hospitals\n" + "Rural: " + RHospital + "\nUrban: " + UHospital);
                            TotalBeds.setText("Total Beds: " + (RBed+UBed));
                            TotalHospitals.setText("Total Hospitals: " + (RHospital+UHospital));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(view.getContext(), ""  + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        requestQueue.add(jsonObjectRequest);
    }
}
