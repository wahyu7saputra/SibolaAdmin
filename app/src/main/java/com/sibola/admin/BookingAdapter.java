package com.sibola.admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aizen on 24 Mei 2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder>{

    private List<Booking> bookingList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textTanggal, textJam, textPenyewa;

        public MyViewHolder(View view){
            super(view);
            textTanggal = (TextView) view.findViewById(R.id.text_tanggal);
            textJam = (TextView) view.findViewById(R.id.text_jam);
            textPenyewa = (TextView) view.findViewById(R.id.text_penyewa);
        }
    }

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_booking, parent, false);

        return new BookingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookingAdapter.MyViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.textTanggal.setText(booking.getTanggal());
        holder.textJam.setText(booking.getSlotJam());
        holder.textPenyewa.setText(booking.getUsername());

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
