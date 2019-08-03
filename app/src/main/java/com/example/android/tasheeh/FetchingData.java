package com.example.android.tasheeh;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchingData extends AppCompatActivity {

    String key = "";
    String data ="";
    boolean flags= true;
    final ArrayList<Ayah> words = new ArrayList<Ayah>();
    ArrayList<Ayah> ayahs = new ArrayList<Ayah>();
    ArrayList<Ayah> matched = new ArrayList<Ayah>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_data);

        new FetchData().execute();
    }

    public class FetchData extends AsyncTask<ArrayList<String>, Void, ArrayList<Ayah>> implements Serializable {

        ArrayList<String> wordslist = (ArrayList<String>) getIntent().getSerializableExtra("key");
        public int counter = 0;

        @Override
        protected ArrayList<Ayah> doInBackground(ArrayList<String>... params) {

            while ((ayahs.size() <1 ) && (counter < wordslist.size())) {
                ayahs.clear();
                try {
                    String OneWord = wordslist.get(counter++);
                    key = "http://api.alquran.cloud/v1/search/" + OneWord + "/all/en";
                    URL url = new URL(key);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    data = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        data = data + line;
                    }
                    ayahs = jsonParse(data);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        public ArrayList<Ayah> jsonParse(String data) throws JSONException {
            JSONObject JO1 = new JSONObject(data);
            JSONObject JO2 = JO1.getJSONObject("data");
            int counts = JO2.getInt("count");
            JSONArray JA = JO2.getJSONArray("matches");

            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(i);
                JSONObject JOT = JO.getJSONObject("surah");

                String surahName = (String) JOT.get("englishName");
                int ayahNo = (int) JO.get("numberInSurah");
                String ayahText = (String) JO.get("text");

                words.add(new Ayah(surahName, ayahText, ayahNo));

            }
            return  words;
        }

        @Override
        protected void onPostExecute(ArrayList w) {
            super.onPostExecute(w);
            if (ayahs.size()== 0)
                Toast.makeText(FetchingData.this, "No result found", Toast.LENGTH_SHORT).show();

            else
                Toast.makeText(FetchingData.this, "Data is found for " + wordslist.get(counter-1), Toast.LENGTH_LONG).show();

            for (Ayah str : ayahs) {
                for (String s1 : wordslist) {
                    String pattern2 = "\\b" + s1.toLowerCase() + "\\b";
                    Pattern p1 = Pattern.compile(pattern2);
                    Matcher m1 = p1.matcher(str.getAyahText().toLowerCase());
                    //break the loop if any keyword doesnt exist in the current ayah.
                    if (m1.find()) {
                        //continue to check other keywords in the arraylist of ayah
                        flags = true;
                    } else { flags = false; break;}

                }
                if (flags) {
                    matched.add(str);
                }
            }
            if (matched.size() != 0){

                Bundle bundle = new Bundle();
                bundle.putSerializable("product", matched);
                Intent cartIntent = new Intent(FetchingData.this, list_view.class);
                cartIntent.putExtras(bundle);
                startActivity(cartIntent);
                finish();
            }
            else {Toast.makeText(FetchingData.this, "NO MATCH FOUND", Toast.LENGTH_SHORT).show();
            finish();}


        }
    }
}
