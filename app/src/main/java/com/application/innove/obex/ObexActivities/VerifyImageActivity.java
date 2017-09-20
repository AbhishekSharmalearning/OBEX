package com.application.innove.obex.ObexActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.innove.obex.ObexAdapters.DocumentCategoryAdapter;
import com.application.innove.obex.ObexFragments.DocumentsFragment;
import com.application.innove.obex.ObexService.WebService;
import com.application.innove.obex.Other.ArtistViewHolder;
import com.application.innove.obex.R;
import com.application.innove.obex.Utilityclass.OBEXConstants;
import com.application.innove.obex.Utilityclass.SessionManager;
import com.application.innove.obex.Utilityclass.ToastUtils;
import com.soundcloud.android.crop.Crop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.content.ContentValues.TAG;
import static com.application.innove.obex.ObexFragments.DocumentsFragment.imagePath;


public class VerifyImageActivity extends AppCompatActivity {
    private ImageView resultView;
    private Uri myUri;
    private Uri output;
    private String tokenid = "";
    private String fcmid = "";
    Bundle extras;
    Context ctx;
    private String fdata = "";
    private WebService wb = new WebService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ctx = this;
        extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("imageUri"));
        extras = getIntent().getExtras();
        resultView = (ImageView) findViewById(R.id.result_image);
        beginCrop(myUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            output = Crop.getOutput(result);
            InsertDocumentDetails insertdocument = (InsertDocumentDetails) new InsertDocumentDetails();
            insertdocument.execute();
          //  Intent in = new Intent(ctx, Document_capture_and_upload.class);
            showProgressDialog();
           // startActivity(in);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class InsertDocumentDetails extends AsyncTask<String, Void, String> {
        JSONObject jsonObject;
        JSONObject jsonObjectone;
        JSONObject jsonObjecttwo;
        JSONArray innerArray = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        private ProgressDialog dialog;
        String output = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                jsonObject = new JSONObject();
                jsonObjectone = new JSONObject();
                jsonObjecttwo = new JSONObject();
                String InnID = SessionManager.getStringFromPreferences(ctx, "sharedInnovIDafterparse");
                String Documentname = SessionManager.getStringFromPreferences(ctx, "Doctype");
                jsonObjecttwo.put("InnovID", InnID);
                jsonObjectone.put("ImageArr", imagePath);
                jsonObjectone.put("Doctype", Documentname);
                innerArray.put(jsonObjectone);
                jsonObjecttwo.put("AllDocs", innerArray);
                jsonArray.put(jsonObjecttwo);
                Log.e(TAG, "Send Json data : " + jsonArray);
                fdata = jsonArray.toString();
                output = wb.callServices(fdata, OBEXConstants.insertdocuments);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            try {
                if (wb.fIsErrorInService) {
                    ToastUtils.showToast(ctx, R.string.Invalidattempt);
                } else {
                    String upimage = "[" + output + "]";
                    JSONArray mArray = new JSONArray(upimage);
                    Log.e(TAG, "Responce data : " + mArray);
                    JSONObject jsonObject = mArray.getJSONObject(0);
                    String status = jsonObject.getString("Status");
                    if (status.equals("INSERT SUCCESSFULLY")) {
                        Intent in = new Intent(ctx, Document_capture_and_upload.class);
                        startActivity(in);
                        ToastUtils.showToast(ctx, R.string.Imageupload);
                        hideProgressDialog();

                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(DocumentCategoryAdapter.ACTION_SIGNED_IMAGE_AFTER_UPLOAD);
                        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        broadcastIntent.putExtra("IsImageUploaded",
                                "YesImageUploaded");
                        ctx.sendBroadcast(broadcastIntent);

                    } else {
                        ToastUtils.showToast(ctx, R.string.FailedUpload);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
