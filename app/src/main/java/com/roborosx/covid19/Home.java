package com.roborosx.covid19;

import android.annotation.SuppressLint;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class Home extends Fragment {

    View view;
    String url="https://firebasestorage.googleapis.com/v0/b/noteapp-e3d5c.appspot.com/o/data.json?alt=media&token=3731d429-1926-4d94-b477-bccd519050a5",date;
    ArrayList<Entry> values;
    ArrayList<String> test;
    TextView total,male,female,unknown;
    int Male=0,Female=0,Unknown=0;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home,container,false);
        values=new ArrayList<>();
        test=new ArrayList<>();
        total.setText("Total Cases: 27891");
        male.setText("Total Cases(Male): 15232 (raw data)");
        female.setText("Total Cases(Female): 14543 (raw data)");
        unknown.setText("Total Cases(Unknown): 5003 (raw data)");
        //Data();
        Button button=view.findViewById(R.id.press);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Sorry I don't Know to Implement Line Graph with Given Data", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void Data(){
        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject jsonObject=response.getJSONObject(i);
                                if(jsonObject.get("gender").equals("male"))
                                    Male++;
                                if (jsonObject.get("gender").equals("female"))
                                    Female++;
                                if(jsonObject.get("gender").equals(""))
                                    Unknown++;

                            }

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
