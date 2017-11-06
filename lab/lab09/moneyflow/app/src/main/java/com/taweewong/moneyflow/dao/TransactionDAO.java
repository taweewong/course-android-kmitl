package com.taweewong.moneyflow.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.taweewong.moneyflow.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM `TRANSACTION`")
    List<Transaction> getAllTransaction();

    @Insert
    Long insertTransaction(Transaction transaction);

    @Delete
    int deleteTransaction(Transaction transaction);

    @Query("SELECT sum(amount) FROM `TRANSACTION` WHERE type = 'INCOME'")
    Float getTransactionIncomeSummary();
}
