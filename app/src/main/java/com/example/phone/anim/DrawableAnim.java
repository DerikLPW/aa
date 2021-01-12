package com.example.phone.anim;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class DrawableAnim extends Animation {
    private final View mView;
    private final Drawable mDrawable;

    public DrawableAnim(View view, Drawable drawable) {
        mView = view;
        mDrawable = drawable;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        try {
            int alpha = 255 - (int) (255 * interpolatedTime);
            if (mDrawable != null && mView != null) {
                if (alpha > 0) {
                    mDrawable.setAlpha(alpha);
                } else {
                    mDrawable.setAlpha(255);
                }
                mView.setBackground(mDrawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
