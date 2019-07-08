package com.upasthit.custom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog {

    static ProgressDialog progress;

    public static Dialog showProgressDialog(Context context) {

//		Dialog dialog = new Dialog(ctx, R.style.Progres_Custom_Dialog);
//		dialog.setContentView(R.layout.custom_progress_dialog);
//		dialog.setCancelable(true);
//
//		WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
//		WMLP.dimAmount = (float) 0.4;

//		return dialog;

        progress = new ProgressDialog(context);
        progress.setMessage("Please wait...");

        return progress;
    }

    public static void setMessage(String message) {
        progress.setMessage(message);
    }

}
