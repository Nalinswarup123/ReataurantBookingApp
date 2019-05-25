package com.example.hp.foodhub;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class BookTable extends AppCompatActivity {

    int tableCharge;
    String city,res,selDate,selTime,tableType,tabPos,tabNo;
    String[] tableStatus;
    ViewGroup gridLayout;
    PaymentPage paymentPage;
    String[] otherData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);

        Intent intent = getIntent();
        city = intent.getStringExtra("City");
        res = intent.getStringExtra("Restaurant");
        selDate = intent.getStringExtra("Date");
        selTime = intent.getStringExtra("Time");
        gridLayout = findViewById(R.id.grdly);
        paymentPage = new PaymentPage();
        otherData = new String[7];


        otherData[0]=res; otherData[1]=city; otherData[4]=selDate; otherData[5]=selTime;

        setTableView();

    }

    public void cardPosition(final View view) {
        //get table type, position and table number
        String tableTag = ((String)view.getTag());
        tabPos  = tableTag.substring(1,tableTag.length());
        tabNo = tableTag.substring(0,1);
        ViewGroup nview = (ViewGroup)view;
        for(int i=0; i<nview.getChildCount(); i++){
            ViewGroup v = (ViewGroup) nview.getChildAt(i);
            for(int j=0; j<v.getChildCount(); j++){
                View vw = v.getChildAt(j);
                if(vw instanceof TextView){
                    tableType=((TextView)vw).getText().toString();
                }
            }

        }
        otherData[2]=tabPos;
        otherData[3]=tableType;
        otherData[6]=tabNo;
     //   Toast.makeText(getApplicationContext(),otherData.length+"" , Toast.LENGTH_LONG).show();
        //table charge
        if(tableType.equals("Family Table"))
            tableCharge = 949;
        else
            tableCharge = 499;
        //confirmation alert
        ((CardView)view).setCardBackgroundColor(Color.GREEN);   //Change color to GREEN
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Table Description:");
        builder.setMessage("Table position:\n"+tabPos+"\n\nTable Type:\n"+tableType+"\n\nTable Charge:\n"+"Rs. "+tableCharge);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((CardView)view).setCardBackgroundColor(Color.parseColor("#eb05baba"));
                Intent intent = new Intent(BookTable.this, PaymentPage.class);
                intent.putExtra("tableCharge",tableCharge );
                intent.putExtra("tableStatus",tableStatus );
                intent.putExtra("otherData",otherData );
              //  paymentPage.getData(tableCharge,tableStatus,city,res,selDate,selTime,tableType,tabPos,tabNo);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((CardView)view).setCardBackgroundColor(Color.parseColor("#eb05baba"));
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    public void setTableView(){
        SharedPreferences sp = getSharedPreferences("Booked Table",MODE_PRIVATE );
        tableStatus = sp.getString(res+selDate+selTime,"n n n n n n n n n n n n" ).split(" ");

        for(int i=0; i<gridLayout.getChildCount(); i++){
            ViewGroup v = (ViewGroup) gridLayout.getChildAt(i);
            if(tableStatus[i].equals("y")){
                ((CardView)v).setCardBackgroundColor(Color.parseColor("#a49c9c"));
                v.setEnabled(false);
            }
        }

    }

}
