package com.taweewong.moneyflow.controller;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.dao.TransactionDAO;
import com.taweewong.moneyflow.database.TransactionDB;
import com.taweewong.moneyflow.entity.Transaction;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        TextView addTransactionText = findViewById(R.id.addTransactionText);
        addTransactionText.setOnClickListener(this);

        TransactionDB transactionDB = Room.databaseBuilder(this,
                TransactionDB.class, "Transaction")
                .fallbackToDestructiveMigration()
                .build();

        Transaction transaction = new Transaction(150, "note", "date", "type");

        TransactionDAO dao = transactionDB.getTransactionDAO();

        Observable.fromCallable(() -> dao.insertTransaction(transaction))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Observe", "subscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("Observe", "next");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Observe", "error");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Observe", "complete");

                    }
                });

        Observable.fromCallable(dao::getAllTransaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(transactions -> {
                   for (Transaction trans : transactions) {
                       Log.d("Observe", trans.getId() + " : " + trans.getAmount());
                   }
                });

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, AddTransactionActivity.class));
    }
}
