package com.example.piyush.flikrimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.piyush.flikrimage.Cache.ImageCache;

public class DisplayImageLoader {

    Context mContext;

    public DisplayImageLoader(Context context) {
        mContext = context;
    }

    public void DisplayImage(final ImageCache cache, final String url, final ImageView imageView, int img_id) {

        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(70,70) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        cache.addImageToWarehouse(url, resource);
                        imageView.setImageBitmap(resource);
                    }
                });

    }
}
