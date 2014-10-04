package com.jacmobile.circularhorizontallistview.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.jacmobile.circularhorizontallistview.R;
import com.jacmobile.circularhorizontallistview.util.ActivityTitleController;
import com.jacmobile.circularhorizontallistview.util.CircularAdapter;
import com.jacmobile.circularhorizontallistview.util.HorizontalListView;

import java.util.ArrayList;

import javax.inject.Inject;


public class ListActivity extends BaseActivity
{
    private static final String TAG = "ListActivity";

    @Inject
    ActivityTitleController titleController;
    @Inject
    LayoutInflater layoutInflater;

    private HorizontalListView listView;
    private ViewGroup layoutParent;
    private CircularAdapter adapter;

    @Override
    protected void onStart()
    {
        super.onStart();
        this.setContentView(R.layout.activity_base);
        this.layoutParent = (ViewGroup) this.findViewById(R.id.base_root);
        this.titleController.setTitle("TEST");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.layoutParent = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private void initHorizontalListView()
    {
        this.listView = (HorizontalListView) this.findViewById(R.id.base_list);
        this.adapter = new CircularAdapter(this, getData());
        this.listView.setAdapter(this.adapter);
        listView.setSelection(5001);
    }

    private ArrayList<String[]> getData()
    {
        ArrayList<String[]> result = new ArrayList<String[]>();
        String[] titles = getResources().getStringArray(R.array.card_titles);
        for (int i = 0; i < titles.length; i++) {
            String[] temp = new String[2];
            temp[0] = titles[i];
            temp[1] = String.valueOf(i + 1 * 2);
            result.add(temp);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_horizontallistview:
                this.initHorizontalListView();
                break;
            case R.id.action_fancycoverflow:
                this.initHorizontalListView();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the layout that appears behind the navigation drawer
     *
     * @param layoutId
     */
    protected void setLayout(int layoutId)
    {
        this.layoutInflater.inflate(layoutId, this.layoutParent, true);
    }
}
