package com.application.innove.obex.ObexActivities;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by Abhishek on 8/30/2017.
 */

public  class AppCompatFragment extends Fragment
{
	static ProgressDialog progress;


	public void showProgressDialog()
	{
		progress=new ProgressDialog(getActivity());
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
