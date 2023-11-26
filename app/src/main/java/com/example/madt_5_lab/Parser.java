package com.example.madt_5_lab;

import com.example.madt_5_lab.models.Currency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Parser {
    public Map<String, Currency> parse(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Map<String, Currency> currencies = new HashMap<>();

        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject currencyObject = jsonObject.getJSONObject(key);
            String code = currencyObject.getString("code");
            double rate = currencyObject.getDouble("rate");

            Currency currency = new Currency(code, rate);
            currencies.put(code, currency);
        }

        return currencies;
    }
}
