package com.sibola.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BookingFragment extends Fragment {

    private RecyclerView rView;
    private DatabaseReference mDatabase;
    private List<Booking> bookingList = new ArrayList<>();
    private BookingAdapter mAdapter;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        mAdapter = new BookingAdapter(this.bookingList);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    collectMyBooking(dataSnapshot);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("DB_ERROR", "The read failed: " + databaseError.getMessage());
            }
        });



        rView = (RecyclerView) view.findViewById(R.id.rv_booking);
        rView.setHasFixedSize(false);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        rView.setItemAnimator(new DefaultItemAnimator());

        // add line decoration
        rView.addItemDecoration(new DividerItemDecorator(getContext()));
        rView.setAdapter(mAdapter);

        return view;
    }

    private void collectMyBooking(DataSnapshot bookingData) throws ParseException{
        //SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyy", Locale.getDefault());

        for (DataSnapshot tanggal : bookingData.getChildren()) {
            for (DataSnapshot jam : tanggal.getChildren()) {
                Booking b = jam.getValue(Booking.class);
                bookingList.add(b);
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}
