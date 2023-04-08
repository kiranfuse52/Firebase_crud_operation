package com.example.firebase_crud_operation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AllDetailsActivity extends AppCompatActivity {
RecyclerView recyclerView;

Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details);
        recyclerView=findViewById(R.id.rc_view);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        FirebaseRecyclerOptions<model>context=new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Student"), model.class)
                .build();

        adapter=new Adapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
        recyclerView.removeAllViews();
        adapter.startListening();

    }
}