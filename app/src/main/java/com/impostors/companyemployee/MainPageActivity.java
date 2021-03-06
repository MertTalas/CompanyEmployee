package com.impostors.companyemployee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class MainPageActivity extends AppCompatActivity {
    Button btnAddEmployee;
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    Button btnLogout;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAddEmployee=findViewById(R.id.btnAddEmployee);
        btnLogout=findViewById(R.id.btnLogout);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("company");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutClicked();
            }
        });

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editTextAlert = getLayoutInflater().inflate(R.layout.alert_add_employee, null);
                AlertDialog.Builder ad = new AlertDialog.Builder(MainPageActivity.this);
                ad.setMessage("??al????an Bilgileri");
                ad.setTitle("Yeni ??al????an ekle");
                final EditText InsertedEmployeeName = editTextAlert.findViewById(R.id.editTextTextEmployeeName);
                final EditText InsertedEmployeePhoneNumber= editTextAlert.findViewById(R.id.editTextTextEmployeePhone);
                final EditText InsertedEmployeeEmail = editTextAlert.findViewById(R.id.editTextTextEmployeeEmail);
                final EditText InsertedEmployeePosition = editTextAlert.findViewById(R.id.editTextTextEmployeePosition);
                ad.setView(editTextAlert);
                ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String EmployeeName = InsertedEmployeeName.getText().toString();
                        final String EmployeePhone = InsertedEmployeePhoneNumber.getText().toString();
                        final String EmployeeEmail = InsertedEmployeeEmail.getText().toString();
                        final String EmployeePosition = InsertedEmployeePosition.getText().toString();

                        if (TextUtils.isEmpty(EmployeePhone) || TextUtils.isEmpty(EmployeeEmail)
                                ||(TextUtils.isEmpty(EmployeePosition)||(TextUtils.isEmpty(EmployeeName)))) {
                            Toast.makeText(MainPageActivity.this, "Gerekli Alanlar?? Doldurunuz", Toast.LENGTH_LONG).show();
                        } else {
                            final Query query = databaseReference.orderByKey().equalTo(firebaseUser.getUid());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    final Employee employee = new Employee(EmployeeName, EmployeePhone,EmployeePosition,EmployeeEmail);
                                    employee.setCompany_id(firebaseUser.getUid());
                                    query.getRef().child(firebaseUser.getUid()).child("employees").push().setValue(employee);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }).setNegativeButton("??ptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                ad.create().show();

            }
        });
        FirebaseRecyclerOptions<Employee> options =
                new FirebaseRecyclerOptions.Builder<Employee>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("company").child(firebaseUser.getUid()).child("employees"), Employee.class)
                        .build();
        adapter= new EmployeeAdapter(options);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void signOutClicked() {
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(MainPageActivity.this, LoginActivity.class);
        startActivity(logout);
    }
}