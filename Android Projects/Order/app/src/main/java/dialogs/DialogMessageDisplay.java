package dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.NumberFormat;

public class DialogMessageDisplay{

    public static AlertDialog displayInfoMessage(Context context, String title, String message){

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

    public static AlertDialog displayErrorMessage(Context context, String title, String message){

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


    public static AlertDialog displayWifiSettingsDialog(final Activity activity,final Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }


    public static AlertDialog displayGpsSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }


    public static AlertDialog displayAddAccountSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displayAirplaneModeSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displayBluetoothSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }


    public static AlertDialog displayMobileDataSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }


    public static AlertDialog displayScreenSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displaySystemLanguageSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }


    public static AlertDialog displayNFCSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displaySyncSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displaySoundSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displayApplicationManagerSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displaySettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }

    public static AlertDialog displayDateSettingsDialog(final Activity activity,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();

            }
        });
        builder.show();

        return builder.create();
    }



    public AlertDialog displayCustomLayoutDialog(int layout,final Context context, String title, String message, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
        Button tb = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        tb.setOnClickListener(new CustomListener(dialog, context));

        return dialog;
    }

    public AlertDialog displayCustomLayout(int layout,final Context context, String title, int theme){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setTitle(title);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
        Button tb = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        tb.setOnClickListener(new CustomListener(dialog, context));

        return dialog;
    }

    public AlertDialog displayLayoutDialog(int layout,final Context context, int theme, View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
        Button tb = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        tb.setOnClickListener(listener);

        return dialog;
    }

    public AlertDialog displayCustomViewDialog(View view,final Context context, String title, int theme, View.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,theme);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
        Button tb = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        tb.setOnClickListener(listener);

        return dialog;
    }

    public static class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private Context context;
        public CustomListener(Dialog dialog, Context context) {
            this.dialog = dialog;
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Custom Layout", Toast.LENGTH_LONG).show();
        }
    }

    public static void displayProgressDialog(Context context, int style, String title, String message, boolean indeterminate, boolean flag){
        ProgressDialog builder = new ProgressDialog(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setProgressStyle(style);
        builder.setIndeterminate(indeterminate);
        builder.setCancelable(flag);
        builder.show();
    }


    public static void displayCustomProgressDialog(int layout, Context context, int style, String title, String message, int theme, boolean flag){
        ProgressDialog builder = new ProgressDialog(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        builder.setProgressStyle(style);
        builder.setCancelable(flag);
        builder.show();
    }


    public static void displayProgressNumberDialog(Context context, int style, String title, String message, int theme, int max, boolean cancel){
        ProgressDialog builder = new ProgressDialog(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setMax(max);
        builder.setProgressStyle(style);
        builder.setCanceledOnTouchOutside(cancel);
        builder.show();
    }

    public static void displayProgressPercentFormatDialog(Context context, int style, String title, String message, int theme, NumberFormat numberFormat, boolean cancel){
        ProgressDialog builder = new ProgressDialog(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setProgressPercentFormat(numberFormat);
        builder.setProgressStyle(style);
        builder.setCanceledOnTouchOutside(cancel);
        builder.show();
    }

    public static void displayProgress(Context context, int style, String title, String message, int theme, int max, int value, boolean cancel){
        ProgressDialog builder = new ProgressDialog(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setMax(max);
        builder.setProgress(value);
        builder.setProgressStyle(style);
        builder.setCanceledOnTouchOutside(cancel);
        builder.show();
    }

    public static void displayProgressCustomDrawable(Context context, int style, String title, String message, int theme, Drawable drawable, boolean cancel){
        ProgressDialog builder = new ProgressDialog(context, theme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setProgressDrawable(drawable);
        builder.setProgressStyle(style);
        builder.setCanceledOnTouchOutside(cancel);
        builder.show();
    }
}
