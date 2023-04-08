package com.example.firebase_crud_operation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Adapter extends FirebaseRecyclerAdapter<model, Adapter.MyViewHolder> {

    public Adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull model model) {
        getRef(position).getKey();

        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());

        holder.edit.setOnClickListener(view -> {
            LayoutInflater linf = LayoutInflater.from(view.getContext());
            final View inflator = linf.inflate(R.layout.updatedialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

            alert.setTitle("Update");
            alert.setMessage("Update Data");
            alert.setView(inflator);

            final EditText et1 = (EditText) inflator.findViewById(R.id.etu_name);
            final EditText et2 = (EditText) inflator.findViewById(R.id.etu_phone);

            alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    String s1=et1.getText().toString();
                    String s2=et2.getText().toString();

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", s1);
                    map.put("phone", s2 );

                    FirebaseDatabase.getInstance().getReference().child("Student")
                            .child(Objects.requireNonNull(getRef(position).getKey()))
                            .updateChildren(map)
                            .addOnCompleteListener(task -> {
                                Toast.makeText(view.getContext(), "Detail Updated", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                            });
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });

            alert.show();

        });
        holder.delete.setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference().child("Student")
                    .child(Objects.requireNonNull(getRef(position).getKey()))
                    .removeValue()
                    .addOnCompleteListener(task -> {
                        Toast.makeText(view.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    });


        });
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent , false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
       ImageView edit,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            phone = itemView.findViewById(R.id.tv_phone);
            edit=itemView.findViewById(R.id.iv_edit);
            delete=itemView.findViewById(R.id.iv_delete);

        }

    }
}