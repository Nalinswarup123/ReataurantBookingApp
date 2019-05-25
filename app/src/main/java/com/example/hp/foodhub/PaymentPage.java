package com.example.hp.foodhub;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class PaymentPage extends AppCompatActivity {

    String uName;
    String city,res,selDate,selTime,tableType,tabPos,tabNo;
    TextView t1,t2,t3,t4,t5,t6,t7;
    Button pay;
    String[] tableStatus;
    String[] otherData;
    SharedPreferences userWallet, currUsr;
    int availAmount, tableCharge;
    long bookingId;
    BookTable bookTable;
    BookingOpenHelper bookingOpenHelper;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        in = getIntent();
        bookTable = new BookTable();
        bookingOpenHelper = new BookingOpenHelper(this);
        otherData = new String[7];
       // tableStatus = new String[12];
        pay = findViewById(R.id.pay);
        t1 = findViewById(R.id.t1);  t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);  t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);  t6 = findViewById(R.id.t6);
        t7 = findViewById(R.id.t7);
        setData();

        currUsr = getSharedPreferences("CurrentUser", MODE_PRIVATE );
        uName  = currUsr.getString("CurUser","" );
        userWallet = getSharedPreferences("User Wallet", MODE_PRIVATE );
        availAmount = userWallet.getInt(uName,0 );
    }


    public void setData(){
        tableCharge = in.getIntExtra("tableCharge",0);
        tableStatus = in.getStringArrayExtra("tableStatus");
        otherData = in.getStringArrayExtra("otherData");
//        otherData = bookTable.getOtherData().clone();


        res = otherData[0]; city = otherData[1]; tabPos = otherData[2]; tableType = otherData[3];
        selDate = otherData[4]; selTime = otherData[5]; tabNo = otherData[6];

        t1.setText(t1.getText()+res);  t2.setText(t2.getText()+city);
        t3.setText(t3.getText()+tabPos);  t4.setText(t4.getText()+tableType);
        t5.setText(t5.getText()+selDate);  t6.setText(t6.getText()+selTime);
        t7.setText(t7.getText()+(tableCharge+""));
    }

    public void makePayment(View view){

        if(availAmount<tableCharge){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Your wallet doesnot have enough amount");
            builder.setMessage("Extra amount Rs. "+(tableCharge-availAmount)+ " will be\n" +
                    "deducted from your linked credit card");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor editor = userWallet.edit();
                    editor.putInt(uName,0 );
                    editor.commit();
                    donePayment();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            Dialog dg = builder.create();
            dg.show();
        }
        else {
            SharedPreferences.Editor editor = userWallet.edit();
            editor.putInt(uName,availAmount-tableCharge );
            editor.commit();
            donePayment();
        }



    }

    public void donePayment(){
        //setting booked table data
        tableStatus[Integer.parseInt(tabNo)]="y";
        String bookedTable = "";
        for(int i=0; i<tableStatus.length; i++){
            bookedTable+=tableStatus[i]+" ";
        }
        SharedPreferences bkTable = getSharedPreferences("Booked Table",MODE_PRIVATE );
        SharedPreferences.Editor bkEditor = bkTable.edit();
        bkEditor.putString(res+selDate+selTime, bookedTable);
        bkEditor.commit();
        //setting booking id data
        SharedPreferences Id = getSharedPreferences("RecentId",MODE_PRIVATE );
        bookingId = Id.getLong("lastID",1000065 );
        bookingId+=67;
        SharedPreferences.Editor editor = Id.edit();
        editor.putLong("lastID",bookingId );
        editor.commit();
        //setting booking data
        bookingOpenHelper.insertItem(bookingId,tableCharge,uName,otherData);
        //showing final alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Booking Confirmed");
        builder.setMessage("Your Booking ID: "+bookingId);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PaymentPage.this,Home.class);
                startActivity(intent);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();







    }
}
