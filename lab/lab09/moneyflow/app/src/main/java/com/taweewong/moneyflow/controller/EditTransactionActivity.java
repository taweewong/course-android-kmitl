package com.taweewong.moneyflow.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

import static com.taweewong.moneyflow.model.Transaction.TransactionType.*;
import static com.taweewong.moneyflow.model.Transaction.TransactionExtraName.*;

public class EditTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    private ToggleButton editIncomeTypeButton;
    private ToggleButton editExpenseTypeButton;
    private EditText editAmountEditText;
    private EditText editNoteEditText;
    private TransactionService transactionService;
    private Transaction transaction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_transaction_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        getSupportActionBar().setElevation(0);
        setTitle("Edit Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collectExtras();
        initializeService();
        initializeView();
    }

    private void collectExtras() {
        transaction = getIntent().getParcelableExtra(TRANSACTION_EXTRA_NAME);
    }

    private void initializeService() {
        transactionService = new TransactionService(this);
    }

    private void initializeView() {
        editIncomeTypeButton = findViewById(R.id.editIncomeTypeButton);
        editExpenseTypeButton = findViewById(R.id.editExpenseTypeButton);
        editAmountEditText = findViewById(R.id.editAmountEditText);
        editNoteEditText = findViewById(R.id.editNoteEditText);
        Button editButton = findViewById(R.id.editButton);

        editAmountEditText.setText(String.valueOf(transaction.getAmount()));
        editNoteEditText.setText(transaction.getNote());
        setToggleButtonChecked(TransactionType.valueOf(transaction.getType()));

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
                break;
            case R.id.menu_delete:
                deleteTransactionInDatabase();
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
                updateTransactionToDatabase();
                finish();
        }
    }

    private void updateTransactionToDatabase() {
        Transaction newTransaction = createTransactionFromInput();
        newTransaction.setId(transaction.getId());

        transactionService.updateTransaction(newTransaction);
    }

    private Transaction createTransactionFromInput() {
        float amount = Float.valueOf(editAmountEditText.getText().toString());
        String note = editNoteEditText.getText().toString();
        String dateString = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
        String type = getTransactionType();

        return new Transaction(amount, note, dateString, type);
    }

    private void deleteTransactionInDatabase() {
        Transaction deleteTransaction = new Transaction();
        deleteTransaction.setId(transaction.getId());
        transactionService.deleteTransaction(deleteTransaction);
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
