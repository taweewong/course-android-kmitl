package com.taweewong.mylazyinstagram.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.taweewong.mylazyinstagram.R;

public class ProgressIndicator {
    private Context context;
    private Dialog progressDialog;

    public ProgressIndicator(Context context) {
        this.context = context;
    }

    public void show() {
        progressDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.loading, null);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(view);
        progressDialog.show();
    }

    public void hide() {
        progressDialog.dismiss();
    }
}
