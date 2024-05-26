package com.example.healthcare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSave, btnDelete; // Menambahkan tombol btnDelete
    private Database db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete); // Menginisialisasi tombol btnDelete
        TextView tvUsername = findViewById(R.id.tvUsername);

        db = new Database(this, "healthcare", null, 1);

        // Assume the username is passed from the previous activity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Set the username text
        tvUsername.setText("Username: " + username);

        // Load existing data
        loadUserData();

        btnSave.setOnClickListener(v -> {
            String newEmail = etEmail.getText().toString();
            String newPassword = etPassword.getText().toString();

            if (newEmail.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(EditProfileActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                db.updateProfile(username, newEmail, newPassword);
                Toast.makeText(EditProfileActivity.this, "Profile updated successfully. Please log in again.", Toast.LENGTH_SHORT).show();
                // Redirect to LoginActivity after updating the profile
                Intent loginIntent = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Close the activity
            }
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
            builder.setTitle("Delete Account");
            builder.setMessage("Are you sure you want to delete your account?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                db.deleteAccount(username);
                Toast.makeText(EditProfileActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                // Redirect to login page or any other appropriate action
                Intent loginIntent = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private void loadUserData() {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            etEmail.setText(email);
            etPassword.setText(password);
        } else {
            // Jika tidak ada data yang sesuai dengan username
            Toast.makeText(this, "No data found for username: " + username, Toast.LENGTH_SHORT).show();
        }
        cursor.close(); // Pastikan untuk menutup kursor setelah selesai menggunakan
    }
}
