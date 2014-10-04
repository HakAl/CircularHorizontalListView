package com.jacmobile.circularhorizontallistview;

import android.app.Application;

import com.jacmobile.circularhorizontallistview.injection.AndroidModule;
import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by alex on 9/21/14.
 */
public class ListApplication extends Application
{
    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}