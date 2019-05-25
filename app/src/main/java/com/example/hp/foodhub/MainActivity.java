package com.example.hp.foodhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ProfileOpenHelper profileOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysplash);

        profileOpenHelper = new ProfileOpenHelper(this);

        splash();


    }


    public void splash(){
        progressBar = findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFcc0000,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(ifLogedIn()){
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
                else {
                setContentView(R.layout.activity_main);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame,new Login() );
                fragmentTransaction.commit();
                }
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);

                }catch (Exception e){}
            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }

    public void changeFrag(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(view.getId()==R.id.logntv){
            fragmentTransaction.replace(R.id.frame,new SignUp() );
            fragmentTransaction.commit();
        }
        else  {
            fragmentTransaction.replace(R.id.frame,new Login() );
            fragmentTransaction.commit();
        }
    }

    public void onClickSignUp(View view){
        SharedPreferences sp = getSharedPreferences("ExistingUsers", MODE_PRIVATE);
        EditText name = findViewById(R.id.txtname),
                mob = findViewById(R.id.txtmob),
                city = findViewById(R.id.txtcity),
                country = findViewById(R.id.txtcountry),
                sgnpswd = findViewById(R.id.crtpswd),
                sgnconfpswd = findViewById(R.id.cnfmpswd),
                userName = findViewById(R.id.regstrid);
        String[] s = new String[5];
        s[0]=userName.getText().toString();
        s[1]=name.getText().toString();
        s[2]=mob.getText().toString();
        s[3]=city.getText().toString();
        s[4]=country.getText().toString();

        //name validation
        if(name.length()<1){
            name.setError("Required Field");
            return;
        }

        //mobile validation
        if(mob.length()!=10){
            mob.setError("Mobile no must be 10 digits long");
            return;
        }

        //city validation
        if(city.length()<1){
            city.setError("Required Field");
            return;
        }

        //country validation
        if(country.length()<1){
            country.setError("Required Field");
            return;
        }

        //Email validation setup
        EmailValidator mEmailValidator = new EmailValidator();
        userName.addTextChangedListener(mEmailValidator);
        userName.setText(userName.getText().toString().trim());

        //Email Validation
        if(!mEmailValidator.isValid()){
            userName.setError("Invalid email");
            return;
        }
        //If user already exists
        if(sp.contains(userName.getText().toString())){
            userName.setError("User already exists");
            return;
        }
        //Checkinf valid password
        if(sgnpswd.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Password must contain some character" ,Toast.LENGTH_SHORT ).show();
            return;
        }
        //Creating User
        if(sgnpswd.getText().toString().equals(sgnconfpswd.getText().toString())){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(userName.getText().toString(),sgnpswd.getText().toString());
            if(editor.commit()){
                Toast.makeText(getApplicationContext(), "Account Created...Login to continue",Toast.LENGTH_SHORT ).show();
                userProfile(s);  //storing user data
                initialAmount(userName.getText().toString());  //initial amount in user wallet
                TextView t = findViewById(R.id.sgntv);
                changeFrag(t);
            }



        }
        else
            Toast.makeText(getApplicationContext(),"Passwords must be same in both fields" ,Toast.LENGTH_SHORT ).show();
    }

    public void onClickLogin(View view){
        SharedPreferences sp = getSharedPreferences("ExistingUsers", MODE_PRIVATE),
                sp2 = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        EditText userName = findViewById(R.id.emailid),password = findViewById(R.id.pswd);

        //validation setup
        EmailValidator mEmailValidator = new EmailValidator();
        userName.addTextChangedListener(mEmailValidator);
        userName.setText(userName.getText().toString().trim());
        //Email Validation
        if(!mEmailValidator.isValid()){
            userName.setError("Invalid email");
            return;
        }
        //login verification
        if(sp.contains(userName.getText().toString()) &&
                sp.getString(userName.getText().toString(),"" )
                        .equals(password.getText().toString())){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            try{
                FileOutputStream fos = openFileOutput(R.string.login_status+"",MODE_PRIVATE );
                fos.write(1);
                fos.close();
            }catch (Exception e){}

            //current user
            SharedPreferences.Editor editor = sp2.edit();
            editor.putString("CurUser", userName.getText().toString());
            editor.commit();

            Toast.makeText(getApplicationContext(), "LoggedIn", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Invalid Username or Password" ,Toast.LENGTH_SHORT ).show();
        }

    }

    public boolean ifLogedIn(){
        boolean bool = false;
        try{
            FileInputStream fis = openFileInput(R.string.login_status+"");
            int i = fis.read();
            fis.close();
            if(i==1)
                bool = true;
        }catch (Exception e){}

        return bool;

    }

    public void userProfile(String... s){
        profileOpenHelper.insertItem(s);
    }

    public void initialAmount(String name){
        SharedPreferences sp = getSharedPreferences("User Wallet",MODE_PRIVATE );
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, 1500);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
