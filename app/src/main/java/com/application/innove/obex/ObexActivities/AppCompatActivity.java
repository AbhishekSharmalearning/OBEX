package com.application.innove.obex.ObexActivities;

import android.app.ProgressDialog;

/**
 * Created by Abhishek on 8/30/2017.
 */

public class AppCompatActivity extends android.support.v7.app.AppCompatActivity
{
	ProgressDialog progress;


	public  void showProgressDialog()
	{
		progress=new ProgressDialog(this);
		progress.setMessage("Loading...");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.setIndeterminate(true);

		progress.show();
	}

	public void hideProgressDialog()
	{
		try {
			progress.dismiss ();
		}catch (Exception ex)
		{

		}
	}
}
