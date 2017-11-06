package com.taweewong.moneyflow.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.model.Transaction;
import com.taweewong.moneyflow.viewholder.TransactionViewHolder;
import com.taweewong.moneyflow.model.Transaction.TransactionType;

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

        TransactionType type = TransactionType.valueOf(transactions.get(position).getType());
        String amount = String.valueOf(transactions.get(position).getAmount());
        String date = transactions.get(position).getDate();
        String note = transactions.get(position).getNote();

        setTransactionTypeImage(type, typeImage);
        noteText.setText(note);
        dateText.setText(date);
        setTransactionAmountDisplay(type, amount, amountText);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    private void setTransactionTypeImage(TransactionType type, ImageView typeImage) {
        switch (type) {
            case INCOME:
                typeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_left_down));
                break;
            case EXPENSE:
                typeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_right_up));
        }
    }

    private void setTransactionAmountDisplay(TransactionType type, String text, TextView amountText) {
        switch (type) {
            case INCOME:
                amountText.setTextColor(ContextCompat.getColor(context, R.color.textGreen));
                amountText.setText(String.format("+ ฿%s", text));
                break;
            case EXPENSE:
                amountText.setTextColor(ContextCompat.getColor(context, R.color.textRed));
                amountText.setText(String.format("- ฿%s", text));
        }
    }
}
