package com.example.healthcare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOD;
    private OrderDetailsAdapter adapter;
    private ArrayList<OrderDetail> orderDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        recyclerViewOD = findViewById(R.id.recyclerViewOD);
        recyclerViewOD.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        String username = Utils.encodeUsername(sharedPreferences.getString("username", ""));

        orderDetailsList = new ArrayList<>();
        adapter = new OrderDetailsAdapter(orderDetailsList);
        recyclerViewOD.setAdapter(adapter);

        Database db = new Database();
        db.getOrderDetails(username, new Database.OrderDetailsCallback() {
            @Override
            public void onOrderDataReceived(ArrayList<OrderDetail> orderDetails) {
                orderDetailsList.clear();
                orderDetailsList.addAll(orderDetails);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(OrderDetailsActivity.this, "Failed to load order details", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonBMDBack).setOnClickListener(v -> finish());
    }
}
