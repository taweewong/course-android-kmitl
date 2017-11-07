package com.taweewong.moneyflow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private TransactionService transactionService;
    private RecyclerView transactionRecyclerView;
    private TextView totalBalanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        initializeView();
        initializeService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTransactionInfo();
    }

    private void initializeView() {
        TextView addTransactionText = findViewById(R.id.addTransactionText);
        addTransactionText.setOnClickListener(this);

        totalBalanceText = findViewById(R.id.totalBalanceText);
        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeService() {
        transactionService = new TransactionService(this);
        transactionService.setTransactionCallback(this);
        transactionService.setTransactionSummaryCallback(this);
    }

    private void displayTransactionInfo() {
        transactionService.getAllTransactions();
        transactionService.getTransactionSummary();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddTransactionActivity.class));
    }

    @Override
    public void getTransactionCallback(List<Transaction> transactions) {
        TransactionAdapter transactionAdapter = new TransactionAdapter(
                MainActivity.this, transactions, transactionRecyclerView);
        transactionRecyclerView.setAdapter(transactionAdapter);
    }

    @Override
    public void getTransactionSummaryCallback(double summaryAmount) {
        setTotalBalanceTextDisplay(summaryAmount);
    }

    private void setTotalBalanceTextDisplay(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        totalBalanceText.setText(String.format("฿%s", formatter.format(amount)));

        transactionService.setTransactionIncomeSummaryCallback(summaryAmount -> {
            double percentOfRemainAmount = calculateRemainMoneyPercent(amount, summaryAmount);
            setTotalBalanceTextColorFrom(percentOfRemainAmount);
        });

        transactionService.getTransactionIncomeSummary();
    }

    private double calculateRemainMoneyPercent(double currentAmount, double summaryAmount) {
        return ((currentAmount * 100) / summaryAmount);
    }

    private void setTotalBalanceTextColorFrom(double percent) {
        if (percent >= 50) {
            totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textGreen));
        } else if (percent >= 25) {
            totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textYellow));
        } else {
            totalBalanceText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textRed));
        }
    }
}
