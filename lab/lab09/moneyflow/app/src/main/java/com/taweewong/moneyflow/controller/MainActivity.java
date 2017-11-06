package com.taweewong.moneyflow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.adapter.TransactionAdapter;
import com.taweewong.moneyflow.model.Transaction;
import com.taweewong.moneyflow.service.TransactionService;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        TransactionService.OnGetTransactionCallback, TransactionService.OnGetTransactionSummaryCallback {
    TransactionService transactionService;
    RecyclerView transactionRecyclerView;
    TransactionAdapter transactionAdapter;
    TextView totalBalanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        TextView addTransactionText = findViewById(R.id.addTransactionText);
        addTransactionText.setOnClickListener(this);

        totalBalanceText = findViewById(R.id.totalBalanceText);

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionService = new TransactionService(this);
        transactionService.setTransactionCallback(this);
        transactionService.setTransactionSummaryCallback(this);
        transactionService.getAllTransactions();
        transactionService.getTransactionSummary();
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

    @Override
    public void getTransactionSummaryCallback(Double summaryAmount) {
        setTotalBalanceTextDisplay(summaryAmount);
    }

    private void setTotalBalanceTextDisplay(Double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        totalBalanceText.setText(String.format("฿%s", formatter.format(amount)));

        transactionService.setTransactionIncomeSummaryCallback(summaryAmount -> {
            double percentOfRemainAmount = ((amount * 100) / summaryAmount);

            if (percentOfRemainAmount >= 50) {
                totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textGreen));
            } else if (percentOfRemainAmount >= 25) {
                totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textYellow));
            } else {
                totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textRed));
            }
        });

        transactionService.getTransactionIncomeSummary();
    }

    @Override
    protected void onResume() {
        super.onResume();
        transactionService.getAllTransactions();
        transactionService.getTransactionSummary();
    }
}
