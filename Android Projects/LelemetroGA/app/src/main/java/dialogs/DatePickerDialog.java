package dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

    private EditText katataksi;
    private Context context;
    private int day, year, month;

    public DatePickerDialog(View v, Context context) {
        katataksi = (EditText)v;
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstannceState){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        return new android.app.DatePickerDialog(context, this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        int monthMultiplied = month + 1;
        String date = day + "/" + monthMultiplied + "/" + year;
        katataksi.setText(date);

        katataksi.setFocusable(false);
        katataksi.setTextIsSelectable(true);

    }
}
