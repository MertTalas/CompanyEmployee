package com.impostors.companyemployee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class EmployeeAdapter extends FirebaseRecyclerAdapter<Employee,EmployeeAdapter.myViewHolder> {
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
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employee,parent,false);
      return new myViewHolder(view);


    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView employeePhone,employeeEmail,employeeName,employeePosition;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeEmail=itemView.findViewById(R.id.employeeEmail);
            employeeName=itemView.findViewById(R.id.employeeName);
            employeePhone=itemView.findViewById(R.id.employeePhone);
            employeePosition=itemView.findViewById(R.id.employeePosition);
        }
    }
}
