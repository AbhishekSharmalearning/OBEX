package com.application.innove.obex.Utilityclass;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by abhisheksharma on 08-Aug-2017.
 */

public class ToastUtils {
    static Toast toast;
    /*
    public static void showToast(Context c,@StringRes int toastText) {
           String message = c.getString(toastText);
           showToast(c,message);
       }
        public  static  void showToast(Context c,String  toastText)
       {
           if (toast != null) {
               toast.cancel();
           }
           toast = Toast.makeText(c,toastText, Toast.LENGTH_SHORT);
           toast.show();
       }
       */
    //pass the resource string
    public  static  void showToast(Context c, @StringRes int toastText)
    {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c,toastText, Toast.LENGTH_SHORT);
        toast.show();
    }
    //pass any hardcoded String
    public  static  void showToastWithoutStrResources(Context c,String toastText)
    {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c, toastText, Toast.LENGTH_SHORT);
        toast.show();
    }
}
