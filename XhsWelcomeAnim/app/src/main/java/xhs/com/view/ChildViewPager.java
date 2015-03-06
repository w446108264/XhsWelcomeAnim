package xhs.com.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 底层ViewPager所有事件由父层ViewPager手动分发
 *
 */

public class ChildViewPager extends ViewPager {
    public boolean mIsLockScoll = false;
    public ChildViewPager(Context context) {
        this(context, null);
    }
    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
}
