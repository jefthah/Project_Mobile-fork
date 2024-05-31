package com.example.healthcare;

<<<<<<< HEAD
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
=======
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
>>>>>>> upstream/main
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
<<<<<<< HEAD
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

=======
import android.widget.TimePicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

>>>>>>> upstream/main
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
<<<<<<< HEAD
    ArrayList<HashMap<String, String>> list;
=======
    HashMap<String,String> item;
    ArrayList list;
>>>>>>> upstream/main
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
<<<<<<< HEAD
    private Button dateButton, timeButton, btnCheckout, btnBack, btnDeleteCart;
    private FirebaseAuth mAuth;
    private static final String TAG = "CartLabActivity";
=======
    private Button dateButton, timeButton, btnCheckout, btnBack;
    private String[][] packages = {};
>>>>>>> upstream/main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

<<<<<<< HEAD
        // Initialize UI components
=======
>>>>>>> upstream/main
        dateButton = findViewById(R.id.buttonBMCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMCartBack);
<<<<<<< HEAD
        btnDeleteCart = findViewById(R.id.buttonDeleteCart);
        tvTotal = findViewById(R.id.textViewBMCartTotalCost);
        lst = findViewById(R.id.listViewDD);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not logged in, redirect to login page
            startActivity(new Intent(CartLabActivity.this, LoginActivity.class));
            finish(); // Close this activity so user can't go back
            return;
        }

        // Get the logged-in user's ID
        String userId = currentUser.getUid();
        Log.d(TAG, "Current user ID: " + userId);

        // Get reference to the logged-in user's cart
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("carts").child(userId);

        // Listen for changes in the cart data
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve cart data from dataSnapshot and display it in your app
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem item = snapshot.getValue(CartItem.class);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("line1", item.getProduct());
                    map.put("line5", item.getPrice());
                    list.add(map);
                }
                displayCartData(); // Display cart data after getting it
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if occurred
                Log.e(TAG, "Error fetching cart data: " + databaseError.getMessage());
            }
        });

        // Set listener for back button
=======
        tvTotal = findViewById(R.id.textViewBMCartTotalCost);
        lst = findViewById(R.id.listViewBMCart);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username","").toString();

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);

        float totalAmount = 0;
        ArrayList<String> dbData = db.getCartData(username,"lab");
        /*if (!dbData.isEmpty()) {
            Toast.makeText(getApplicationContext(), "" + dbData.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
        }*/

        packages =  new String[dbData.size()][];
        for(int i=0;i<packages.length;i++){
            packages[i] = new String[5];
        }

        for(int i=0;i<dbData.size();i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost : "+strData[1]+"/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost : "+totalAmount);

        list = new ArrayList();
        for(int i=0;i<packages.length;i++){
            item = new HashMap<String,String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            list.add( item );
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{ "line1","line2","line3","line4","line5" },
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
>>>>>>> upstream/main
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this, LabTestActivity.class));
            }
        });

<<<<<<< HEAD
        // Call clearCart() method from onClick listener
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && !list.isEmpty()) { // Check if the cart is not empty
                    Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                    it.putExtra("price", tvTotal.getText());
                    it.putExtra("date", dateButton.getText());
                    it.putExtra("time", timeButton.getText());
                    startActivity(it);

                    // Move cart items to order details and clear the cart
                    String userId = mAuth.getCurrentUser().getUid(); // Assuming mAuth is initialized correctly
                    Database db = new Database();
                    db.moveCartToOrder(userId, list, new Database.DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Cart moved to order and cleared successfully");
                            list.clear();
                            displayCartData();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "Failed to move cart to order: " + e.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize date picker
=======
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                it.putExtra("price",tvTotal.getText());
                it.putExtra("date",dateButton.getText());
                it.putExtra("time",timeButton.getText());
                startActivity(it);
            }
        });

>>>>>>> upstream/main
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

<<<<<<< HEAD
        // Initialize time picker
=======
>>>>>>> upstream/main
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
<<<<<<< HEAD

        // Set listener for delete cart button
        btnDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = mAuth.getCurrentUser().getUid();
                Database db = new Database();
                db.clearCart(userId, new Database.DatabaseCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(CartLabActivity.this, "Cart deleted successfully", Toast.LENGTH_SHORT).show();
                        // Clear the list and update the UI
                        list.clear();
                        displayCartData();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(CartLabActivity.this, "Failed to delete cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Method to display cart data in ListView
    private void displayCartData() {
        if (list != null && list.size() > 0) {
            sa = new SimpleAdapter(this, list,
                    R.layout.multi_lines,
                    new String[]{"line1", "line5"},
                    new int[]{R.id.line_a, R.id.line_e});
            lst.setAdapter(sa);
            // Calculate total cost
            float totalAmount = 0;
            for (HashMap<String, String> item : list) {
                String costString = item.get("line5").substring("Total Cost : ".length()).replace("/-", "").trim();
                totalAmount += Float.parseFloat(costString);
            }
            tvTotal.setText("Total Cost : " + totalAmount + "/-");
        } else {
            Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to initialize date picker
    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateButton.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
    }

    // Method to initialize time picker
    private void initTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, hour, minute, true);
=======
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                dateButton.setText(i2 + "/" + i1 + "/" + i);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeButton.setText(i + ":" + i1);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
>>>>>>> upstream/main
    }
}
