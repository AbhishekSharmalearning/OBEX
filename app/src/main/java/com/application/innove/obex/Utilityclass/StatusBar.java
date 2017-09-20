package com.application.innove.obex.Utilityclass;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import com.application.innove.obex.R;

/**
 * Created by dm391 on 8/3/2017.
 */
public class StatusBar {
    public static void MatchStatusBGEqualsAndAboveLollipop(Activity act, Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            act.getWindow().setStatusBarColor(ctx.getResources().getColor(R.color.statusbar));
        }
    }
}