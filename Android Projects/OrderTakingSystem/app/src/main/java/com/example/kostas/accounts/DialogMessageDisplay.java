package com.example.kostas.accounts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class DialogMessageDisplay{

    public static AlertDialog displayMessage(Context context, String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        return  builder.create();

    }
}
