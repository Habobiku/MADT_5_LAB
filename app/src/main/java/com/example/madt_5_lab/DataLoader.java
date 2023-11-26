package com.example.madt_5_lab;

import android.os.AsyncTask;

import com.example.madt_5_lab.models.Currency;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class DataLoader extends AsyncTask<String, Void, String> {
    private Parser parser;
    private MainActivity mainActivity;

    public DataLoader(MainActivity mainActivity, Parser parser) {
        this.mainActivity = mainActivity;
        this.parser = parser;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Map<String, Currency> currencies = null;
        try {
            currencies = parser.parse(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.updateUI(currencies);
    }
}
