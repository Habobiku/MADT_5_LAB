package com.example.madt_5_lab

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.madt_5_lab.models.Currency
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var currencies: Map<String, Currency>
    private lateinit var filteredCurrencies: Map<String, Currency>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupEditText()
        loadData()
    }

    private fun initializeViews() {
        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
    }

    private fun setupEditText() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filterCurrencies(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadData() {
        val parser = Parser()
        val dataLoader = DataLoader(this, parser)
        dataLoader.execute("https://www.floatrates.com/daily/usd.json")
    }

    public fun updateUI(currencies: Map<String, Currency>) {
        this.currencies = currencies
        this.filteredCurrencies = currencies
        displayCurrencies(filteredCurrencies)
    }

    private fun displayCurrencies(currencies: Map<String, Currency>) {
        val listItems = currencies.map { "${it.key} - ${it.value.rate}" }.toTypedArray()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    private fun filterCurrencies(query: String) {
        filteredCurrencies = currencies.filterKeys { it.contains(query, ignoreCase = true) }
        displayCurrencies(filteredCurrencies)
    }
}