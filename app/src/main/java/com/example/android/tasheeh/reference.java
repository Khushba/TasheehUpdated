package com.example.android.tasheeh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class reference extends AppCompatActivity {


    Button click3;
    public  static TextView dataOfRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        click3 = findViewById(R.id.buttonRef);
        dataOfRef = findViewById(R.id.textRef);

        click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numbeR = findViewById(R.id.ayahNumber3);
                String ayahNumber3 = numbeR.getText().toString();


                Spinner numbeR2 = findViewById(R.id.spinner_go3);
                String surahNumber3 = numbeR2.getSelectedItem().toString();


                fetchData process = new fetchData();
                process.execute(ayahNumber3, surahNumber3);

            }

        });}
    }