package com.application.innove.obex.Other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.innove.obex.ObexAdapters.DocumentCategoryAdapter;
import com.application.innove.obex.ObexModel.Document;
import com.application.innove.obex.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;


public class ArtistViewHolder
        extends ChildViewHolder implements View.OnClickListener {

    public TextView DocTextView;
    public ImageView uploaddocuments, Downloaddocuments, DeleteDocuments;
    public  ImageView Signed;

  //  public static final String ACTION_DO_IMAGE_CHANGED_AFTER_IMAGE_UPLOAD = "Image_changed_to_signed_image";


    public ArtistViewHolder(View itemView) {
        super(itemView);
        Context context = itemView.getContext();
        DocTextView = (TextView) itemView.findViewById(R.id.DocumentListupload);
        Signed = (ImageView) itemView.findViewById(R.id.Signed);
        uploaddocuments = (ImageView) itemView.findViewById(R.id.upload);
        Downloaddocuments = (ImageView) itemView.findViewById(R.id.DownloadDoc);
        DeleteDocuments = (ImageView) itemView.findViewById(R.id.DeleteDoc);
    }

//    public void bind(final Document item, final DocumentCategoryAdapter.OnItemClickListener listener)
//    {
//
//        itemView.setOnClickListener(new View.OnClickListener()
//                                    {
//                                        @Override
//                                        public void onClick(View v)
//                                        {
//                                            listener.onItemClick(item);
//                                        }
//                                    }
//        );
//    }

//        IntentFilter filter = new IntentFilter(
//                ACTION_DO_IMAGE_CHANGED_AFTER_IMAGE_UPLOAD);
//
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//
//        context.registerReceiver(SyncServiceCompleatedBroadcastReceiver,
//                filter);




    public void setArtistName(String artistName) {
        DocTextView.setText(artistName);
    }


    @Override
    public void onClick(View view) {


    }

//    private BroadcastReceiver SyncServiceCompleatedBroadcastReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//
//            final String IsOwnReg = intent.getStringExtra("IsImageUploaded");
//
//            if (IsOwnReg.equals("YesImageUploaded")) {
//
//                Signed.setImageResource(R.drawable.checkeddoc);
//
//            }
//        }
//    };
}
