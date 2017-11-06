package com.taweewong.moneyflow.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.model.Transaction;
import com.taweewong.moneyflow.service.TransactionService;

import static com.taweewong.moneyflow.model.Transaction.TransactionType.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    ToggleButton addIncomeTypeButton;
    ToggleButton addExpenseTypeButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        getSupportActionBar().setElevation(0);
        setTitle("Add Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addIncomeTypeButton = findViewById(R.id.addIncomeTypeButton);
        addExpenseTypeButton = findViewById(R.id.addExpenseTypeButton);
        addButton = findViewById(R.id.addButton);

        addIncomeTypeButton.setOnClickListener(this);
        addExpenseTypeButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addIncomeTypeButton:
                toggleTwoButton(addIncomeTypeButton, addExpenseTypeButton);
                break;
            case R.id.addExpenseTypeButton:
                toggleTwoButton(addExpenseTypeButton, addIncomeTypeButton);
                break;
            case R.id.addButton:
                insertTransactionToDatabase();
                finish();
        }
    }

    private void insertTransactionToDatabase() {
        TransactionService transactionService = new TransactionService(this);
        EditText addAmountEditText = findViewById(R.id.addAmountEditText);
        EditText addNoteEditText = findViewById(R.id.addNoteEditText);

        Float amount = Float.valueOf(addAmountEditText.getText().toString());
        String note = addNoteEditText.getText().toString();
        String dateString = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
        String type = getTransactionType();
        Transaction newTransaction = new Transaction(amount, note, dateString, type);

        transactionService.insertTransaction(newTransaction);
    }

    private void toggleTwoButton(ToggleButton toCheckedButton, ToggleButton toUncheckedButton) {
        toCheckedButton.setEnabled(false);
        toCheckedButton.setChecked(true);
        toUncheckedButton.setEnabled(true);
        toUncheckedButton.setChecked(false);
    }

    private String getTransactionType() {
        if (addIncomeTypeButton.isChecked()) {
            return INCOME.name();
        } else {
            return  EXPENSE.name();
        }
    }
}
