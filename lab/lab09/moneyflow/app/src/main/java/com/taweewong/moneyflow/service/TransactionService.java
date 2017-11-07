package com.taweewong.moneyflow.service;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.taweewong.moneyflow.dao.TransactionDAO;
import com.taweewong.moneyflow.database.TransactionDB;
import com.taweewong.moneyflow.model.Transaction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransactionService {

    public interface OnGetTransactionCallback {
        void getTransactionCallback(List<Transaction> transactions);
    }

    public interface OnGetTransactionIncomeSummaryCallback {
        void getTransactionIncomeSummaryCallback(double incomeAmount);
    }

    public interface OnGetTransactionSummaryCallback {
        void getTransactionSummaryCallback(double summaryAmount);
    }

    private TransactionDAO transactionDAO;
    private TransactionDB transactionDB;
    private OnGetTransactionCallback transactionCallback;
    private OnGetTransactionIncomeSummaryCallback transactionIncomeSummaryCallback;
    private OnGetTransactionSummaryCallback transactionSummaryCallback;

    public TransactionService(Context context) {
        buildTransactionDatabase(context);
        transactionDAO = transactionDB.getTransactionDAO();
    }

    private void buildTransactionDatabase(Context context) {
        transactionDB = Room.databaseBuilder(context,
                TransactionDB.class, "Transaction")
                .fallbackToDestructiveMigration()
                .build();
    }

    public void insertTransaction(Transaction transaction) {
        Observable.fromCallable(() -> transactionDAO.insertTransaction(transaction))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteTransaction(Transaction transaction) {
        Observable.fromCallable(() -> transactionDAO.deleteTransaction(transaction))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void getAllTransactions() {
        Observable.fromCallable(transactionDAO::getAllTransaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(transactions -> transactionCallback.getTransactionCallback(transactions));
    }

    public void getTransactionIncomeSummary() {
        Observable.fromCallable(transactionDAO::getTransactionIncomeSummary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aDouble -> transactionIncomeSummaryCallback.getTransactionIncomeSummaryCallback(aDouble));
    }

    public void getTransactionSummary() {
        Observable.fromCallable(transactionDAO::getTransactionSummary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aDouble -> transactionSummaryCallback.getTransactionSummaryCallback(aDouble));
    }

    public void updateTransaction(Transaction transaction) {
        Observable.fromCallable(() -> transactionDAO.updateTransaction(transaction))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void setTransactionCallback(OnGetTransactionCallback transactionCallback) {
        this.transactionCallback = transactionCallback;
    }

    public void setTransactionIncomeSummaryCallback(OnGetTransactionIncomeSummaryCallback transactionIncomeSummaryCallback) {
        this.transactionIncomeSummaryCallback = transactionIncomeSummaryCallback;
    }

    public void setTransactionSummaryCallback(OnGetTransactionSummaryCallback transactionSummaryCallback) {
        this.transactionSummaryCallback = transactionSummaryCallback;
    }
}
