package xhs.com.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

import xhs.com.utils.DisplayUtil;
import xhs.com.activity.R;

/**
 * 滑动指示器
 * @author Zhongdaxia
 *
 */

public class WelcomeIndicator extends LinearLayout{

    Context mContext;
    ArrayList<ImageView> mImageViews ;

    int heightSelect;
    Bitmap bmpSelect;
    Bitmap bmpNomal;

    AnimatorSet mOutAnimatorSet;
    AnimatorSet mInAnimatorSet;

    public WelcomeIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.setOrientation(HORIZONTAL);

        heightSelect = DisplayUtil.dip2px(mContext, 24);
        bmpSelect= BitmapFactory.decodeResource(getResources(), R.drawable.welcome_indicator_point_select);
        bmpNomal= BitmapFactory.decodeResource(getResources(), R.drawable.welcome_indicator_point_nomal);
    }

    public void init(int count){
        mImageViews = new ArrayList<ImageView>();
        for(int i = 0 ; i < count ; i++){
            RelativeLayout rl = new RelativeLayout(mContext);
            LayoutParams params = new LayoutParams(heightSelect,heightSelect);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            ImageView imageView = new ImageView(mContext);

            if(i == 0){
                imageView.setImageBitmap(bmpSelect);
                rl.addView(imageView, layoutParams);
            }
            else{
                imageView.setImageBitmap(bmpNomal);
                rl.addView(imageView, layoutParams);
            }
            this.addView(rl, params);
            mImageViews.add(imageView);
        }
    }

    public void play(int startPosition,int nextPosition){
        if(startPosition < 0 || nextPosition < 0 || nextPosition == startPosition){
            return;
        }

        final ImageView imageViewStrat = mImageViews.get(startPosition);
        final ImageView imageViewNext = mImageViews.get(nextPosition);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f, 0.25f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f, 0.25f);

        if(mOutAnimatorSet != null && mOutAnimatorSet.isRunning()){
            mOutAnimatorSet.cancel();
            mOutAnimatorSet = null;
        }
        mOutAnimatorSet = new AnimatorSet();
        mOutAnimatorSet.play(anim1).with(anim2);
        mOutAnimatorSet.setDuration(100);

        ObjectAnimator animIn1 = ObjectAnimator.ofFloat(imageViewNext, "scaleX", 0.25f, 1.0f);
        ObjectAnimator animIn2 = ObjectAnimator.ofFloat(imageViewNext, "scaleY", 0.25f, 1.0f);

        if(mInAnimatorSet != null && mInAnimatorSet.isRunning()){
            mInAnimatorSet.cancel();
            mInAnimatorSet = null;
        }
        mInAnimatorSet = new AnimatorSet();
        mInAnimatorSet.play(animIn1).with(animIn2);
        mInAnimatorSet.setDuration(100);

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewStrat.setImageBitmap(bmpNomal);
                ObjectAnimator animFill1 = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f);
                ObjectAnimator animFill2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f);
                AnimatorSet mFillAnimatorSet = new AnimatorSet();
                mFillAnimatorSet.play(animFill1).with(animFill2);
                mFillAnimatorSet.start();
                imageViewNext.setImageBitmap(bmpSelect);
                mInAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        mOutAnimatorSet.start();
    }
}
