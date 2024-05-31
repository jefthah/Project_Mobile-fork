package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
>>>>>>> upstream/main
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
=======
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edname,edaddress,edcontact,edpincode;
>>>>>>> upstream/main
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
=======
        EdgeToEdge.enable(this);
>>>>>>> upstream/main
        setContentView(R.layout.activity_buy_medicine_book);

        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContactNumber);
<<<<<<< HEAD
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
=======
        edpincode = findViewById(R.id.editTextBMBpincode);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        Intent intent=getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");
        // String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                db.addOrder(username,edname.getText().toString(),edaddress.getText().toString(),edcontact.getText().toString(),Integer.parseInt(edpincode.getText().toString()),date.toString(),"",Float.parseFloat(price[1].toString()),"medicine");
                db.removeCart(username,"medicine");
                Toast.makeText(getApplicationContext(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BuyMedicineBookActivity.this,HomeActivity.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
>>>>>>> upstream/main
