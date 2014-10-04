package com.jacmobile.circularhorizontallistview.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by acorll on 7/24/2014.
 */
public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>
{
    public int resId = 0;
    private int reqWidth;
    private int reqHeight;
    private Resources resources;
    private final WeakReference<ImageView> imageViewReference;

    public BitmapWorkerTask(final ImageView imageView, final Resources resources, final int reqWidth, final int reqHeight)
    {
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        this.resources = resources;
        this.imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Integer... params)
    {
        this.resId = params[0];
        return this.decodeSampledBitmap(resources, resId, reqWidth, reqHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final AsyncTask<Integer, Void, Bitmap> bitmapWorkerTask =
                    getBitmapWorker(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
                imageView.invalidate();
            }
        }
    }

    private static BitmapWorkerTask getBitmapWorker(ImageView imageView)
    {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView)
    {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorker(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.resId;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == 0 || bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        return true;
    }

    private static Bitmap decodeSampledBitmap(Resources res, int resId, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
