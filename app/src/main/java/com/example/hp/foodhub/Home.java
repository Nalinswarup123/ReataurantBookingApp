package com.example.hp.foodhub;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView navHeader1, navHeader2;
    int curId;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    String city,res,selDate,selTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Navigation Header
        View navView = getLayoutInflater().inflate(R.layout.nav_header_home,
                (ViewGroup)findViewById(R.id.nav_view) );
        inflateNavigationHeader(navView);

        //Bottom Navigation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.home1);

        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction ft = f.beginTransaction();
        ft.replace(R.id.frmlay,new FragHome());
        ft.commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            int id = menuItem.getItemId();
            curId = id;
            if(id==R.id.home1){
                navigationView.setCheckedItem(R.id.home0);
                ft.replace(R.id.frmlay,new FragHome() );
                ft.commit();
            }
            else if(id==R.id.person1){
                navigationView.setCheckedItem(R.id.person);
                ft.replace(R.id.frmlay,new ProfileClass() );
                ft.commit();
            }
            else if(id==R.id.book1){
                navigationView.setCheckedItem(R.id.book);
                ft.replace(R.id.frmlay,new FragMyBookings() );
                ft.commit();
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(curId==R.id.home0 || curId==R.id.home1){
                finishAffinity();
            }else{
                navigationView.setCheckedItem(R.id.home0);
                bottomNavigationView.setSelectedItemId(R.id.home1);



//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.frmlay,new ProfileClass());
//                ft.commit();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want\n to signout?");
            builder.setCancelable(false);
            builder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    signOut();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            Dialog dialog = builder.create();
            dialog.show();

        }
        else if(id == R.id.action_exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want\n to exit?");
            builder.setCancelable(false);
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAffinity();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            Dialog dialog = builder.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        curId = id;
        if (id == R.id.home0) {
            bottomNavigationView.setSelectedItemId(R.id.home1);
            ft.replace(R.id.frmlay,new FragHome() );
            ft.commit();
        } else if (id == R.id.person) {
            bottomNavigationView.setSelectedItemId(R.id.person1);
            ft.replace(R.id.frmlay,new ProfileClass() );
            ft.commit();
        } else if (id == R.id.book) {
            bottomNavigationView.setSelectedItemId(R.id.book1);
            ft.replace(R.id.frmlay,new FragMyBookings() );
            ft.commit();

        } else if (id == R.id.wallet) {


            ft.replace(R.id.frmlay,new WalletClass() );
            ft.commit();

        } else if (id == R.id.help) {
            Intent intent = new Intent(Home.this, HelpAndSupport.class);
            startActivity(intent);
        }else if (id == R.id.about) {
            Intent intent = new Intent(Home.this, AboutUs.class);
            startActivity(intent);

        }else if (id == R.id.signout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want\n to signout?");
            builder.setCancelable(false);
            builder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    signOut();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            Dialog dialog = builder.create();
            dialog.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoCity(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.city_hydera:
                intent = new Intent(Home.this,Hyderabad.class);
                startActivity(intent);
                break;
            case R.id.city_mumbai:
                intent = new Intent(Home.this,Mumbai.class);
                startActivity(intent );
                break;
            case R.id.city_bangalore:
                intent = new Intent(Home.this,Bangalore.class);
                startActivity(intent );
                break;
            case R.id.city_delhi:
                intent = new Intent(Home.this,NewDelhi.class);
                startActivity(intent );
                break;
            case R.id.city_chandi:
                intent  = new Intent(Home.this,Chandigarh.class);
                startActivity(intent);
                break;
        }
    }

    public void inflateNavigationHeader(View navView){
        navHeader1 = navView.findViewById(R.id.nav_txtv);
        navHeader2 = navView.findViewById(R.id.textView);
        SharedPreferences sp = getSharedPreferences("CurrentUser",MODE_PRIVATE );
        ProfileOpenHelper profileOpenHelper = new ProfileOpenHelper(this);
        Cursor cursor = profileOpenHelper.getData();
        while(cursor.moveToNext()){
            if((cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.username))
                    .trim()).equals(sp.getString("CurUser","not found" ))){

                navHeader1.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.profile_name)));
                navHeader2.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.username)));
                break;
            }

        }


    }

    public void chooseOrdertype(View view) {
        // Get name of restaurant and city
        ViewGroup nview = (ViewGroup)view;
        for(int i=0; i<nview.getChildCount(); i++){
            ViewGroup v = (ViewGroup) nview.getChildAt(i);
            for(int j=0; j<v.getChildCount(); j++){
                View vw = v.getChildAt(j);
                if(vw instanceof TextView){
                    if(j==1)
                        res=((TextView)vw).getText().toString();
                    else
                        city=((TextView)vw).getText().toString();
                }
            }

        }
        //AlertDialog to pick date and time


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Home.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Select date and time");
                    View view1 = getLayoutInflater().inflate(R.layout.time_date_view,(ViewGroup)findViewById(R.id.tm_dt_vw) );

                    //date
                    ArrayList<String> days = new ArrayList<>();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");  //date format
                    Date curDate = new Date();   //current date
                    Calendar c = Calendar.getInstance();
                    c.setTime(curDate);
                    for(int k=1; k<=5; k++){        //next five days
                        c.add(Calendar.DATE, 1);
                        Date nextDate = c.getTime();
                        days.add(dateFormat.format(nextDate));
                    }
                    Spinner spDate = view1.findViewById(R.id.date_spinner);
                    ArrayAdapter<String> dateAdapter= new ArrayAdapter<String>(view1.getContext(),R.layout.support_simple_spinner_dropdown_item,days);
                    spDate.setAdapter(dateAdapter);
                    onClickDate(spDate);

                    //time
                    String[] stime = {"10:00 am","12:00 am","2:00 pm","4:00 pm","6:00 pm","8:00 pm","10:00 pm"};
                    Spinner spinner = view1.findViewById(R.id.time_spinner);
                    ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(view1.getContext(),R.layout.support_simple_spinner_dropdown_item,stime);
                    spinner.setAdapter(timeAdapter);
                    onClickTime(spinner);

                    builder1.setView(view1);

                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Home.this,BookTable.class);
                            intent.putExtra("City", city.substring(1,city.length()-1));
                            intent.putExtra("Restaurant",res.substring(0,res.length()) );
                            intent.putExtra("Date",selDate );
                            intent.putExtra("Time",selTime );
                            startActivity(intent);
                        }
                    });

                    builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog dg = builder1.create();
                    dg.show();

    }

    public void onClickDate(Spinner spD){
        spD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selDate =  (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClickTime(Spinner spT){
        spT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selTime = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void signOut(){
        try{

            FileOutputStream fos = openFileOutput(R.string.login_status+"",MODE_PRIVATE );
            fos.write(0);
            fos.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);


    }
}
