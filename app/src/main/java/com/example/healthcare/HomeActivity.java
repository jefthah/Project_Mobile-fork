package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private TextView usernameView, userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menghubungkan TextView dengan ID yang sesuai di layout
        usernameView = findViewById(R.id.usernameView);
        userNameTextView = findViewById(R.id.userNameTextView);

        // Mengambil username dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");

        // Menampilkan username di TextView
        usernameView.setText("Hello");
        userNameTextView.setText(username);

        // Menampilkan pesan sambutan
        Toast.makeText(getApplicationContext(), "Selamat datang " + username, Toast.LENGTH_SHORT).show();

        // Menambahkan listener untuk CardView Logout
        findViewById(R.id.cardExit).setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        // Menambahkan listener untuk CardView Find Doctor
        findViewById(R.id.cardFindDoctor).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        });

<<<<<<< HEAD
        // Menambahkan listener untuk CardView Edit Profile
        findViewById(R.id.cardEditProfile).setOnClickListener(v -> {
            Intent editIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
            editIntent.putExtra("username", username);
            startActivity(editIntent);
        });

        // Menambahkan listener untuk CardView Lab Test
        findViewById(R.id.cardLabTest).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
        });

        // Menambahkan listener untuk CardView Order Details
        findViewById(R.id.cardOrderDetails).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, OrderDetailsActivity.class));
        });

        // Menambahkan listener untuk CardView Buy Medicine
        CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        buyMedicine.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BuyMedicineActivity.class));
        });

        // Implementasikan listener untuk CardView lainnya secara serupa
=======
        CardView labTest = findViewById(R.id.cardLabTest);
        labTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LabTestActivity.class));
            }
        });

        // Menambahkan kode untuk membuka EditProfileActivity
        CardView editProfile = findViewById(R.id.cardEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
                editIntent.putExtra("username", username);
                startActivity(editIntent);
            }
        });

        CardView orderDetails = findViewById(R.id.cardOrderDetails);
        orderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,OrderDetailsActivity.class));
            }
        });

        CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        buyMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,BuyMedicineActivity.class));
            }
        });

        CardView health = findViewById(R.id.cardHealthDoctor);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,HealthArticlesActivity.class));
            }
        });
>>>>>>> upstream/main
    }
}
