package com.example.hooney.tailing_week_two;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        event();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                changeActivity();
            }
        });


        thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void init(){

    }

    private void event(){

    }

    private void changeActivity(){
        try {
            Thread.sleep(3000);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
