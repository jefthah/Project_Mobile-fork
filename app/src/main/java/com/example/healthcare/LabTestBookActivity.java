package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestBookActivity extends AppCompatActivity {

<<<<<<< HEAD
    EditText edname, edaddress, edcontact, edpincode;
=======
    EditText edname,edaddress,edcontact,edpincode;
>>>>>>> upstream/main
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

<<<<<<< HEAD
        edname = findViewById(R.id.editTextFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContactNumber);
        edpincode = findViewById(R.id.editTextpincode);
        btnBooking = findViewById(R.id.buttonBooking);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").split(java.util.regex.Pattern.quote(":"));
=======
        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContactNumber);
        edpincode = findViewById(R.id.editTextBMBpincode);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        Intent intent=getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
>>>>>>> upstream/main
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");

                Database db = new Database();
                OrderDetail order = new OrderDetail(
                        edname.getText().toString(),
                        edaddress.getText().toString(),
                        edcontact.getText().toString(),
                        edpincode.getText().toString(),
                        date,
                        time,
                        price[1]
                );

                db.addOrder(username, order, new Database.DatabaseCallback() {
                    @Override
                    public void onSuccess() {
                        db.clearCart(username, new Database.DatabaseCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                                finish(); // Finish current activity after successful checkout
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getApplicationContext(), "Gagal menghapus keranjang", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Pemesanan gagal", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
=======
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                db.addOrder(username,edname.getText().toString(),edaddress.getText().toString(),edcontact.getText().toString(),Integer.parseInt(edpincode.getText().toString()),date.toString(),time.toString(),Float.parseFloat(price[1].toString()),"lab");
                db.removeCart(username,"lab");
                Toast.makeText(getApplicationContext(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LabTestBookActivity.this,HomeActivity.class));
            }
        });
    }
}
>>>>>>> upstream/main
