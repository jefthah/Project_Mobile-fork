package com.example.healthcare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText ed1,ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
<<<<<<< HEAD
    private Button dateButton, timeButton, btnBook, btnBack;

=======
    private Button dateButton, timeButton, btnBook,btnBack;
>>>>>>> upstream/main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);

        // Inisialisasi TextView dan EditText
        tv = findViewById(R.id.textViewBMBPackageName);
        ed1 = findViewById(R.id.editTextBMBFullName);
        ed2 = findViewById(R.id.editTextBMBAddress);
        ed3 = findViewById(R.id.editTextBMBpincode);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonCartTime);
        timeButton = findViewById(R.id.buttonAppTime);
<<<<<<< HEAD
        btnBook= findViewById(R.id.buttonBookAppointment);
=======
        btnBook = findViewById(R.id.buttonBookAppointment);
>>>>>>> upstream/main
        btnBack = findViewById(R.id.buttonAppBack);

        // Mengatur EditText agar tidak bisa diedit
        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText(fees);

        initDatePicker();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
<<<<<<< HEAD
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class));
=======
            public void onClick(View v) {
                startActivity(new Intent(BookAppointmentActivity.this,FindDoctorActivity.class));
>>>>>>> upstream/main
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
<<<<<<< HEAD
            public void onClick(View view) {

=======
            public void onClick(View v) {
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();

                if(db.checkAppointmentExists(username,title+" => "+fullname,address,contact,dateButton.getText().toString(),timeButton.getText().toString())==1) {
                    Toast.makeText(getApplicationContext(),"Appointment already booked",Toast.LENGTH_LONG).show();
                }else{
                    float feeAmount = Float.parseFloat(fees.replaceAll("[^\\d.]", ""));
                    db.addOrder(username,title+" => "+fullname,address,contact,0,dateButton.getText().toString(),timeButton.getText().toString(),feeAmount, "appointment");
                    Toast.makeText(getApplicationContext(), "Your appointment is done successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                }
>>>>>>> upstream/main
            }
        });
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                dateButton.setText(i2+"/"+i1+"/"+i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeButton.setText(i+":"+i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);


        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener,hrs,mins,true);

    }

}