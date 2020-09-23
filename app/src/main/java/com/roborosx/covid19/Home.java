package com.roborosx.covid19;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends Fragment {

    View view;
    String url="https://api.rootnet.in/covid19-in/stats/latest";
    ArrayList<Entry> values;
    ArrayList<String> test;
    TextView total,male,female,unknown;
    int discharged=0,active=0,death=0,Total=0;
    PieChart pieChart;
    ArrayList<PieEntry> arrayList1;
    ProgressBar progressBar;
    CardView cardView;
    Button button;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home,container,false);
        values=new ArrayList<>();
        test=new ArrayList<>();
        arrayList1=new ArrayList<>();
        loadId();
        Data();
        return view;
    }

    private void loadId() {
        pieChart=view.findViewById(R.id.pieChart);
        total=view.findViewById(R.id.c1);
        male=view.findViewById(R.id.c2);
        female=view.findViewById(R.id.c3);
        unknown=view.findViewById(R.id.c4);
        progressBar=view.findViewById(R.id.progressBar);
        cardView=view.findViewById(R.id.card);
        progressBar.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        button=view.findViewById(R.id.more);
        button.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Stats.class);
                startActivity(intent);
            }
        });
    }


    public void Data(){
        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object=response.getJSONObject("data");
                            JSONObject object1 = object.getJSONObject("summary");
                            Total = object1.getInt("confirmedCasesIndian");
                            discharged = object1.getInt("discharged");
                            death = object1.getInt("deaths");
                            JSONArray object2 = object.getJSONArray("unofficial-summary");
                            JSONObject jsonObject = object2.getJSONObject(0);
                            active = jsonObject.getInt("active");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pieChart.setVisibility(View.VISIBLE);
                        cardView.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        total.setText("" + Total);
                        male.setText("" + discharged);
                        female.setText("" + active);
                        unknown.setText("" + death);
                        arrayList1.add(new PieEntry(discharged,"Discharged"));
                        arrayList1.add(new PieEntry(active,"Active"));
                        arrayList1.add(new PieEntry(death,"Death"));
                        PieDataSet dataSet=new PieDataSet(arrayList1,"Cases In India");
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        dataSet.setValueTextColor(Color.WHITE);
                        dataSet.setValueTextSize(10f);
                        PieData data=new PieData(dataSet);
                        pieChart.setCenterText("Total Cases");
                        pieChart.setCenterTextColor(Color.BLACK);
                        pieChart.setData(data);
                        progressBar.setVisibility(View.GONE);
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
