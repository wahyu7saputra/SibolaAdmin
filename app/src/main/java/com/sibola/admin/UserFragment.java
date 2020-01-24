package com.sibola.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    private RecyclerView rView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<User, UserViewHolder> mAdapter;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(User.class, R.layout.row_user,
                UserViewHolder.class, mDatabase) {

            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
                viewHolder.setTextEmail(model.getEmail());
                viewHolder.setTextUsername(model.getUsername());
            }

        };

        mAdapter.notifyDataSetChanged();

        rView = (RecyclerView) view.findViewById(R.id.rv_user);
        rView.setHasFixedSize(false);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        rView.setItemAnimator(new DefaultItemAnimator());
        // add line decoration
        rView.addItemDecoration(new DividerItemDecorator(getContext()));
        rView.setAdapter(mAdapter);

        rView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                final User user = mAdapter.getItem(position);
                /*Toast.makeText(getContext(), user.getUsername(), Toast.LENGTH_SHORT).show();*/

                final CharSequence[] items = {"Lihat/Ubah", "Hapus"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(user.getUsername());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            //Toast.makeText(getContext(), "sasasas", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), ViewUserActivity.class);
                            intent.putExtra("userId", user.getUserId());
                            intent.putExtra("username", user.getUsername());
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
                //return true;
            }
        }));

        return view;
    }

}
