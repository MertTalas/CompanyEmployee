package com.impostors.companyemployee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EmployeeAdapter extends FirebaseRecyclerAdapter<Employee, EmployeeAdapter.myViewHolder> {
    public EmployeeAdapter(@NonNull FirebaseRecyclerOptions<Employee> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull Employee employee) {
        myViewHolder.employeePosition.setText(employee.getPosition());
        myViewHolder.employeeName.setText(employee.getName());
        myViewHolder.employeePhone.setText(employee.getPhoneNumber());
        myViewHolder.employeeEmail.setText(employee.getEmail());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employee, parent, false);
        return new myViewHolder(view);


    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView employeePhone, employeeEmail, employeeName, employeePosition;
        ImageButton btnEditEmployee,btnDeleteEmployee;
        FirebaseAuth auth;
        FirebaseUser firebaseUser;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEditEmployee = itemView.findViewById(R.id.buttonEdit);
            auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();
            employeeEmail = itemView.findViewById(R.id.employeeEmail);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeePhone = itemView.findViewById(R.id.employeePhone);
            employeePosition = itemView.findViewById(R.id.employeePosition);
            firebaseUser = auth.getCurrentUser();
            btnDeleteEmployee=itemView.findViewById(R.id.buttonDelete);



            btnEditEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(v.getRootView().getContext());
                    View editTextAlert = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.update_alert, null);
                    final EditText updatePhone = editTextAlert.findViewById(R.id.editTextUpdatePhone);
                    final EditText updateEmail = editTextAlert.findViewById(R.id.editTextUpdateEmail);
                    final EditText updatePosition = editTextAlert.findViewById(R.id.editTextUpdatePosition);

                    ad.setMessage("Edit");
                    ad.setTitle("Edit Employee");
                    ad.setView(editTextAlert);

                    ad.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase firebaseDatabase;
                            DatabaseReference databaseReference;
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();

                            final String updatedPhone = updatePhone.getText().toString();
                            final String updatedEmail = updateEmail.getText().toString();
                            final String updatedPosition = updatePosition.getText().toString();
                            Query query = databaseReference.child("company")
                                    .child(firebaseUser.getUid()).child("employees").orderByChild("name").equalTo(getItem(getAdapterPosition()).getName());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                        Map<String, Object> updateInfo = new HashMap<>();
                                        if (!TextUtils.isEmpty(updatedEmail)) {
                                            updateInfo.put("email", updatedEmail);

                                        } else if (!TextUtils.isEmpty(updatedPhone)) {
                                            updateInfo.put("phoneNumber", updatedPhone);

                                        } else if (!TextUtils.isEmpty(updatedPosition)) {
                                            updateInfo.put("position", updatedPosition);

                                        }
                                        appleSnapshot.getRef().updateChildren(updateInfo);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });
                    ad.create().show();
                }

            });
            btnDeleteEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRef(getAdapterPosition()).removeValue();
                }
            });
        }
    }
}
