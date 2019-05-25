package com.example.hp.foodhub;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import static android.content.Context.MODE_PRIVATE;

public class FragMyBookings extends Fragment {
    RecyclerView recyclerView;
    BookingOpenHelper bookingOpenHelper;
    SharedPreferences sp;
    BookingAdapter adapter;
    GridLayoutManager gridLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mybookings,container,false );
        bookingOpenHelper = new BookingOpenHelper(view.getContext());
        recyclerView = view.findViewById(R.id.recyclerview_book);

        gridLayoutManager = new GridLayoutManager(this.getContext(),1,GridLayoutManager.VERTICAL,false);
        adapter = new BookingAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        inflateAdapter();
        return view;

    }

    public void inflateAdapter(){
        sp = this.getActivity().getSharedPreferences("CurrentUser",MODE_PRIVATE );
        Cursor cursor = bookingOpenHelper.getData();
        while(cursor.moveToNext()) {
            if ((cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.username)).trim())
                    .equals(sp.getString("CurUser", "not found"))) {
                String[] obj = new String[8];
                obj[0]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.bookingID)));
                obj[1]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.rest)));
                obj[2]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.city)));
                obj[3]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.seldate)));
                obj[4]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.selTime)));
                obj[5]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.tabpos)));
                obj[6]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.tabType)));
                obj[7]=(cursor.getString(cursor.getColumnIndexOrThrow(bookingOpenHelper.amount)));
                adapter.addItem(obj);

            }
        }
    }
}
