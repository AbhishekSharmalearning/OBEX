package com.application.innove.obex.Utilityclass;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by abhisheksharma on 08-Aug-2017.
 */
//Remember to add in manifest
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
public class InternetStatus
{
    //returns boolean true if a network connection is available.
    public static boolean checkConnection(Context c)
    {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
        if (ni == null)
        {
            // There are no active networks.
            return false;
        }
        else
        {
            return true;
        }
    }
}
