package com.taweewong.moneyflow.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.taweewong.moneyflow.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM `TRANSACTION`")
    List<Transaction> getAllTransaction();

    @Insert
    long insertTransaction(Transaction transaction);

    @Delete
    int deleteTransaction(Transaction transaction);

    @Query("SELECT IFNULL(sum(amount), 0) FROM `TRANSACTION` WHERE type = 'INCOME'")
    double getTransactionIncomeSummary();

    @Query("SELECT IFNULL(IFNULL(sum(amount), 0) - " +
            "IFNULL((SELECT sum(amount) FROM `TRANSACTION` WHERE type = 'EXPENSE'), 0), 0)" +
            " FROM `TRANSACTION` WHERE type = 'INCOME'")
    double getTransactionSummary();

    @Update
    int updateTransaction(Transaction transaction);
}
