package com.taweewong.moneyflow.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taweewong.moneyflow.R;

public class TransactionViewHolder extends RecyclerView.ViewHolder{
    private ImageView typeImage;
    private TextView noteText;
    private TextView dateText;
    private TextView amountText;

    public TransactionViewHolder(View itemView) {
        super(itemView);
        typeImage = itemView.findViewById(R.id.typeImage);
        noteText = itemView.findViewById(R.id.noteText);
        dateText = itemView.findViewById(R.id.dateText);
        amountText = itemView.findViewById(R.id.amountText);
    }

    public ImageView getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(ImageView typeImage) {
        this.typeImage = typeImage;
    }

    public TextView getNoteText() {
        return noteText;
    }

    public void setNoteText(TextView noteText) {
        this.noteText = noteText;
    }

    public TextView getDateText() {
        return dateText;
    }

    public void setDateText(TextView dateText) {
        this.dateText = dateText;
    }

    public TextView getAmountText() {
        return amountText;
    }

    public void setAmountText(TextView amountText) {
        this.amountText = amountText;
    }
}
