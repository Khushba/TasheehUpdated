package com.example.android.tasheeh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class list_view extends AppCompatActivity implements Serializable {

    ListView listView;
    ArrayAdapter<Ayah> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Bundle bundle = this.getIntent().getExtras();
        ArrayList<Ayah> product = null;
        if (bundle != null) {
            product = (ArrayList<Ayah>) bundle.getSerializable("product");
        }
        arrayAdapter = new WordAdapter(list_view.this, product);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);
        Toast.makeText(list_view.this,"data size is "+ product.size(), Toast.LENGTH_SHORT).show();

    }
}
