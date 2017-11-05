package com.taweewong.moneyflow.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.taweewong.moneyflow.dao.TransactionDAO;
import com.taweewong.moneyflow.entity.Transaction;

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDB extends RoomDatabase {
    public abstract TransactionDAO getTransactionDAO();
}
