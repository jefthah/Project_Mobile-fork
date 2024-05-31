package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContactNumber);
        edpincode = findViewById(R.id.editTextBMBBpincode);
        btnBooking = findViewById(R.id.buttonBMBBBooking);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");

        btnBooking.setOnClickListener(v -> {
            SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedpreferences.getString("username", "");
            String encodedUsername = Utils.encodeUsername(username);

            if (validateInput()) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ordersRef = database.getReference("orders").child(encodedUsername).push();
                DatabaseReference cartRef = database.getReference("carts").child(encodedUsername);

                OrderDetail orderDetail = new OrderDetail(
                        edname.getText().toString(),
                        edaddress.getText().toString(),
                        edcontact.getText().toString(),
                        edpincode.getText().toString(),
                        date,
                        "",
                        price
                );

                ordersRef.setValue(orderDetail)
                        .addOnSuccessListener(aVoid -> {
                            cartRef.removeValue()
                                    .addOnSuccessListener(aVoid1 -> {
                                        Toast.makeText(getApplicationContext(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Gagal menghapus keranjang: " + e.getMessage(), Toast.LENGTH_LONG).show());
                        })
                        .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Gagal melakukan pemesanan: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } else {
                Toast.makeText(getApplicationContext(), "Mohon isi semua detail", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateInput() {
        return !edname.getText().toString().isEmpty()
                && !edaddress.getText().toString().isEmpty()
                && !edcontact.getText().toString().isEmpty()
                && !edpincode.getText().toString().isEmpty();
    }
}
