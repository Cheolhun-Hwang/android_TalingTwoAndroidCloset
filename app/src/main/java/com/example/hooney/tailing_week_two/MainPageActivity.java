package com.example.hooney.tailing_week_two;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hooney.tailing_week_two.DataClass.Account;
import com.example.hooney.tailing_week_two.Fragments.AccFragment;
import com.example.hooney.tailing_week_two.Fragments.DashFragment;
import com.example.hooney.tailing_week_two.Fragments.DressFragment;
import com.example.hooney.tailing_week_two.Fragments.HomeFragment;
import com.example.hooney.tailing_week_two.Fragments.TempFragment;
import com.example.hooney.tailing_week_two.gridview_home.dressItem;
import com.example.hooney.tailing_week_two.temppa.BottomNavigationViewHelper;

import java.sql.Array;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private Account MainUserInfo;

    private AccFragment accFragment;
    private HomeFragment homeFragment;
    private DashFragment dashFragment;
    private TempFragment tempFragment;
    private DressFragment dressFragment;

    private FragmentManager manager;

    private int flagWho;

    private ArrayList<dressItem> list;

    /*Get Set*/
    public ArrayList<dressItem> getList() {
        return list;
    }

    public void setList(ArrayList<dressItem> list) {
        this.list = list;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_one:
                    manager.beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                    flagWho = 0;
                    return true;
                case R.id.navigation_two:
                    manager.beginTransaction().replace(R.id.content, new DashFragment()).commit();
                    flagWho = 1;
                    return true;
                case R.id.navigation_three:
                    manager.beginTransaction().replace(R.id.content, new DressFragment()).commit();
                    flagWho = 2;
                    return true;
                case R.id.navigation_four:
                    manager.beginTransaction().replace(R.id.content, new TempFragment()).commit();
                    flagWho = 3;
                    return true;
                case R.id.navigation_five:
                    manager.beginTransaction().replace(R.id.content, new AccFragment()).commit();
                    flagWho = 4;
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        MainUserInfo = (Account) getIntent().getSerializableExtra("user");
        if(MainUserInfo == null){
            MainUserInfo = new Account();
        }else{
        }

        init();
        loadDressData();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);

        navigation.setSelectedItemId(R.id.navigation_one);

    }

    private void init(){
        flagWho = -1;
        manager = getSupportFragmentManager();

        list = new ArrayList<dressItem>();


        event();
    }



    public void event(){
    }

    private void loadDressData(){
        for(int i = 0 ; i<5;i++){
            dressItem temp = new dressItem();
            temp.setDressName("Dress " + i);
            temp.setSeason(new int[]{1, 2});
            list.add(temp);
        }

        for(int i = 0;i<list.size();i++){
            Log.d("Data Recive", "Data : " + list.get(i).getDressName());
        }
    }
}
