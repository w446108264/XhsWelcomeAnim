package xhs.com.activity.fragment.outlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import xhs.com.activity.fragment.outlayer.loginlayer.LoginFragment;
import xhs.com.activity.fragment.outlayer.loginlayer.RegisterFragment;
import xhs.com.utils.DisplayUtil;
import xhs.com.activity.R;

/**
 * 登录层
 */
public class LoginAnimFragment extends Fragment {

    RelativeLayout rl_parent;
    Fragment mLoginFragment, mRegisterFragment;
    ViewPager mViewPager;
    private TextView mLoginText, mRegisterText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_welcomanim_login, null);
        rl_parent = (RelativeLayout)view.findViewById(R.id.rl_parent);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_fragment);
        mLoginFragment = new LoginFragment();
        mRegisterFragment = new RegisterFragment();
        mLoginText = (TextView) view.findViewById(R.id.tv_Login);
        mRegisterText = (TextView) view.findViewById(R.id.tv_reg);
        mRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mViewPager.setAdapter(new ContainerAdapter(getFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    mLoginText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_triangle);
                    mRegisterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mLoginText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mRegisterText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_triangle);
                }
            }
        });
        return view;
    }

    public void playInAnim(){
        rl_parent.setVisibility(View.VISIBLE);

        AnimatorSet mAnimatorSet;
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(rl_parent,
                "y", DisplayUtil.getDisplayheightPixels(getActivity()), DisplayUtil.dip2px(getActivity(), 160));

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim3);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    public class ContainerAdapter extends FragmentPagerAdapter {

        public ContainerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mLoginFragment;
                case 1:
                    return mRegisterFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
