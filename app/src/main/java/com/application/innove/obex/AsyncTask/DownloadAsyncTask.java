package com.application.innove.obex.AsyncTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;

import com.application.innove.obex.ObexActivities.Document_capture_and_upload;
import com.application.innove.obex.ObexService.WebService;
import com.application.innove.obex.R;
import com.application.innove.obex.Utilityclass.OBEXConstants;
import com.application.innove.obex.Utilityclass.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by abhisheksharma on 15-Sep-2017.
 */

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {

    private String output = "", fdata = "";
    private JSONObject jsonObject = new JSONObject();
    private JSONArray jsonArray = new JSONArray();
    String InnovID, DocumentID,DocType;
    private WebService wb = new WebService();
    Context context;
    String status;
    private String menucategory="";


    public DownloadAsyncTask(String innID, String documentID, String documenttype, Context ctx) {
        this.InnovID = innID;
        this.DocumentID = documentID;
        this.DocType =documenttype;
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            jsonObject.put("InnovID", InnovID);
            jsonObject.put("DocID", DocumentID);
            jsonArray.put(jsonObject);
            fdata = jsonArray.toString();
            output = wb.callServices(fdata, OBEXConstants.DownloadDocuments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            if (wb.fIsErrorInService) {
                ToastUtils.showToast(context, R.string.Invalidattempt);
            } else {
                JSONObject jsonObject = new JSONObject(output);
                //String images = jsonObject.getString("ImageArr");
                String status=jsonObject.getString("Status");
                if(status.equals("Image found"))
                {
                    addNotification();
                    String image=jsonObject.getString("ImageArr");
                    SaveImage(image);
                }
                else
                {
                    ToastUtils.showToastWithoutStrResources(context,"Image not Found");
                }
//                byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                image.setImageBitmap(decodedByte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNotification(){
        Notification.Builder builder = new Notification.Builder(context);
        Intent notificationIntent = new Intent(context,Document_capture_and_upload.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
        builder.setSmallIcon(R.drawable.download).setContentTitle("Innov Documents").setContentText("Download Complete...").setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = builder.getNotification();
        notificationManager.notify(R.drawable.download, notification);
    }

    private void SaveImage(String imagestring){
        try
        {
            byte[] decodedString = Base64.decode(imagestring, Base64.DEFAULT);
            Bitmap bitmapimage= BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            String iconsStoragePath = Environment.getExternalStorageDirectory() + "/INNOV_DOCS"+"/";
            File sdIconStorageDir = new File(iconsStoragePath);
            if (!sdIconStorageDir.exists())
            {
                sdIconStorageDir.mkdirs();
            }
            String filePath = sdIconStorageDir.toString()+"/"+DocType+".PNG";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            bitmapimage.compress(Bitmap.CompressFormat.PNG,100,bos);
            bos.flush();
            bos.close();
            snackbar();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void snackbar()
    {
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/INNOV_DOCS"+"/"+DocType+".PNG";
        Snackbar snackbar = Snackbar.make(Document_capture_and_upload.parentlayout,iconsStoragePath, Snackbar.LENGTH_LONG).setAction("OPEN", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });snackbar.show();
    }
}

