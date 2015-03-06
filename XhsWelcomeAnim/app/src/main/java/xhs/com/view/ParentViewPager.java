package xhs.com.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import xhs.com.utils.DisplayUtil;
import xhs.com.activity.fragment.outlayer.WelcomAnimFragment;


/**
 * 顶层ViewPager,包含2个fragment childview
 * 处于第一个fragment,所有事件将被拦截在本层View,手动分发到指定子View
 * 处于第二个fragment,释放拦截
 * @author Zhongdaxia
 */

public class ParentViewPager extends ViewPager {

    public static boolean mLoginPageLock = false;

    /**
     * 跳过按钮相关
     */
    private Boolean mSkipFlag = true;
    private float mCx,mCy;
    private int[] mTvSkipLocation;
    private int margin, left, top, right, bottom;

    private WelcomAnimFragment mWelcomAnimFragment;

    public ParentViewPager(Context context) {
        this(context, null);
    }

    public ParentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWelcomAnimFragment(WelcomAnimFragment welcomAnimFragment) {
        this.mWelcomAnimFragment = welcomAnimFragment;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mLoginPageLock) {
            requestDisallowInterceptTouchEvent(true);
            return super.onInterceptTouchEvent(ev);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.getCurrentItem() == 1 || mLoginPageLock) {
            return true;
        }
        if (mWelcomAnimFragment != null) {
            mCx = ev.getX();
            mCy = ev.getY();

            /**
             * 由于子View被拦截,这里通过计算跳过按钮的坐标手动处理跳过click事件
             */
            if (mTvSkipLocation == null) {
                mTvSkipLocation = new int[2];
                mWelcomAnimFragment.tv_skip.getLocationOnScreen(mTvSkipLocation);
            }
            if (left == 0) {
                margin = DisplayUtil.dip2px(getContext(), 10);
                left = mTvSkipLocation[0] - margin;
                top = mTvSkipLocation[1] - margin;
                right = mTvSkipLocation[0] + mWelcomAnimFragment.tv_skip.getWidth() + margin;
                bottom = mTvSkipLocation[1] + mWelcomAnimFragment.tv_skip.getHeight() + margin;
            }
            if (mCx - left > 0 && right - mCx > 0 && mCy - top > 0 && bottom - mCy > 0 && !mLoginPageLock) {
            } else {
                mSkipFlag = false;
            }
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mCx - left > 0 && right - mCx > 0 && mCy - top > 0 && bottom - mCy > 0 && !mLoginPageLock && mSkipFlag) {
                    if (mWelcomAnimFragment.mWelcomAnimFragmentInterface != null) {
                        mWelcomAnimFragment.mWelcomAnimFragmentInterface.onSkip();
                    }
                }
                mSkipFlag = true;
                mCx = 0;
                mCy = 0;
            }

            /**
             * touch事件由顶层viewpager捕捉,手动分发到两个子viewpager
             */
            if (mWelcomAnimFragment.imageViewPager != null && !mWelcomAnimFragment.imageViewPager.mIsLockScoll) {
                mWelcomAnimFragment.imageViewPager.onTouchEvent(ev);
            }
            if (mWelcomAnimFragment.textViewPager != null) {
                mWelcomAnimFragment.textViewPager.onTouchEvent(ev);
            }
            if (mWelcomAnimFragment.mIsMoveParent) {
                return super.onTouchEvent(ev);
            }
        }
        return true;
    }
}
