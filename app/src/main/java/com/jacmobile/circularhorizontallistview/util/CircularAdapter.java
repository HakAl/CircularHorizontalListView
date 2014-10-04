package com.jacmobile.circularhorizontallistview.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.circularhorizontallistview.R;

import java.util.ArrayList;

/**
 * Created by alex on 9/19/14.
 */
public class CircularAdapter extends BaseAdapter
{
    static final String GA_TAG = "CircularListAdapter";

    private Context context;
    private BitmapWorkerTask utilityManager;
    private ArrayList<String[]> data;
    private TypedArray cardImages;

    /**
     * Constructs a {@linkplain CircularAdapter}.
     */
    public CircularAdapter(Context context, ArrayList<String[]> data)
    {
        this.context = context;
        this.data = data;
        this.cardImages = context.getApplicationContext().getResources().obtainTypedArray(R.array.card_images);
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        position = position % data.size();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        Merchant merchantView = new Merchant();
        String[] merchant = data.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.merchant_slide, parent, false);
            merchantView.card = (ImageView) view.findViewById(R.id.img_home_merchant);
            merchantView.name = (TextView) view.findViewById(R.id.txt_home_merchant_name);
            merchantView.percent = (TextView) view.findViewById(R.id.txt_home_savings);
            view.setTag(merchantView);
        } else {
            merchantView = (Merchant) view.getTag();
        }
        float width = context.getResources().getDimension(R.dimen.card_image_width);
        float height = context.getResources().getDimension(R.dimen.card_image_height);
        if (BitmapWorkerTask.cancelPotentialWork(this.cardImages.getResourceId(position, R.drawable.ic_launcher), merchantView.card)) {
            final BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(merchantView.card, context.getResources(), (int)width, (int)height);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), null, bitmapWorkerTask);
            merchantView.card.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute(this.cardImages.getResourceId(position, R.drawable.ic_launcher));
        }
//        new BitmapWorkerTask(merchantView.card,
//                context.getResources(),
//                merchantView.card.getWidth(),
//                merchantView.card.getHeight()).execute();
        merchantView.name.setText(merchant[0]);
        merchantView.percent.setText(merchant[1] + "%");

//        this.utilityManager.(

//                this.utilityManager.registerRequestSettings(GA_TAG, "getMerchantImage"),
//                merchantView.card,
//                merchant.getLogoUrl(),
//                merchantView.card.getWidth(),
//                merchantView.card.getHeight());
//        merchantView.name.setText(merchant.getName());
//        merchantView.percent.setText(merchant.getStockLevels());
        return view;
    }

    private void getImageWidth()
    {

    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position % data.size());
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEmpty()
    {
        return data.size() > 0;
    }

    static class Merchant
    {
        ImageView card;
        TextView name;
        TextView percent;
    }
}
