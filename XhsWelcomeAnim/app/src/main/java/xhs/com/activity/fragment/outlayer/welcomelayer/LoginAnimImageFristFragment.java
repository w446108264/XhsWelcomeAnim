package xhs.com.activity.fragment.outlayer.welcomelayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import xhs.com.activity.fragment.base.LoginAnimImageBaseFragment;
import xhs.com.utils.DisplayUtil;
import xhs.com.activity.R;

/**
 * 第一页动画
 */
public class LoginAnimImageFristFragment extends LoginAnimImageBaseFragment {

    private final int mScrollToBottomBitmapHeight = 864;
    private final int mScrollBitmapNomalHeight = 1286;
    private final int mScrollBitmapSpitHeight = 150;
    int mMarginTopHeigth;
    private ImageView iv_scroll,tv_bg;
    AnimatorSet mAnimatorSet;
    ObjectAnimator mObjectAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_welcomanim_image_frist, null);
        iv_scroll = (ImageView) view.findViewById(R.id.iv_scroll);
        tv_bg = (ImageView) view.findViewById(R.id.tv_bg);

        Bitmap bmpShield= BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_phone);
        Bitmap bmpScroll= BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_frist_scrollbg);
        mScrollBitmapHeight = mScrollBitmapNomalHeight;

        iv_scroll.setScaleType(ImageView.ScaleType.MATRIX);
        iv_scroll.setImageBitmap(bitmapScale(mImageViewWidth, bmpScroll, BITMAP_SCROLL));

        tv_bg.setScaleType(ImageView.ScaleType.MATRIX);
        tv_bg.setImageBitmap(bitmapScale(mImageViewWidth, bmpShield, BITMAP_SHIELD));

        scrollMarginTopHeigth = mNewScrollBitmapHeight * mScrollToBottomBitmapHeight / mScrollBitmapHeight ;

        RelativeLayout.LayoutParams paramsGoods = (RelativeLayout.LayoutParams) iv_scroll.getLayoutParams();
        mMarginTopHeigth = mNewScrollBitmapHeight * mScrollBitmapSpitHeight / mScrollBitmapNomalHeight - DisplayUtil.dip2px(getActivity(), 20);
        paramsGoods.topMargin = mMarginTopHeigth  ;
        iv_scroll.setLayoutParams(paramsGoods);
        return view;
    }
    int scrollMarginTopHeigth;
    @Override
    public void playInAnim() {
        if (mAnimStartY < 0) {
            mAnimStartY = ViewHelper.getY(iv_scroll);
        }

        if(mObjectAnimator == null){
            mObjectAnimator = ObjectAnimator.ofFloat(iv_scroll, "y", mMarginTopHeigth, -scrollMarginTopHeigth+mMarginTopHeigth);
        }
        if(mAnimatorSet == null){
            mAnimatorSet = new AnimatorSet();
        }

        if(mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
        }

        mAnimatorSet.play(mObjectAnimator);
        mAnimatorSet.setDuration(3000);
        mAnimatorSet.start();
    }

    @Override
    public void playOutAnim() { }

    @Override
    public void reset() {
        if(mAnimatorSet != null){
            mAnimatorSet.cancel();
        }
    }
}
