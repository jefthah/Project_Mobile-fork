package com.example.healthcare;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
=======
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
>>>>>>> upstream/main
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

=======
>>>>>>> upstream/main
public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
<<<<<<< HEAD
    Button btnBack, btnAddToCart;
=======
    Button btnBack,btnAddToCart;
>>>>>>> upstream/main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details);

        tvPackageName = findViewById(R.id.textViewBMCartTitle);
        edDetails = findViewById(R.id.editTextBMDTextMultiLine);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        btnBack = findViewById(R.id.buttonBMDBack);
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
<<<<<<< HEAD
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + "/-");

        btnBack.setOnClickListener(v -> startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class)));

        btnAddToCart.setOnClickListener(v -> {
            SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedpreferences.getString("username", "");
            String encodedUsername = Utils.encodeUsername(username);  // Encode the username

            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(intent.getStringExtra("text3"));

            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts").child(encodedUsername).push();
            CartItem cartItem = new CartItem(product, String.valueOf(price));

            cartRef.setValue(cartItem)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Product Added to Cart", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to add product to cart: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });
    }
}
=======
        tvTotalCost.setText("Total Cost : "+intent.getStringExtra("text3")+"/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineDetailsActivity.this,BuyMedicineActivity.class));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

                Database db  = new Database(getApplicationContext(),"healthcare",null,1);
                if(db.checkCart(username,product)==1){
                    Toast.makeText(getApplicationContext(),"Product Already Added",Toast.LENGTH_LONG).show();
                }else {
                    db.addCart(username,product,price,"medicine");
                    Toast.makeText(getApplicationContext(),"Record Inserted To Cart",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BuyMedicineDetailsActivity.this,BuyMedicineActivity.class));
                }
            }
        });
    }
}
>>>>>>> upstream/main
