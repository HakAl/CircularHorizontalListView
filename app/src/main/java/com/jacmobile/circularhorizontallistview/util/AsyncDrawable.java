package com.jacmobile.circularhorizontallistview.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by dgaffey on 7/28/14.
 */
public class AsyncDrawable extends BitmapDrawable
{
    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask)
    {
        super(res, bitmap);
        this.bitmapWorkerTaskReference = new WeakReference<>(bitmapWorkerTask);
    }

    public BitmapWorkerTask getBitmapWorkerTask()
    {
        return this.bitmapWorkerTaskReference.get();
    }
}
