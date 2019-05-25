package com.example.hp.foodhub;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class ProfileClass extends Fragment {

    TextView head1, head2;
    Button pname, uname, mobno, city, country, btn_changePasswd;
    SharedPreferences sp;
    Home hm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        hm = new Home();
        final View view = inflater.inflate(R.layout.frag_profile,container,false );
        head1 = view.findViewById(R.id.profname);
        head2 = view.findViewById(R.id.usrname);
        pname = view.findViewById(R.id.prof_name);
        uname = view.findViewById(R.id.user_name);
        mobno = view.findViewById(R.id.mob_no);
        city = view.findViewById(R.id.city_name);
        country = view.findViewById(R.id.country_name);
        btn_changePasswd = view.findViewById(R.id.chngpwd);


        sp = this.getActivity().getSharedPreferences("CurrentUser",MODE_PRIVATE );
        ProfileOpenHelper profileOpenHelper = new ProfileOpenHelper(view.getContext());
        Cursor cursor = profileOpenHelper.getData();
        while(cursor.moveToNext()){
            if((cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.username)).trim()).equals(sp.getString("CurUser","not found" ))){

                head1.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.profile_name)));
                head2.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.username)));
                pname.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.profile_name)));
                uname.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.username)));
                mobno.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.mobno)));
                city.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.city)));
                country.setText(cursor.getString(cursor.getColumnIndexOrThrow(profileOpenHelper.country)));
                break;
            }

        }

        btn_changePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                changePassword(view);
            }
        });

        return view;
    }
    public void changePassword(View v){
        View v2 = getLayoutInflater().inflate(R.layout.changepwd_view,(ViewGroup)v.findViewById(R.id.ch_pwd));
        final EditText t1=v2.findViewById(R.id.change_pwd),t2=v2.findViewById(R.id.conf_change_pwd);

        final SharedPreferences sp2 = getActivity().getSharedPreferences("ExistingUsers", MODE_PRIVATE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Change Password");
        builder.setView(v2);
        builder.setCancelable(false);
        builder.setPositiveButton("Change",null );

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if((t1.getText().toString().trim()).length()!=0){
                    if(t1.getText().toString().equals(t2.getText().toString())){
                        SharedPreferences.Editor editor = sp2.edit();
                        editor.putString(sp.getString("CurUser","" ),t1.getText().toString().trim() );
                        editor.commit();
                        Toast.makeText(getContext(),"Password Changed Sucessfully" , Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(),"Login to continue" ,Toast.LENGTH_LONG ).show();

                        try{

                            FileOutputStream fos = getActivity().openFileOutput(R.string.login_status+"",MODE_PRIVATE );
                            fos.write(0);
                            fos.close();

                        }catch (Exception e){
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getContext(), "Both field must be same", Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(getContext(), "Field can't be empty", Toast.LENGTH_LONG).show();
                }
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
}
