package xhs.com.activity.fragment.outlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import xhs.com.activity.R;
import xhs.com.activity.fragment.outlayer.welcomelayer.LoginAnimImageFristFragment;
import xhs.com.activity.fragment.outlayer.welcomelayer.LoginAnimImageSecondFragment;
import xhs.com.activity.fragment.outlayer.welcomelayer.LoginAnimImageThridFragment;
import xhs.com.activity.fragment.base.LoginAnimImageBaseFragment;
import xhs.com.adapter.ImageFragmentStatePagerAdapter;
import xhs.com.adapter.TextFragmentStatePagerAdapter;
import xhs.com.bean.TextBean;
import xhs.com.view.ChildViewPager;
import xhs.com.view.WelcomeIndicator;

/**
 * 动画层
 */
public class WelcomAnimFragment extends Fragment {

    public ChildViewPager imageViewPager ;
    public ChildViewPager textViewPager ;
    public WelcomeIndicator view_indicator ;
    public TextView tv_skip ;
    public RelativeLayout rl_indicator;

    public int mOldPosition = -1;
    public boolean mIsMoveParent = false;
    private boolean mFristPageSuperLock = false;
    ImageFragmentStatePagerAdapter mImageFragmentStatePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_welcomanim, null);

        imageViewPager = (ChildViewPager) view.findViewById(R.id.vp_imageanim);
        textViewPager = (ChildViewPager) view.findViewById(R.id.vp_textanim);
        view_indicator = (WelcomeIndicator) view.findViewById(R.id.view_indicator);
        tv_skip = (TextView) view.findViewById(R.id.tv_skip);
        rl_indicator = (RelativeLayout) view.findViewById(R.id.rl_indicator);

        initImageFragmentViewPager();
        initTextFragmentViewPager();
        return view;
    }


    private void initImageFragmentViewPager(){
        imageViewPager.setOffscreenPageLimit(3);
        mImageFragmentStatePagerAdapter = new ImageFragmentStatePagerAdapter(getChildFragmentManager());
        imageViewPager.setAdapter(mImageFragmentStatePagerAdapter);
        imageViewPager.mIsLockScoll = true;

        imageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) { }

            @Override
            public void onPageSelected(int i) {
                if(i == imageViewPager.getAdapter().getCount() - 1){
                    mIsMoveParent = true;
                }
                else{
                    mIsMoveParent = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
    }

    /**
     * 初始化文字动画数据
     */
    private void initTextFragmentViewPager(){
        ArrayList<TextBean> mTextBeans = new ArrayList<TextBean>();
        TextBean bean0 = new TextBean();
        bean0.mId = 0;
        bean0.mTitle = "欢迎来到小红书!";
        bean0.mContent = "在开始您小红书的旅途之前.一起来了解一下小红书购物笔记的几个特色吧";
        mTextBeans.add(bean0);

        TextBean bean1 = new TextBean();
        bean1.mId = 1;
        bean1.mTitle = "购物笔记";
        bean1.mContent = "精选达人笔记,以及所有关注的笔记,尽收眼底.";
        mTextBeans.add(bean1);

        TextBean bean2 = new TextBean();
        bean2.mId = 2;
        bean2.mTitle = "福利社";
        bean2.mContent = "在这里,全世界的好东西都出触手可及!只需轻轻一点,将长草的好物收入囊中.";
        mTextBeans.add(bean2);

        TextBean bean3 = new TextBean();
        bean3.mId = 3;
        bean3.mTitle = "心愿单!";
        bean3.mContent = "你可以将其他人的笔记收藏起来啦!不仅如此,还可以订阅达人的心愿单.";
        mTextBeans.add(bean3);

        TextFragmentStatePagerAdapter adapterText = new TextFragmentStatePagerAdapter(getChildFragmentManager(),mTextBeans);
        textViewPager.setAdapter(adapterText);
        view_indicator.init(mTextBeans.size());

        /**
         * 文字动画层滑动监听
         */
        textViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) { }

            @Override
            public void onPageSelected(int p) {
                if(mOldPosition < 0){
                    mOldPosition = 0;
                }
                view_indicator.play(mOldPosition,p);

                /**
                 * 上层图片动画的viewpager有3个子fragment
                 * 下层文字动画的viewpager有4个子fragment
                 *        0   1   2  -->  图片动画
                 *   0   1   2   3  -->  文字动画
                 */
                if(p == 0){
                    imageViewPager.mIsLockScoll = true;
                    if(mOldPosition == 1){
                        mFristPageSuperLock = true;
                    }
                }
                else if(p == 1){
                    imageViewPager.mIsLockScoll = false;
                    if(mFristPageSuperLock){
                        mFristPageSuperLock = false;
                    }
                    else{
                        reset();
                        LoginAnimImageFristFragment fragment = (LoginAnimImageFristFragment)mImageFragmentStatePagerAdapter.getFragement(0);
                        fragment.playInAnim();
                    }
                }
                else if(p == 2){
                    imageViewPager.mIsLockScoll = false;
                    reset();
                    LoginAnimImageSecondFragment fragment = (LoginAnimImageSecondFragment)mImageFragmentStatePagerAdapter.getFragement(1);
                    fragment.playInAnim();
                }
                else if(p == 3){
                    imageViewPager.mIsLockScoll = false;
                    reset();
                    LoginAnimImageThridFragment fragment = (LoginAnimImageThridFragment)mImageFragmentStatePagerAdapter.getFragement(2);
                    fragment.playInAnim();
                }
                else{
                    imageViewPager.mIsLockScoll = false;
                    reset();
                }
                mOldPosition = p;
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
    }

    /**
     * 非当前展示动画页,停止动画,回复初始状态
     */
    public void reset(){
        if(mOldPosition > 0){
            LoginAnimImageBaseFragment fragment = (LoginAnimImageBaseFragment)mImageFragmentStatePagerAdapter.getFragement(mOldPosition - 1);
            fragment.reset();
        }
    }

    /**
     * 跳过监听
     */
    public WelcomAnimFragmentInterface mWelcomAnimFragmentInterface;
    public void setWelcomAnimFragmentInterface(WelcomAnimFragmentInterface mInterface){
        this.mWelcomAnimFragmentInterface = mInterface;
    }
    public interface WelcomAnimFragmentInterface{
        void onSkip();
    }
}
