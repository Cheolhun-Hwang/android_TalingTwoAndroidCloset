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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        HttpURLConnection con = null;
        BufferedReader reader = null;
        String url_string = "https://192.168.43.130:65001/weather";
        try{
            URL url = new URL(url_string);
            con = (HttpURLConnection) url.openConnection();
            con.connect();

            InputStream stream = con.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }

            return buffer.toString();

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null){
                con.disconnect();
            }
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "None";
    }
}
