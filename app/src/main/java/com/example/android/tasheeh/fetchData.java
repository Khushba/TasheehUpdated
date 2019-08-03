package com.example.android.tasheeh;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class fetchData extends AsyncTask<String, String, Void> {

    String dataOfRef ="";
    String data2="";
    String dataParsed = "";
    String singleParsed ="";

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String ayahNo = strings[0];
            String surahNo = strings[1];
            URL url = new URL("http://api.alquran.cloud/v1/ayah/"+surahNo+":"+ayahNo+"/en.asad");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                dataOfRef = dataOfRef + line;

                JSONObject JO1 = new JSONObject(dataOfRef);
                JSONObject JO2 = JO1.getJSONObject("data");
                JSONObject JO3 = JO2.getJSONObject("surah");
                int quranAyahNumber = (int) JO2.get("number");

                URL secondUrl = new URL("http://api.alquran.cloud/v1/ayah/"+quranAyahNumber);
                HttpURLConnection secondHttpURLConnection = (HttpURLConnection) secondUrl.openConnection();
                InputStream secondInputStream = secondHttpURLConnection.getInputStream();
                BufferedReader secondBufferedReader = new BufferedReader(new InputStreamReader(secondInputStream));

                String secondLine;
                secondLine = "";
                while (secondLine != null) {
                    secondLine = secondBufferedReader.readLine();
                    data2 = data2 + secondLine;

                    JSONObject SJO1 = new JSONObject(data2);
                    JSONObject SJO2 = SJO1.getJSONObject("data");

                    singleParsed = "Surah : " + JO3.get("name")  + "\n\n" + "Ayah Number : " + JO2.get("numberInSurah") + "\n\n" +
                            "Ayah : " + SJO2.get("text")  + "\n\n" + "Translation : " + JO2.get("text") + "\n\n\n\n";

                    dataParsed = dataParsed + singleParsed;
                    secondLine=null;
                    line=null;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        reference.dataOfRef.setText(this.dataParsed);
        Log.d("In Sha Allah", "onPostExecute: "+dataOfRef);
    }

}