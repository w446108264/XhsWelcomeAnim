package xhs.com.activity.fragment.outlayer.welcomelayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import xhs.com.activity.fragment.base.LoginAnimImageBaseFragment;
import xhs.com.utils.DisplayUtil;
import xhs.com.activity.R;

/**
 * 第二页动画
 */
public class LoginAnimImageSecondFragment extends LoginAnimImageBaseFragment {

    private final int DURATION_GOODS = 1000;

    private final int mScrollBitmapGoodsY = 442;
    private final int mScrollBitmapGoodsX = 30;
    private final int mScrollBitmapSpitHeight = 156;
    private final int mScrollBitmapNomalHeight = 1056;
    private final int mGoodsBitmapNomalHeight = 198;

    private ImageView iv_scroll, iv_shield, iv_goods, iv_buy;

    Bitmap bmpBuysIng, bmpBuysed;

    android.view.animation.AnimationSet animatorSetGoods;
    Animation scaleAnimation, rotateAnimation, translateAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_welcomanim_image_second, null);
        iv_scroll = (ImageView) view.findViewById(R.id.iv_scroll);
        iv_shield = (ImageView) view.findViewById(R.id.iv_shield);
        iv_buy = (ImageView) view.findViewById(R.id.iv_buy);
        iv_goods = (ImageView) view.findViewById(R.id.iv_goods);

        Bitmap bmpScroll = BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_second_scrollbg);
        Bitmap bmpShield = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_shield);
        Bitmap bmpGoods = BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_second_goods);

        mScrollBitmapHeight = mScrollBitmapNomalHeight;

        iv_shield.setScaleType(ImageView.ScaleType.MATRIX);
        iv_shield.setImageBitmap(bitmapScale(mImageViewWidth, bmpShield, BITMAP_SHIELD));
        iv_shield.setVisibility(View.GONE);

        iv_scroll.setScaleType(ImageView.ScaleType.MATRIX);
        Bitmap bitmap = bitmapScale(mImageViewWidth, bmpScroll, BITMAP_SCROLL);
        iv_scroll.setImageBitmap(bitmap);

        iv_goods.setImageBitmap(bitmapScale((int) ((float) (bitmap.getHeight() * mGoodsBitmapNomalHeight) / mScrollBitmapNomalHeight), bmpGoods, BITMAP_SHIELD));
        iv_goods.setVisibility(View.GONE);

        bmpBuysIng = bitmapScale(BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_second_buging));
        bmpBuysed = bitmapScale(BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_second_bugged));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_buy.getLayoutParams();
        int ivBuyMargin = DisplayUtil.dip2px(getActivity(), 50);
        params.rightMargin = ivBuyMargin;
        iv_buy.setImageBitmap(bmpBuysed);
        iv_buy.setLayoutParams(params);

        RelativeLayout.LayoutParams paramsGoods = (RelativeLayout.LayoutParams) iv_goods.getLayoutParams();
        int goodsMarginTopHeigth = mNewScrollBitmapHeight * mScrollBitmapGoodsY / mScrollBitmapNomalHeight;
        int goodsMarginLeftHeigth = mNewScrollBitmapHeight * mScrollBitmapGoodsX / mScrollBitmapNomalHeight;
        paramsGoods.topMargin = goodsMarginTopHeigth;
        paramsGoods.leftMargin = goodsMarginLeftHeigth;
        iv_goods.setLayoutParams(paramsGoods);
        return view;
    }

    @Override
    public void playInAnim() {
        if (mAnimStartY < 0) {
            mAnimStartY = ViewHelper.getY(iv_scroll);
        }
        ViewHelper.setY(iv_scroll, mAnimStartY);

        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(iv_scroll, "y", mAnimStartY, mAnimStartY - mNewScrollBitmapHeight * mScrollBitmapSpitHeight / mScrollBitmapHeight);
            mObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    iv_shield.setVisibility(View.VISIBLE);
                    iv_buy.setVisibility(View.VISIBLE);
                    iv_goods.setVisibility(View.VISIBLE);
                    iv_buy.setImageBitmap(bmpBuysIng);

                    iv_shield.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv_shield.setVisibility(View.VISIBLE);
                            iv_buy.setVisibility(View.VISIBLE);
                            iv_goods.setVisibility(View.VISIBLE);

                            float goodsX = ViewHelper.getX(iv_goods);
                            float goodsY = ViewHelper.getY(iv_goods);
                            float buyX = ViewHelper.getX(iv_buy);
                            float buyY = ViewHelper.getY(iv_buy);

                            float scale = (float) iv_buy.getHeight() / iv_goods.getHeight();

                            if (scaleAnimation == null) {
                                scaleAnimation = new ScaleAnimation(1.0f, scale, 1.0f, scale);
                            }

                            if (rotateAnimation == null) {
                                rotateAnimation = new RotateAnimation(0f, 180f,
                                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            }

                            if (translateAnimation == null) {
                                translateAnimation = new TranslateAnimation(
                                        Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE, buyX - goodsX ,
                                        Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE, buyY - goodsY);
                            }

                            if (animatorSetGoods == null) {
                                animatorSetGoods = new android.view.animation.AnimationSet(true);
                                animatorSetGoods.setDuration(DURATION_GOODS);
                                animatorSetGoods.addAnimation(rotateAnimation);
                                animatorSetGoods.addAnimation(scaleAnimation);
                                animatorSetGoods.addAnimation(translateAnimation);
                                animatorSetGoods.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        mIsAnimatorSetGoodsStart = true;
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        iv_buy.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                iv_buy.setImageBitmap(bmpBuysed);
                                            }
                                        }, 200);
                                        mIsAnimatorSetGoodsStart = false;
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }

                                });
                            } else {
                                animatorSetGoods.cancel();
                            }
                            iv_buy.setVisibility(View.VISIBLE);
                            iv_goods.setVisibility(View.VISIBLE);
                            iv_goods.startAnimation(animatorSetGoods);
                        }
                    },400);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }

        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        iv_shield.setVisibility(View.GONE);
        iv_goods.setVisibility(View.GONE);
        iv_buy.setVisibility(View.GONE);
        iv_goods.clearAnimation();
        iv_buy.setImageBitmap(bmpBuysIng);
        mAnimatorSet.play(mObjectAnimator);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    @Override
    public void playOutAnim() {
    }

    boolean mIsAnimatorSetGoodsStart = false;

    @Override
    public void reset() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        if (animatorSetGoods != null) {
            animatorSetGoods.cancel();
            iv_goods.clearAnimation();
        }

        iv_shield.setVisibility(View.GONE);
        iv_goods.setVisibility(View.GONE);
        iv_buy.setVisibility(View.GONE);
        iv_buy.setImageBitmap(bmpBuysIng);
    }
}
