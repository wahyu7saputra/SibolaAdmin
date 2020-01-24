package com.sibola.admin;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class ViewUserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private User mUser;
    private String mUserId;
    private String mUsername;

    protected TextView userIdText;
    protected TextView emailText;
    protected CheckBox member;
    protected EditText depositEditText;
    protected FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        //add to activity you want to pull variables from
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mUserId = extras.getString("userId");
            Log.d("USER_ID", mUserId);
            this.mUsername = extras.getString("username");
        }

        userIdText = (TextView) findViewById(R.id.text_user_id);
        emailText = (TextView) findViewById(R.id.text_email);
        member = (CheckBox) findViewById(R.id.checkBox_member);
        depositEditText = (EditText) findViewById(R.id.editText_deposit);
        fab = (FloatingActionButton) findViewById(R.id.fab_user);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child(mUserId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mUser = dataSnapshot.getValue(User.class);
                Log.d("USER_ID", mUser.getUsername());
                userIdText.setText(mUser.getUserId());
                emailText.setText(mUser.getEmail());
                member.setChecked(mUser.isMember());
                String deposit = String.valueOf(mUser.getDeposit());
                depositEditText.setText(deposit);

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.i("FIREBASE_ERROR", "The read failed: " + databaseError.getMessage());
                // ...
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_person_black_24dp);
        actionBar.setTitle("User : " + mUsername);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long deposit2 = Long.valueOf(depositEditText.getText().toString());
                mDatabase.child(mUserId).child("member").setValue(member.isChecked());
                mDatabase.child(mUserId).child("deposit").setValue(deposit2);

                Toast.makeText(getApplicationContext(), "Update data user berhasil!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
