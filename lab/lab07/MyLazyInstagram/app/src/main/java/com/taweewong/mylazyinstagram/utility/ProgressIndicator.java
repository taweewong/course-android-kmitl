package com.taweewong.mylazyinstagram.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.taweewong.mylazyinstagram.R;

public class ProgressIndicator {
    private Dialog progressDialog;

    public ProgressIndicator(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading, null);

        progressDialog = new Dialog(context);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(view);
    }

    public void show() {
        progressDialog.show();
    }

    public void hide() {
        progressDialog.hide();
    }
}
