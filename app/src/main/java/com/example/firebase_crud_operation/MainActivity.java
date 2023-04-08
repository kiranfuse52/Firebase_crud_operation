package com.example.firebase_crud_operation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name,phone;
    Button submit,list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.etName);
        phone=findViewById(R.id.etPhone);
        submit=findViewById(R.id.btEnter);
        list=findViewById(R.id.btlist);


        submit.setOnClickListener(view -> {
            String uname=name.getText().toString();
            String uphone=phone.getText().toString();
            model model=new model(uname,uphone);

            FirebaseDatabase.getInstance().getReference().child("Student").push()
                    .setValue(model)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            name.setText("");
                            phone.setText("");
                            Toast.makeText(MainActivity.this, "Data Inseted Successfulley...", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Insertion Faild...", Toast.LENGTH_SHORT).show();
                        }
                    });

        });
        list.setOnClickListener(view -> {
            startActivity(new Intent(this,AllDetailsActivity.class));
        });
    }
}