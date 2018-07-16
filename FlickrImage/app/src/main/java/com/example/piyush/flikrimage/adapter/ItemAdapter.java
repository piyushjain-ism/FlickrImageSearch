package com.example.piyush.flikrimage.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.piyush.flikrimage.Cache.ImageCache;
import com.example.piyush.flikrimage.R;
import com.example.piyush.flikrimage.imageloader.DisplayImageLoader;
import com.example.piyush.flikrimage.pojo.ImagePojo;

import java.util.ArrayList;

/**
 * Created by piyushjain on 03/04/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    ArrayList<ImagePojo> imagePojos;
    ImageCache cache;

    public ItemAdapter(Context context, ArrayList<ImagePojo> imagePojos, ImageCache cache) {
        this.context = context;
        activity = (Activity)context;
        this.imagePojos = imagePojos;
        this.cache = cache;
        }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImage;
        public MyViewHolder(View view) {
            super(view);

            mImage = view.findViewById(R.id.img);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ImagePojo imagePojo = imagePojos.get(position);
        String img = "your_image_url_here";

        Bitmap bm = cache.getImageFromWarehouse(img);

        if(bm != null)
        {
            holder.mImage.setImageBitmap(bm);
        }
        else
        {
            holder.mImage.setImageBitmap(null);
            new DisplayImageLoader(context).DisplayImage(cache,"http://farm"+imagePojo.getFarm()+".static.flickr.com/"+imagePojo.getServer()+"/"+imagePojo.getId()+"_"+imagePojo.getSecret()+".jpg", holder.mImage, R.mipmap.ic_launcher);
        }

    }

    @Override
    public int getItemCount() {
        if(imagePojos!=null)
        return imagePojos.size();
        else
            return 0;
    }

}
