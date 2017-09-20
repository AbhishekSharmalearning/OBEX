package com.application.innove.obex.Utilityclass;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Abhishek
 */
//To Read More on Permissions:
// https://developer.android.com/training/permissions/requesting.html#perm-check
public class PermissionUtil {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_IMAGES = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA_ACCESS = 456;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_DOC = 1234;



    //
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                //To help find situations where the user might need an explanation,
                // Android provides a utiltity method, shouldShowRequestPermissionRationale().
                // This method returns true if the app has requested this permission previously
                // and the user denied the request.
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_IMAGES);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }
                else
                {
                    ActivityCompat.
                            requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_IMAGES);
                }
                return false;
            }
            //if permission was already granted
            else
            {
                //You already have the permission, just go ahead.
                return true;
            }
        }
        //phone was less than API level 23
        else {
            return true;
        }
    }

    public static boolean checkPermissionForCamera(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //To help find situations where the user might need an explanation,
                // Android provides a utiltity method, shouldShowRequestPermissionRationale().
                // This method returns true if the app has requested this permission previously
                // and the user denied the request.
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.CAMERA))
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera Access permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]
                                            {Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA_ACCESS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA_ACCESS);
                }
                return false;
            }
            //
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                //To help find situations where the user might need an explanation,
                // Android provides a utiltity method, shouldShowRequestPermissionRationale().
                // This method returns true if the app has requested this permission previously
                // and the user denied the request.
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_DOC);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }
                else
                {
                    ActivityCompat.
                            requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_FOR_DOC);
                }
                return false;
            }
            //if permission was already granted
            else
            {
                //You already have the permission, just go ahead.
                return true;
            }
        }
        //phone was less than API level 23
        else {
            return true;
        }
    }


}
