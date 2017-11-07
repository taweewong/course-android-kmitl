package com.taweewong.moneyflow.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.model.Transaction;
import com.taweewong.moneyflow.service.TransactionService;
import com.taweewong.moneyflow.model.Transaction.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.taweewong.moneyflow.model.Transaction.TransactionType.EXPENSE;
import static com.taweewong.moneyflow.model.Transaction.TransactionType.INCOME;

public class EditTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    ToggleButton editIncomeTypeButton;
    ToggleButton editExpenseTypeButton;
    EditText editAmountEditText;
    EditText editNoteEditText;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        getSupportActionBar().setElevation(0);
        setTitle("Edit Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editIncomeTypeButton = findViewById(R.id.editIncomeTypeButton);
        editExpenseTypeButton = findViewById(R.id.editExpenseTypeButton);
        editAmountEditText = findViewById(R.id.editAmountEditText);
        editNoteEditText = findViewById(R.id.editNoteEditText);
        editButton = findViewById(R.id.editButton);

        editAmountEditText.setText(String.valueOf(getIntent().getDoubleExtra("amount", 0)));
        editNoteEditText.setText(getIntent().getStringExtra("note"));
        setToggleButtonChecked(TransactionType.valueOf(getIntent().getStringExtra("type")));

        editIncomeTypeButton.setOnClickListener(this);
        editExpenseTypeButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    private void setToggleButtonChecked(TransactionType type) {
        switch (type) {
            case INCOME:
                break;
            case EXPENSE:
                toggleTwoButton(editExpenseTypeButton, editIncomeTypeButton);
        }
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
            case R.id.editIncomeTypeButton:
                toggleTwoButton(editIncomeTypeButton, editExpenseTypeButton);
                break;
            case R.id.editExpenseTypeButton:
                toggleTwoButton(editExpenseTypeButton, editIncomeTypeButton);
                break;
            case R.id.editButton:
                insertTransactionToDatabase();
                finish();
        }
    }

    private void insertTransactionToDatabase() {
        TransactionService transactionService = new TransactionService(this);

        Float amount = Float.valueOf(editAmountEditText.getText().toString());
        String note = editNoteEditText.getText().toString();
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
        if (editIncomeTypeButton.isChecked()) {
            return INCOME.name();
        } else {
            return  EXPENSE.name();
        }
    }
}
