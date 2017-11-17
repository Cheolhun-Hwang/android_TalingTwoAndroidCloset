package com.example.hooney.tailing_week_two.Fragments;


import android.content.Entity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hooney.tailing_week_two.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class DressFragment extends Fragment {
    private View view;

    private Button getWeather;
    private TextView showWeather;


    public DressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dress, container, false);

        init();
        event();

        return view;
    }

    private void init(){
        getWeather = (Button) view.findViewById(R.id.getWeatherBTN);
        showWeather = (TextView) view.findViewById(R.id.showWeatherTextview);
    }

    private void event(){
        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showWeather.setText(getWeatherToServer());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private String getWeatherToServer() throws Exception {
        HttpClient client = new DefaultHttpClient();
        String url = "http://192.168.83.1:65001/weather";

        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity resEntity = response.getEntity();
        if(resEntity != null){
            return EntityUtils.toString(resEntity);
        }else{
            return "None";
        }


    }
}
