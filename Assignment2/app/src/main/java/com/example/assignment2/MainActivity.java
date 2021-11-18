package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;
    DatabaseHelper db;
    ArrayAdapter arrayAdapter;
    ArrayList<String> longitudeList, latitudeList, addressList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        db = new DatabaseHelper(this);

        longitudeList = new ArrayList<>();
        latitudeList = new ArrayList<>();
        addressList = new ArrayList<>();

        list = (ListView) findViewById(R.id.list);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivity(in);
            }
        });

        getData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(MainActivity.this, EditActivity.class);
                in.putExtra("address", addressList.get(position));
                in.putExtra("longitude", longitudeList.get(position));
                in.putExtra("latitude", latitudeList.get(position));
                in.putExtra("position", position);
                MainActivity.this.startActivity(in);
            }
        });
    }

    private void getData(){
        Cursor cursor = db.getData();
        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No addresses! ", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                addressList.add(cursor.getString(1));
                longitudeList.add(cursor.getString(2));
                latitudeList.add(cursor.getString(3));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, addressList);
            list.setAdapter(arrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Type here to search..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
}