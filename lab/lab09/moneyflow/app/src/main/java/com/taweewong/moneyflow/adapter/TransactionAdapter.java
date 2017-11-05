package com.taweewong.moneyflow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.entity.Transaction;
import com.taweewong.moneyflow.viewholder.TransactionViewHolder;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    private List<Transaction> transactions;
    private Context context;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.transaction_view_holder, null, false);

        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        ImageView typeImage = holder.getTypeImage();
        TextView noteText = holder.getNoteText();
        TextView dateText = holder.getDateText();
        TextView amountText = holder.getAmountText();

        noteText.setText(transactions.get(position).getNote());
        dateText.setText(transactions.get(position).getDate());
        amountText.setText(String.valueOf(transactions.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
