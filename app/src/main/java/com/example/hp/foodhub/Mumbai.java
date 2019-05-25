package com.example.hp.foodhub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Mumbai extends AppCompatActivity {

    String city,res,selDate,selTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mumbai);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        city = "Mumbai";

    }

    public void chooseOrdertype(View view) {
        //get name of restaurant

        ViewGroup nview = (ViewGroup)view;
        for(int i=0; i<nview.getChildCount(); i++){
            ViewGroup v = (ViewGroup) nview.getChildAt(i);
            Toast.makeText(getApplicationContext(), v.getChildCount()+"",Toast.LENGTH_SHORT ).show();
            for(int j=0; j<v.getChildCount(); j++){
                View vw = v.getChildAt(j);
                if(vw instanceof TextView){
                    res=((TextView)vw).getText().toString();
                }
            }

        }



                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Mumbai.this);
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
                            Intent intent = new Intent(Mumbai.this,BookTable.class);
                            intent.putExtra("City", city);
                            intent.putExtra("Restaurant",res);
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
}
