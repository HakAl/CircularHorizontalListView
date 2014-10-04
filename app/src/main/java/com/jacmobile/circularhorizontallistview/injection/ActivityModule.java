package com.jacmobile.circularhorizontallistview.injection;

import android.content.Context;
import android.view.LayoutInflater;

import com.jacmobile.circularhorizontallistview.activities.BaseActivity;
import com.jacmobile.circularhorizontallistview.activities.ListActivity;
import com.jacmobile.circularhorizontallistview.util.ActivityTitleController;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
        injects = {
                ListActivity.class,
//                HomeFragment.class
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ActivityModule
{
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity)
    {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext()
    {
        return activity;
    }

    @Provides
    @Singleton
    ActivityTitleController provideTitleController()
    {
        return new ActivityTitleController(activity);
    }

    @Provides
    @Singleton
    LayoutInflater proviceLayoutInflater()
    {
        return (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
