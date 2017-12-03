package com.primerexample.gpxviewer.utilities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.primerexample.gpxviewer.R;


public class Info {

private View view;
private Context context;

    public Info(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void showMessage(String message){

        Snackbar snackbar= Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        snackbar.setDuration(Constants.TIME);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

        TextView snackTextView = (TextView)
                snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setTextColor(ContextCompat.getColor(context,R.color.toolbarTextColor));
        snackTextView.setTextSize(context.getResources().getDimension(R.dimen.text_snack));
        snackbar.show();

    }






}
