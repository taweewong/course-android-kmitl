package com.taweewong.moneyflow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.adapter.TransactionAdapter;
import com.taweewong.moneyflow.entity.Transaction;
import com.taweewong.moneyflow.service.TransactionService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        TransactionService.OnGetTransactionCallback {
    TransactionService transactionService;
    RecyclerView transactionRecyclerView;
    TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        TextView addTransactionText = findViewById(R.id.addTransactionText);
        addTransactionText.setOnClickListener(this);

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionService = new TransactionService(this);
        transactionService.setCallback(this);
        transactionService.getAllTransactions();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddTransactionActivity.class));
    }

    @Override
    public void getTransactionCallback(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            Log.d("transaction_test", transaction.getId() + " : " + transaction.getAmount());
        }
        transactionAdapter = new TransactionAdapter(MainActivity.this, transactions);
        transactionRecyclerView.setAdapter(transactionAdapter);
    }
}
