package com.taweewong.moneyflow.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taweewong.moneyflow.R;

public class EditTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        getSupportActionBar().setElevation(0);
        setTitle("Edit Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
