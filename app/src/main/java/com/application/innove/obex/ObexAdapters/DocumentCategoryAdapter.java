package com.application.innove.obex.ObexAdapters;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.innove.obex.AsyncTask.DownloadAsyncTask;
import com.application.innove.obex.ObexExpandableModel.DocumentDummy;
import com.application.innove.obex.ObexModel.DeleteResponseModel;
import com.application.innove.obex.ObexModel.Document;
import com.application.innove.obex.ObexModel.DownloadResponseModel;
import com.application.innove.obex.Other.ArtistViewHolder;
import com.application.innove.obex.Other.GenreViewHolder;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.SessionManager;
import com.application.innove.obex.Utilityclass.ToastUtils;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import static com.application.innove.obex.ObexFragments.DocumentsFragment.hidePicker;
import static com.application.innove.obex.ObexFragments.DocumentsFragment.layoutActionSheet;
import static com.application.innove.obex.ObexFragments.DocumentsFragment.showPicker;


public class DocumentCategoryAdapter extends
        ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder> {

//    private final OnItemClickListener listener;
//    public interface OnItemClickListener {
//        void onItemClick(Document item);
//    }

    private View dummyView;
    private Context ctx;
    private List<DocumentDummy> lstgroups;
    public static ProgressDialog progress;
    public static final String ACTION_SIGNED_IMAGE_AFTER_UPLOAD = "com.application.innove.obex.ObexAdapters.DocumentCategoryAdapter";


    public DocumentCategoryAdapter(List<DocumentDummy> groups) {
        super(groups);
        this.lstgroups = groups;


    }

    @Override
    public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        progress = new ProgressDialog(ctx);
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.document_cateory, parent, false);

        dummyView = view;


        return new GenreViewHolder(view);
    }

    @Override
    public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        progress = new ProgressDialog(ctx);
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.document_cateogory_item, parent, false);


        return new ArtistViewHolder(view);
    }

//    ExpandableGroup dummyGroup;
//    int currChildIndex;
    @Override
    public void onBindChildViewHolder(final ArtistViewHolder holder, int flatPosition, final ExpandableGroup group, final int childIndex) {
        final Document artist = ((DocumentDummy) group).getItems().get(childIndex);
        holder.setArtistName(artist.getName());

        IntentFilter filter = new IntentFilter(ACTION_SIGNED_IMAGE_AFTER_UPLOAD);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        ctx.registerReceiver(SyncServiceCompleatedBroadcastReceiver, filter);

//        holder.bind(((DocumentDummy) group).getItems().get(childIndex), listener);





        holder.uploaddocuments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                dummyGroup= group;
//                currChildIndex=childIndex;

                Document artist = ((DocumentDummy) group).getItems().get(childIndex);
//                ToastUtils.showToastWithoutStrResources(ctx, artist.getDocType());
                SessionManager.putStringInPreferences(ctx, artist.getdoctype(), "Doctype");
                SessionManager.putStringInPreferences(ctx, artist.getDocID(), "DocID");

                /*if (IsOwnReg.equals("YesImageUploaded")) {

                    holder.Signed.setImageResource(R.drawable.checkeddoc);
                }*/

                if (layoutActionSheet.getVisibility() == View.GONE) {
                    layoutActionSheet.startAnimation(showPicker);
                    layoutActionSheet.setVisibility(View.VISIBLE);
                } else {
                    layoutActionSheet.startAnimation(hidePicker);
                    layoutActionSheet.setVisibility(View.GONE);
                }
            }
        });

        holder.DeleteDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String documentID = artist.getDocID();
                String InnID = SessionManager.getStringFromPreferences(ctx, "sharedInnovIDafterparse");
                DeletDocumentsAfterUplaod(InnID, documentID);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCanceledOnTouchOutside(false);
                progress.show();


            }
        });

        holder.Downloaddocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String documentID = artist.getDocID();
                String documenttype = artist.getdoctype();
                String InnID = SessionManager.getStringFromPreferences(ctx, "sharedInnovIDafterparse");
                DownloadAsyncTask downloaddoc= (DownloadAsyncTask) new DownloadAsyncTask(InnID,documentID,documenttype,ctx);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                downloaddoc.execute();


            }
        });

    }



    @Override
    public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
    }


    private void DeletDocumentsAfterUplaod(String innov_id, String DocID) {

        WebAPI.getInstance().doDocumentsDelete(innov_id, DocID, new WebAPI.DeleteApiObexCallback() {

            @Override
            public void onSuccessObexDeleteDocuments(DeleteResponseModel response) {

                if (response != null) {

                    if (response.getStatus().equals("Success")) {

                        ToastUtils.showToastWithoutStrResources(ctx, "Document Deleted Succesfully");
                        progress.dismiss();

                    } else {
                        ToastUtils.showToast(ctx, R.string.Invalidattempt);
                        progress.dismiss();
                    }
                } else {
                    ToastUtils.showToast(ctx, R.string.Invalidattempt);

                }

            }

            @Override
            public void onFailure(String failureMessage)
            {
                ToastUtils.showToast(ctx,R.string.Wrong);
            }
        });
    }



    String IsOwnReg = "";
    private final BroadcastReceiver SyncServiceCompleatedBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            IsOwnReg = intent.getStringExtra("IsImageUploaded");
            if (IsOwnReg.equals("YesImageUploaded"))
            {

               // Document artist = ((DocumentDummy) dummyGroup).getItems().get(currChildIndex);

              /*  ArtistViewHolder nn = new ArtistViewHolder(dummyView);
                nn.Signed.setImageResource(R.drawable.checkeddoc);*/
            }
        }
    };
}


//    private final BroadcastReceiver SyncServiceCompleatedBroadcastReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//
//            final String IsOwnReg = intent.getStringExtra("Insert_successfully");
//
//            if (IsOwnReg.equals("Image_changed")) {
//
//
//
//                ArtistViewHolder.Signed.setImageResource(R.drawable.checkeddoc);
//
//
//            }
//        }
//    };



