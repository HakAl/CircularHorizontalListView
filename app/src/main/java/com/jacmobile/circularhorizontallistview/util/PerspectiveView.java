package com.jacmobile.circularhorizontallistview.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by acorll on 6/9/2014.
 */
public class PerspectiveView extends HorizontalListView
{
//    Adjustable scale factor
    private static final float SCALE_FACTOR = 0.5f;
//    Anchor point for transformation
    private static final float X_ANCHOR = 0.5f;
    private static final float Y_ANCHOR = 0.75f;

    public PerspectiveView(Context context)
    {
        super(context);
        init();
    }

    public PerspectiveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * enable static transformations for each child
     */
    private void init()
    {
        setStaticTransformationsEnabled(true);
    }

    /**
     * Calculate the current position of a view in screen coordinates
     * @param view  the view to calculate center of
     * @return horizontal center of view
     */
    private int getViewCenter(View view)
    {
        int[] childCoords = new int[2];
        view.getLocationOnScreen(childCoords);
        return childCoords[0] +(view.getWidth() / 2);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t)
    {
        HorizontalScrollView hScrollView = null;
        if (getParent() instanceof HorizontalScrollView) {
            hScrollView = (HorizontalScrollView) getParent();
            if (hScrollView == null) {
                return false;
            }
        }
        int childCenter = getViewCenter(child);
        int viewCenter = getViewCenter(hScrollView);

//        Calculate the difference between the child's center and parent's
        float delta = Math.min(1.0f, Math.abs(childCenter - viewCenter) / (float) viewCenter);
//        Set minimum scale factor
        float scale = Math.max(0.4f, 1.0f - (SCALE_FACTOR * delta));
        float xTrans = child.getWidth() * X_ANCHOR;
        float yTrans = child.getWidth() * Y_ANCHOR;

        t.clear();
//        Set the transformation for the child view
        t.getMatrix().setScale(scale, scale, xTrans, yTrans);
        child.invalidate();

        return true;
    }
}
