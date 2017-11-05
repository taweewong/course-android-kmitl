package com.taweewong.moneyflow.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.taweewong.moneyflow.entity.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM `TRANSACTION`")
    List<Transaction> getAllTransaction();

    @Insert
    Long insertTransaction(Transaction transaction);
}
