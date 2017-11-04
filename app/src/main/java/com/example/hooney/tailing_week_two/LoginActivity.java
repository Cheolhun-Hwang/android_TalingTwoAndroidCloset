package com.example.hooney.tailing_week_two;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooney.tailing_week_two.DataClass.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private EditText email;
    private EditText passwd;
    private Button sign;

    private ArrayList<Account> accList;
    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        event();
    }

    private void init(){
        email = (EditText) findViewById(R.id.Login_email_edittext);
        passwd = (EditText) findViewById(R.id.Login_passwd_edittext);
        sign = (Button) findViewById(R.id.Login_sign_btn);

        accList = new ArrayList<Account>();
    }

    private void event(){

        sign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                getDataToServer();


                String email_s = email.getText().toString();
                String passwd_s = passwd.getText().toString();
                String name_s = "None";
                boolean isLogin = false;


                for(int i = 0; i< accList.size() ; i++){
                    if(email_s.equals(accList.get(i).getID()) && passwd_s.equals(accList.get(i).getPwd())){
                        Toast.makeText(getApplicationContext(), "ID / Password 성공! 이름 : " + accList.get(i).getUname(), Toast.LENGTH_SHORT).show();
                        isLogin = true;
                        user = accList.get(i);
                        break;
                    }
                }



                if( !isLogin ){
                    Toast.makeText(getApplicationContext(), "ID / Password 실패", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                }


            }
        });
    }

    private void getDataToServer(){
        accList.add(new Account("BBB", "123", "고길동"));
        accList.add(new Account("CCC", "123", "가나가"));
        accList.add(new Account("DDD", "123", "라면"));
        accList.add(new Account("EEE", "123", "마우스"));
        accList.add(new Account("FFF", "123", "컴퓨터"));
    }

}

