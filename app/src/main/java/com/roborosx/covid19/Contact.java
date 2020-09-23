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

public class Contact extends Fragment {

    View view;
    RecyclerView recyclerView;
    ContactAdapter adapter,adapter2;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    Context context;
    ArrayList<ContactInformation> arrayList,arrayList2;
    String url="https://api.rootnet.in/covid19-in/contacts",location,number;
    ImageView info;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts,container,false);
        context = view.getContext();
        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        searchView=view.findViewById(R.id.searchView);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        Data();
        clickListener();
        searchMethod();
        return view;
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
                ContactInformation info = arrayList.get(i);
                if (info.getState().toLowerCase().contains(newText.trim().toLowerCase())) {
                    arrayList2.add(new ContactInformation(info.getState(), info.getNumber()));
                    adapter2 = new ContactAdapter(arrayList2, context);
                    recyclerView.setAdapter(adapter2);
                    condition = true;
                }
            }
        }

        if(!condition) {
            Toast.makeText(context, "No Result", Toast.LENGTH_SHORT).show();
            arrayList2.clear();
            adapter2=new ContactAdapter(arrayList2,context);
            recyclerView.setAdapter(adapter2);
        }
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
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.VISIBLE);
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
    }

}
