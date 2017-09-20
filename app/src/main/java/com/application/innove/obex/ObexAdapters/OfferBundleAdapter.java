package com.application.innove.obex.ObexAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.innove.obex.ObexModel.ImageCapturemodel;
import com.application.innove.obex.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhisheksharma on 17-Aug-2017.
 */

  public  class OfferBundleAdapter extends RecyclerView.Adapter<OfferBundleAdapter.offerviewholder> {

    Context ctx;
    private List<File> imagesFiles;



    public OfferBundleAdapter(Context ctx, ArrayList<File> photos) {
        this.ctx = ctx;
        this.imagesFiles = photos;
    }

    @Override
    public int getItemCount() {
        return imagesFiles.size();
    }

    @Override
    public offerviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        return new offerviewholder(inflater.inflate(R.layout.image_view_horizontal, parent, false));

    }

    @Override
    public void onBindViewHolder(offerviewholder holder, final int position) {

        Picasso.with(ctx)
                .load(imagesFiles.get(position))
                .fit()
                .centerCrop()
                .into(holder.imageviewset);

    }


    class offerviewholder extends RecyclerView.ViewHolder {
        public ImageView imageviewset;

        public offerviewholder(View itemView) {
            super(itemView);
            imageviewset  = (ImageView) itemView.findViewById(R.id.image_view_set);
        }
    }
}
