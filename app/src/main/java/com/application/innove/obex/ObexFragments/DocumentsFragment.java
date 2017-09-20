package com.application.innove.obex.ObexFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.application.innove.obex.ObexActivities.AppCompatFragment;
import com.application.innove.obex.ObexActivities.VerifyImageActivity;
import com.application.innove.obex.ObexAdapters.DocumentCategoryAdapter;
import com.application.innove.obex.ObexExpandableModel.DocumentDummy;
import com.application.innove.obex.ObexModel.Document;
import com.application.innove.obex.ObexModel.DocumentResponseModel;
import com.application.innove.obex.ObexService.WebService;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.OBEXConstants;
import com.application.innove.obex.Utilityclass.SessionManager;
import com.application.innove.obex.Utilityclass.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DocumentFragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.content.ContentValues.TAG;


/**
 * Created by abhisheksharma on 04-Aug-2017.
 */

public class DocumentsFragment extends AppCompatFragment {
//public class DocumentsFragment extends AppCompatFragment implements DocumentCategoryAdapter.OnItemClickListener {


    //region Initialization
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerview;
    Uri uriImage;
    public static String imagePath = "";
    ListView list;
    File compressedImage;
    public static String fdata = "";
    private String InnovID = "";
    final int PIC_CROP = 1;

    public static LinearLayout layoutActionSheet;

    public static Animation showPicker, hidePicker;
    private WebService wb = new WebService();
    Context context;
    public static final int REQUEST_CODE_CAMERA = 0012;
    public static final int REQUEST_CODE_GALLERY = 0013;


    private DocumentCategoryAdapter adapter;

    private List<Document> lstAddressProof = new ArrayList<>();
    private List<Document> lstAgeProof = new ArrayList<>();
    private List<Document> lstIdProof = new ArrayList<>();
    private List<Document> lstOthers = new ArrayList<>();
    private List<DocumentDummy> uniqueList = new ArrayList<>();

    public DocumentsFragment() {
    }

    //endregion

    //region oncreate

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //endregion

