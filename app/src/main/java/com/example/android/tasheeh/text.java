package com.example.android.tasheeh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class text extends AppCompatActivity {

    EditText editTextInput;
    String strInput;
    Button btnInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        btnInput = (Button) findViewById(R.id.button1);
        btnInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                editTextInput = (EditText) findViewById(R.id.editInput);
                strInput = editTextInput.getText().toString();
                if(strInput.equals("ABC")||strInput.equals("abc"))
//                    Toast.makeText(getApplicationContext(), "verified", Toast.LENGTH_LONG).show();
                {Intent i = new Intent(text.this, authentic.class);
                startActivity(i);}
                else
//                    Toast.makeText(getApplicationContext(), "inauthentic", Toast.LENGTH_LONG).show();
                {Intent i = new Intent(text.this, inauthentic.class);
                    startActivity(i);}
            }
        });
    }
}
