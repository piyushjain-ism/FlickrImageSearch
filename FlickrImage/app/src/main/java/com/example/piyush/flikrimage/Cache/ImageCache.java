package com.example.piyush.flikrimage.Cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


public class ImageCache {

    private LruCache<String, Bitmap> cacheImage;

    private static ImageCache cache;

    public static ImageCache getInstance()
    {
        if(cache == null)
        {
            cache = new ImageCache();
        }

        return cache;
    }

    public void initializeCache()
    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);

        final int cacheSize = maxMemory / 8;

        System.out.println("cache size = "+cacheSize);

        cacheImage = new LruCache<String, Bitmap>(cacheSize);

    }

    public void addImageToWarehouse(String key, Bitmap value)
    {
        if(cacheImage != null && cacheImage.get(key) == null)
        {
            cacheImage.put(key, value);
        }
    }

    public Bitmap getImageFromWarehouse(String key)
    {
        if(key != null)
        {
            return cacheImage.get(key);
        }
        else
        {
            return null;
        }
    }

    public void removeImageFromWarehouse(String key)
    {
        cacheImage.remove(key);
    }



}
