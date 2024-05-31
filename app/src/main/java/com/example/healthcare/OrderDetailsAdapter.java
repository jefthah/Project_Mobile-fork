package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder> {

    private ArrayList<OrderDetail> orderDetailsList;

    public OrderDetailsAdapter(ArrayList<OrderDetail> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_detail, parent, false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailsList.get(position);
        holder.textViewOrderName.setText(orderDetail.getName());
        holder.textViewOrderAddress.setText(orderDetail.getAddress());
        holder.textViewOrderContact.setText(orderDetail.getContact());
        holder.textViewOrderDate.setText(orderDetail.getDate());
        holder.textViewOrderPincode.setText(orderDetail.getPincode());
        holder.textViewOrderPrice.setText(orderDetail.getPrice());
        holder.textViewOrderTime.setText(orderDetail.getTime());
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    static class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderName, textViewOrderAddress, textViewOrderContact, textViewOrderDate, textViewOrderPincode, textViewOrderPrice, textViewOrderTime;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderName = itemView.findViewById(R.id.textViewOrderName);
            textViewOrderAddress = itemView.findViewById(R.id.textViewOrderAddress);
            textViewOrderContact = itemView.findViewById(R.id.textViewOrderContact);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewOrderPincode = itemView.findViewById(R.id.textViewOrderPincode);
            textViewOrderPrice = itemView.findViewById(R.id.textViewOrderPrice);
            textViewOrderTime = itemView.findViewById(R.id.textViewOrderTime);
        }
    }
}
