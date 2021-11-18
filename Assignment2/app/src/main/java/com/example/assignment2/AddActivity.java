package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Button addLocationButton;
    EditText address;
    EditText longitude;
    EditText latitude;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addLocationButton = (Button) findViewById(R.id.addLocationButton);
        address = (EditText) findViewById(R.id.address);
        longitude = (EditText) findViewById(R.id.longitude);
        latitude = (EditText) findViewById(R.id.latitude);

        db = new DatabaseHelper(this);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressEntered = address.getText().toString();
                String longitudeEntered = longitude.getText().toString();
                String latitudeEntered = latitude.getText().toString();

                if (addressEntered.equals("") || longitudeEntered.equals("") || latitudeEntered.equals("")) {
                    if(addressEntered.equals("")) {
                        Toast.makeText(AddActivity.this, "Please enter an address! ", Toast.LENGTH_SHORT).show();
                    }else if(longitudeEntered.equals("")){
                        Toast.makeText(AddActivity.this, "Please enter a longitude! ", Toast.LENGTH_SHORT).show();
                    }else if(latitudeEntered.equals("")){
                        Toast.makeText(AddActivity.this, "Please enter a latitude! ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    boolean insertData = db.insertData(addressEntered, longitudeEntered, latitudeEntered);
                    if(insertData == true){
                        Toast.makeText(AddActivity.this, "Location added successfully! ", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(AddActivity.this, MainActivity.class);
                        AddActivity.this.startActivity(in);
                    }else{
                        Toast.makeText(AddActivity.this, "Failed to add location! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(AddActivity.this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}