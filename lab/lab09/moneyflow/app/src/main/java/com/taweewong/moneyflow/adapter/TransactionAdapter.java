package com.taweewong.moneyflow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taweewong.moneyflow.R;
import com.taweewong.moneyflow.controller.EditTransactionActivity;
import com.taweewong.moneyflow.model.Transaction;
import com.taweewong.moneyflow.model.Transaction.TransactionType;
import com.taweewong.moneyflow.viewholder.TransactionViewHolder;

import java.text.DecimalFormat;
import java.util.List;

import static com.taweewong.moneyflow.model.Transaction.TransactionExtraName.*;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> implements View.OnClickListener {
    private List<Transaction> transactions;
    private Context context;
    private RecyclerView recyclerView;

    public TransactionAdapter(Context context, List<Transaction> transactions, RecyclerView recyclerView) {
        this.transactions = transactions;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.transaction_view_holder, null, false);
        itemView.setOnClickListener(this);

        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        ImageView typeImage = holder.getTypeImage();
        TextView noteText = holder.getNoteText();
        TextView dateText = holder.getDateText();
        TextView amountText = holder.getAmountText();

        TransactionType type = TransactionType.valueOf(transactions.get(position).getType());
        Double amount = transactions.get(position).getAmount();
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

    private void setTransactionAmountDisplay(TransactionType type, Double text, TextView amountText) {
        DecimalFormat formatter = new DecimalFormat("#,###.0");

        switch (type) {
            case INCOME:
                amountText.setTextColor(ContextCompat.getColor(context, R.color.textGreen));
                amountText.setText(String.format("+ ฿%s", formatter.format(text)));
                break;
            case EXPENSE:
                amountText.setTextColor(ContextCompat.getColor(context, R.color.textRed));
                amountText.setText(String.format("- ฿%s", formatter.format(text)));
        }
    }

    @Override
    public void onClick(View view) {
        int itemPosition = recyclerView.getChildAdapterPosition(view);
        Transaction transactionItem = transactions.get(itemPosition);
        Intent intent = new Intent(context, EditTransactionActivity.class);
        intent.putExtra(TRANSACTION_EXTRA_NAME, transactionItem);

        context.startActivity(intent);
    }
}
