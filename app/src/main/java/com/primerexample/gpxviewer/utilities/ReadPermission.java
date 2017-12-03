package com.primerexample.gpxviewer.utilities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.primerexample.gpxviewer.R;
import com.primerexample.gpxviewer.ui.MapActivity;

/**
 * Created by Максим on 01.12.2017.
 */

public class ReadPermission {




    public static void requestPermission(Context context, FloatingActionButton fabSearch){

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((MapActivity)context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(context);

                builder.setMessage(R.string.permission_explanation);

                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions((MapActivity)context,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        Constants.READ_FILE_PERMISSION_REQUEST_CODE);

                            }
                        }
                );
                builder.setCancelable(false);
                builder.create().show();

            }
            else {


                ActivityCompat.requestPermissions((MapActivity)context,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.READ_FILE_PERMISSION_REQUEST_CODE);


            }
        }
        else {

            fabSearch.show();

        }
    }


}
