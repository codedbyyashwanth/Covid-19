package com.roborosx.covid19;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class Contact extends Fragment {

    View view;
    RecyclerView recyclerView;
    ContactAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    Context context;
    ArrayList<ContactInformation> arrayList;
    String url="https://api.rootnet.in/covid19-in/contacts",location,number;
    ImageView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts,container,false);
        context = view.getContext();
        arrayList=new ArrayList<>();
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Data();
        clickListener();
        return view;
    }

    private void clickListener() {
        info=view.findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MoreInfo.class);
                startActivity(intent);
            }
        });

    }


    public void Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("data").getJSONObject("contacts");
                            JSONArray array=jsonObject.getJSONArray("regional");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                location=object.getString("loc");
                                number=object.getString("number");
                                number = number.replace(',','\n');
                                arrayList.add(new ContactInformation(location,number));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerView = view.findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        adapter=new ContactAdapter(arrayList,context);
                        layoutManager=new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
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

        progressBar.setVisibility(View.GONE);
    }

}
