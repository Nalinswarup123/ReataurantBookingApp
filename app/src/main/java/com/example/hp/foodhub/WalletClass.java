package com.example.hp.foodhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WalletClass extends Fragment {
    String uName;
    TextView walAmount;
    Button btnPaytm, btnCard;
    SharedPreferences sp1, sp2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_wallet,container,false );
        walAmount = view.findViewById(R.id.tv_E_money);
        btnPaytm = view.findViewById(R.id.addViaPaytm);
        btnCard = view.findViewById(R.id.addViaCredit);
        sp1 = getActivity().getSharedPreferences("CurrentUser",Context.MODE_PRIVATE );
        uName  = sp1.getString("CurUser","" );
        sp2 = getActivity().getSharedPreferences("User Wallet", Context.MODE_PRIVATE );

        initializeAmount();

        btnPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                callDialogue(view);
            }
        });

        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                callDialogue(view);
            }
        });

        return view;
    }

    public void callDialogue(View v){
        final View bView = getLayoutInflater().inflate(R.layout.wallet_view,(ViewGroup)v.findViewById(R.id.wal_view) );
        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Enter amount to add");
        builder.setCancelable(false);
        builder.setView(bView);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText addMoney = bView.findViewById(R.id.add_money);
                if(addMoney.length()!=0){
                    int money = Integer.parseInt(walAmount.getText().toString())
                            +Integer.parseInt(addMoney.getText().toString());
                    walAmount.setText(money+"");
                    SharedPreferences.Editor editor = sp2.edit();
                    editor.putInt(uName,money );
                    editor.commit();
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void initializeAmount(){
         int uAmount = sp2.getInt(uName,0 );
         walAmount.setText(uAmount+"");

    }
}
