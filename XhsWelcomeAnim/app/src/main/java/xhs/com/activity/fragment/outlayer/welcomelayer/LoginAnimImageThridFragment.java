package xhs.com.activity.fragment.outlayer.welcomelayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import xhs.com.activity.R;
import xhs.com.activity.fragment.base.LoginAnimImageBaseFragment;

/**
 * 第三页动画
 */
public class LoginAnimImageThridFragment extends LoginAnimImageBaseFragment {

    private final int mScrollBitmapSpitHeight = 350;
    private final int mScrollBitmapNomalHeight = 1378;
    private final int mCareToTopHeight = 288;
    private final int mCareToLeftHeight = -10;

    private ImageView iv_scroll,iv_shield,iv_care;

    ObjectAnimator mCareObjectAnimator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_welcomanim_image_thrid, null);
        iv_scroll = (ImageView)view.findViewById(R.id.iv_scroll);
        iv_shield = (ImageView)view.findViewById(R.id.iv_shield);
        iv_care = (ImageView)view.findViewById(R.id.iv_care);

        Bitmap bmpScroll= BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_thrid_scrollbg);
        Bitmap bmpShield= BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_thrid_shield);
        mScrollBitmapHeight = mScrollBitmapNomalHeight;

        iv_shield.setScaleType(ImageView.ScaleType.MATRIX);
        iv_shield.setImageBitmap(bitmapScale(mImageViewWidth, bmpShield, BITMAP_SHIELD));
        iv_shield.setVisibility(View.GONE);

        iv_scroll.setScaleType(ImageView.ScaleType.MATRIX);
        iv_scroll.setImageBitmap(bitmapScale(mImageViewWidth, bmpScroll, BITMAP_SCROLL));


        Bitmap bmpCare= bitmapScale(BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_thrid_shield_care));
        iv_care.setScaleType(ImageView.ScaleType.MATRIX);
        iv_care.setImageBitmap(bmpCare);

        RelativeLayout.LayoutParams paramsGoods = (RelativeLayout.LayoutParams) iv_care.getLayoutParams();
        int goodsMarginTopHeigth = mNewScrollBitmapHeight * mCareToTopHeight / mScrollBitmapNomalHeight ;
        int goodsMarginLeft = mNewScrollBitmapHeight * mCareToLeftHeight / mScrollBitmapNomalHeight ;
        paramsGoods.topMargin = goodsMarginTopHeigth;
        paramsGoods.leftMargin = goodsMarginLeft;
        iv_care.setLayoutParams(paramsGoods);
        iv_care.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void playInAnim() {
        if(mAnimStartY < 0){
            mAnimStartY = ViewHelper.getY(iv_scroll);
        }
        ViewHelper.setY(iv_scroll, mAnimStartY);

        if(mObjectAnimator == null){
            mObjectAnimator = ObjectAnimator.ofFloat(iv_scroll, "y", mAnimStartY, mAnimStartY - mNewScrollBitmapHeight * mScrollBitmapSpitHeight / mScrollBitmapHeight);
            mObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }

                @Override
                public void onAnimationEnd(Animator animation) {
                    iv_shield.setVisibility(View.VISIBLE);
                    iv_care.setVisibility(View.VISIBLE);
                    if(mCareObjectAnimator == null){
                        mCareObjectAnimator = ObjectAnimator.ofInt(iv_care, "alpha", 0, 255);
                        mCareObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                        mCareObjectAnimator.setDuration(500);
                    }
                    if(mCareObjectAnimator.isRunning()){
                        mCareObjectAnimator.cancel();
                    }
                    mCareObjectAnimator.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) { }

                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
        }
        if(mAnimatorSet == null){
            mAnimatorSet = new AnimatorSet();
        }
        if(mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
        }

        iv_shield.setVisibility(View.GONE);
        iv_care.setVisibility(View.GONE);
        mAnimatorSet.play(mObjectAnimator);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    @Override
    public void playOutAnim() {

    }

    @Override
    public void reset() {
        if(mAnimatorSet != null){
            mAnimatorSet.cancel();
        }
        iv_care.setVisibility(View.GONE);
        iv_shield.setVisibility(View.GONE);
    }
}