    // region  Oncreateview
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this.getActivity().getApplicationContext();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.documetsfragment, container, false);
        try {
            ButterKnife.inject(this, rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        layoutActionSheet = rootView.findViewById(R.id.cameraBottomLayout);
        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerview.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        animationview();


        LoadFromJSON();
       // LoadFilteredList();
        //showProgressDialog();

        return rootView;

    }
    //endregion

    //region Load from Asset json
    private void LoadFromJSON() {
        List<DocumentDummy> lst = prepareDocumentDummy();
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        for (int i = 0; i < lst.size(); i++) {
            if ((lst.get(i).getCheckListID() != null)) {

                if (lst.get(i).getCheckListID() == 1) {
                    if (!flag1) {
                        flag1 = true;
                        uniqueList.add(lst.get(i));
                    }
                    lstAddressProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));

                }
                if (lst.get(i).getCheckListID() == 2) {
                    if (!flag2) {
                        flag2 = true;
                        uniqueList.add(lst.get(i));

                    }
                    lstAgeProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                }
                if (lst.get(i).getCheckListID() == 3) {
                    if (!flag3) {
                        flag3 = true;
                        uniqueList.add(lst.get(i));
                    }
                    lstIdProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                }

                if (!flag4) {
                    if (lst.get(i).getCheckListID() == 4) {
                        if (!flag4) {
                            flag4 = true;
                            uniqueList.add(lst.get(i));
                        }
                        lstOthers.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                    }
                }
            }
        }//end of for loop
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        adapter = new DocumentCategoryAdapter(makeAllDocumentHeader());
        recyclerview.setAdapter(adapter);
    }

    //endregion

    //region Load From URL for Showing Documents
    public void LoadFilteredList() {


        WebAPI.getInstance().doObexDocumentDetails(new WebAPI.DocumentsApiObexCallback() {
            @Override
            public void onObexDocumentdetailsinformation(DocumentResponseModel response) {
                hideProgressDialog();
                if (response != null) {
                    if (response.getStatus().equals("1")) {
                        if (response.getDocuments() != null) {
                            List<DocumentDummy> lst = response.getDocuments();

                            if (lst.size() > 0) {
                                boolean flag1 = false;
                                boolean flag2 = false;
                                boolean flag3 = false;
                                boolean flag4 = false;
                                for (int i = 0; i < lst.size(); i++) {
                                    if ((lst.get(i).getCheckListID() != null)) {

                                        if (lst.get(i).getCheckListID() == 1) {
                                            if (!flag1) {
                                                flag1 = true;
                                                uniqueList.add(lst.get(i));
                                            }
                                            lstAddressProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));

                                        }

                                        if (lst.get(i).getCheckListID() == 2) {
                                            if (!flag2) {
                                                flag2 = true;
                                                uniqueList.add(lst.get(i));

                                            }
                                            lstAgeProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                                        }

                                        if (lst.get(i).getCheckListID() == 3) {
                                            if (!flag3) {
                                                flag3 = true;
                                                uniqueList.add(lst.get(i));

                                            }
                                            lstIdProof.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                                        }

                                        if (!flag4) {
                                            if (lst.get(i).getCheckListID() == 4) {
                                                if (!flag4) {
                                                    flag4 = true;
                                                    uniqueList.add(lst.get(i));
                                                }
                                                lstOthers.add(new Document(lst.get(i).getDocName(), lst.get(i).getDoctype(),lst.get(i).getDocID(), true));
                                            }
                                        }
                                    }
                                }//end of for loop

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recyclerview.setLayoutManager(layoutManager);
                                adapter = new DocumentCategoryAdapter(makeAllDocumentHeader());
                                recyclerview.setAdapter(adapter);
                            }
                        }

                    }
                } else {
                    ToastUtils.showToast(getActivity(), R.string.Invalidattempt);
                    // hideProgressDialog();
                }
            }

            @Override
            public void onFailure(String failureMessage) {

                hideProgressDialog();
                failureMessage = "Something went wrong Please try again Later";
            }
        });


    }


    public DocumentDummy makeFirstHeader() {
        //return new DocumentDummy(uniqueList.get(0).getCheckListName(), lstAddressProof);
        return new DocumentDummy("Address Proof", lstAddressProof);
    }

    public DocumentDummy makeSecondHeader() {
        //return new DocumentDummy(uniqueList.get(1).getCheckListName(), lstAgeProof);
        return new DocumentDummy("Age Proof", lstAgeProof);
    }

    public DocumentDummy makeThirdHeader() {
        return new DocumentDummy("ID Proof", lstIdProof);
    }

    public DocumentDummy makeFourthHeader() {
        return new DocumentDummy("Others", lstOthers);
    }


    public List<DocumentDummy> makeAllDocumentHeader() {
        if (lstAddressProof != null && lstAgeProof != null && lstIdProof != null && lstOthers != null) {
            return Arrays.asList(makeFirstHeader(),
                    makeSecondHeader(),
                    makeThirdHeader(),
                    makeFourthHeader());
        }
        return null;
    }

    //endregion

    //region Code Snippet for Offline JSon Working
    public List<DocumentDummy> prepareDocumentDummy() {
        try {
            List<DocumentDummy> DocumentDummyList = new ArrayList<>();
            InputStream stream = getActivity().getAssets().open("Document.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String data = builder.toString();
            DocumentDummyList = new Gson().fromJson(data, new TypeToken<List<DocumentDummy>>() {
            }.getType());
            return DocumentDummyList;
        } catch (Exception e) {
            //java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
            //dont keep root key...
            e.printStackTrace();
        }
        return null;
    }

    //endregion

    //region Animation for Opening Camera gallery

    private void animationview() {

        AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation ends
        layoutActionSheet.startAnimation(alpha);
        showPicker = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        hidePicker = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        showPicker.setAnimationListener(animListener);
        hidePicker.setAnimationListener(animListener);
    }

    private Animation.AnimationListener animListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
            if (animation == showPicker) {
                layoutActionSheet.setVisibility(View.VISIBLE);
            }

            if (animation == hidePicker) {
                layoutActionSheet.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            if (animation == showPicker) {
                layoutActionSheet.setVisibility(View.VISIBLE);
            }

            if (animation == hidePicker) {
                layoutActionSheet.setVisibility(View.GONE);
            }
        }
    };


    @OnClick(R.id.btnCamera)
    public void onCamera() {
        boolean SearchInnovID = SessionManager.getBooleanFromPreferences(context, "InnovIDSelected");
        if (SearchInnovID) {
            if (layoutActionSheet.getVisibility() == View.VISIBLE) {
                layoutActionSheet.startAnimation(hidePicker);
            }
            EasyImage.openCamera(this, REQUEST_CODE_CAMERA);


        } else {
            ToastUtils.showToastWithoutStrResources(context, "Please Enter Innov_ID first");
        }

    }

    @OnClick(R.id.btnGallery)
    public void onGallery() {
        boolean SearchInnovID = SessionManager.getBooleanFromPreferences(context, "InnovIDSelected");
        if (SearchInnovID) {
            if (layoutActionSheet.getVisibility() == View.VISIBLE) {
                layoutActionSheet.startAnimation(hidePicker);
            }
            EasyImage.openGallery(this, REQUEST_CODE_GALLERY);

        } else {
            ToastUtils.showToastWithoutStrResources(context, "Please Enter Innov_ID first");
        }
    }

    @OnClick(R.id.btnCancel)
    public void onCancel() {
        if (layoutActionSheet.getVisibility() == View.VISIBLE) {
            layoutActionSheet.startAnimation(hidePicker);
        }
    }

    //endregion

    //region Imageclick and Compressing

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Log.e("Image : ", "Wrong Image");
            }

            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
//                try {
                if (imageFile == null) {
                    ToastUtils.showToastWithoutStrResources(getActivity(), "Error acquiring image, please try again");
                    return;
                }

                uriImage = getOutputMediaFileUri(imageFile);
                String path = uriImage.toString();
                File actualfile = null;
                try {
                    actualfile = new File(new URI(path));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                imagePath = compressfile(actualfile);
                Intent i = new Intent(getActivity(), VerifyImageActivity.class);
                i.putExtra("imageUri", uriImage.toString());
                startActivity(i);

            }


        });

    }


    private String compressfile(File actualfile) {
        String encodedImage = "";
        try {
            compressedImage = new Compressor(this.context).setMaxWidth(640).setMaxHeight(480).setQuality(75).setCompressFormat(Bitmap.CompressFormat.WEBP).compressToFile(actualfile);
            Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(compressedImage));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

    public static Uri getOutputMediaFileUri(File file) {
        return Uri.fromFile(file);
    }



}
