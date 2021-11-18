package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    Button editLocationButton, deleteButton;
    EditText editAddress;
    EditText editLongitude;
    EditText editLatitude;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editAddress = (EditText) findViewById(R.id.editAddress);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editLatitude = (EditText) findViewById(R.id.editLatitude);

        editLocationButton = (Button) findViewById(R.id.editLocationButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);


        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String getAddress = (String) bundle.get("address");
        String getLongitude = (String) bundle.get("longitude");
        String getLatitude = (String) bundle.get("latitude");
        int position = (Integer) bundle.get("position");

        editAddress.setText(getAddress);
        editLongitude.setText(getLongitude);
        editLatitude.setText(getLatitude);

        editLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressEntered = editAddress.getText().toString();
                String longitudeEntered = editLongitude.getText().toString();
                String latitudeEntered = editLatitude.getText().toString();

                boolean updateData = db.updateData(position + 1, addressEntered, longitudeEntered, latitudeEntered);
                if (updateData == true) {
                    Toast.makeText(EditActivity.this, "Location updated successfully! ", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(EditActivity.this, MainActivity.class);
                    EditActivity.this.startActivity(in);
                } else {
                    Toast.makeText(EditActivity.this, "Failed to update location! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressEntered = editAddress.getText().toString();
                String longitudeEntered = editLongitude.getText().toString();
                String latitudeEntered = editLatitude.getText().toString();
                boolean deleteData = db.deleteData(position + 1, addressEntered, longitudeEntered, latitudeEntered);
            if(deleteData == true) {
                Toast.makeText(EditActivity.this, "Location deleted! ", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(EditActivity.this, MainActivity.class);
                EditActivity.this.startActivity(in);
            }else{
                Toast.makeText(EditActivity.this, "Failed to delete location! ", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(EditActivity.this, MainActivity.class));
        return super.onOptionsItemSelected(item);

    }
}