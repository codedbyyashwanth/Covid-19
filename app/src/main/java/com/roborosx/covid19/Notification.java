package com.roborosx.covid19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Notification extends Fragment {

    View view;
    String url = "https://api.rootnet.in/covid19-in/notifications",title,link,date;
    ArrayList<NotificationInfo> arrayList;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification, container, false);
        arrayList=new ArrayList<>();
        Data();
        return view;
    }

    public void Data() {
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray array = jsonObject.getJSONArray("notifications");
                            for (int i = 1; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                title=object.getString("title");
                                link=object.getString("link");
                                date=title.substring(0,11);
                                title=title.substring(11);
                                if(!date.contains("2020")) {
                                    date="No Date";
                                }
                                arrayList.add(new NotificationInfo(title, link, date));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView=view.findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        adapter=new NotificationAdapter(arrayList,view.getContext());
                        layoutManager=new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
