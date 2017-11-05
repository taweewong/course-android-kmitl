package com.taweewong.moneyflow.service;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.taweewong.moneyflow.dao.TransactionDAO;
import com.taweewong.moneyflow.database.TransactionDB;
import com.taweewong.moneyflow.entity.Transaction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransactionService {
    private TransactionDAO transactionDAO;
    private TransactionDB transactionDB;

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
                .subscribeOn(Schedulers.io());
    }

    public void deleteTransaction(Transaction transaction) {
        Observable.fromCallable(() -> transactionDAO.deleteTransaction(transaction))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public List<Transaction> getAllTransactions() {
        return Observable.fromCallable(transactionDAO::getAllTransaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .blockingFirst();
    }
}
