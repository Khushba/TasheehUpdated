package com.example.android.tasheeh;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyByText extends AppCompatActivity implements Serializable {

    String ayah;
    String keys;
    EditText ayahtext;
    EditText keywords;
    Button button;
    ArrayList<String> listOfWords;

    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_by_text);


        ayahtext = (EditText) findViewById(R.id.editInput);
        keywords = (EditText) findViewById(R.id.editInput2);
        button = (Button) findViewById(R.id.button1);

        String strings = (String) getIntent().getSerializableExtra("string");
        ayahtext.setText(strings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ayah = ayahtext.getText().toString();
                keys = keywords.getText().toString();

                listOfWords = getWords(keys);
                flag = true;

                // String[] stockArr = new String[listOfWords.size()];
                // stockArr = listOfWords.toArray(stockArr);

                for (String s : listOfWords) {
                    String pattern = "\\b" + s.toLowerCase() + "\\b";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(ayah.toLowerCase());

                    if (m.find()) {
                        //continue to check other keywords in the ayah by user
                    } else {
                        Toast.makeText(VerifyByText.this, "Aayh doesn't contain word " + s, Toast.LENGTH_SHORT).show();
                        flag = false;
                        break;
                    }
                }
                if (listOfWords.size() <= 1){
                    flag = false;
                    Toast.makeText(VerifyByText.this, "keywords should be more than 1", Toast.LENGTH_SHORT).show();
                }
                if (flag) {

                    Intent m = new Intent(VerifyByText.this, FetchingData.class);
                    m.putExtra("key", listOfWords);
                    startActivity(m);
                    finish();

                }
            }
        });
    }

    public static ArrayList<String> getWords(String text) {
        ArrayList<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }

        return words;
    }
}
