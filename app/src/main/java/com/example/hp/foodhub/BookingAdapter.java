package com.example.hp.foodhub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingUI> {
    private ArrayList<String[]> bookinglist = new ArrayList<>();

    public void addItem(String[] object)
    {
        bookinglist.add(object);

    }

    @NonNull
    @Override
    public BookingUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.mybooking_view,viewGroup,false);
        return new BookingUI(v);
    }



    @Override
    public void onBindViewHolder(@NonNull BookingUI BookingUI, int i) {
        BookingUI.bindData(bookinglist.get(i));
    }

    @Override
    public int getItemCount() {
        return bookinglist.size();
    }

    public static class BookingUI extends RecyclerView.ViewHolder{
        TextView ID,res,cityname,seldate,selTime,tableDesc,tableType,amount;

        public BookingUI(View itemView){
            super(itemView);
            ID = itemView.findViewById(R.id.Id);
            res = itemView.findViewById(R.id.rest);
            cityname = itemView.findViewById(R.id.city);
            seldate = itemView.findViewById(R.id.date);
            selTime = itemView.findViewById(R.id.stime);
            tableDesc = itemView.findViewById(R.id.tabdesc);
            tableType = itemView.findViewById(R.id.type);
            amount = itemView.findViewById(R.id.paid);

        }

        public void bindData(String[] ob){
            ID.setText("Booking ID "+ob[0]);
            res.setText(ob[1]);
            cityname.setText(ob[2]);
            seldate.setText(ob[3]);
            selTime.setText(ob[4]);
            tableDesc.setText(ob[5]);
            tableType.setText(ob[6]);
            amount.setText(ob[7]);
        }

    }
}
