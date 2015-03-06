package xhs.com.activity.fragment.base;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import xhs.com.utils.DisplayUtil;

/**
 * 上层图片滚动fragment基类
 */
public abstract class LoginAnimImageBaseFragment extends Fragment {

    public final int BITMAP_SCROLL = 11;
    public final int BITMAP_SHIELD = 12;

    public float mAnimStartY = -1;
    public int mScrollBitmapHeight = 0;
    public int mNewScrollBitmapHeight = 0;
    public int mImageViewWidth = 0;

    public AnimatorSet mAnimatorSet;
    public ObjectAnimator mObjectAnimator;

    float mScaleWidth = 0;
    float mScaleHeight = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int parentViewWidth =DisplayUtil.getDisplayWidthPixels(getActivity()) - DisplayUtil.dip2px(getActivity(), 40);
        mImageViewWidth = parentViewWidth - DisplayUtil.dip2px(getActivity(),60);
    }

    public Bitmap bitmapScale(int ivWidth,Bitmap bitmap,int type){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = ivWidth;
        int newHeight = (int) ((float)height) * newWidth / width;

        if(type == BITMAP_SCROLL){
            mNewScrollBitmapHeight = newHeight;
        }

        mScaleWidth = ((float) newWidth) / width;
        mScaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

    public Bitmap bitmapScale(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

    public void playInAnim(){};
    public void playOutAnim(){};
    public void reset(){};
}
