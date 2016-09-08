package com.myxh.coolshopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.ui.base.BaseFragment;

/**
 * Created by asus on 2016/8/27.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private SimpleDraweeView mLoginIvHead;
    private TextView mLoginTvUsername;
    private ImageView mLoginIvLevel;
    private TextView mLoginTvBalance;
    private ImageView mLoginIvArrowRight;
    private RelativeLayout mLoginLayout;
    private Button mNologinBtnLogin;
    private LinearLayout mNologinLayout;
    private TextView mUserTvCart;
    private TextView mUserTvFavorite;
    private TextView mUserTvHistory;
    private TextView mItemUnpaidTvCount;
    private RelativeLayout mItemUnpaidLayout;
    private TextView mItemPaidTvCount;
    private TextView mItemPaidTvUncommentCount;
    private RelativeLayout mItemPaidOrderLayout;
    private TextView mItemLotteryTvCount;
    private RelativeLayout mItemLotteryLayout;
    private TextView mItemTreasureTvCount;
    private RelativeLayout mItemTreasureLayout;
    private LinearLayout mUserOrderLayout;
    private TextView mItemBankcardTvComplete;
    private RelativeLayout mItemBankcardLayout;
    private TextView mItemCouponsTvCount;
    private RelativeLayout mItemCouponsLayout;
    private TextView mItemRecommendTvNew;
    private RelativeLayout mItemRecommendLayout;
    private RelativeLayout mItemQrLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoginIvHead = (SimpleDraweeView) view.findViewById(R.id.me_login_iv_head);
        mLoginTvUsername = (TextView) view.findViewById(R.id.me_login_tv_username);
        mLoginIvLevel = (ImageView) view.findViewById(R.id.me_login_iv_level);
        mLoginTvBalance = (TextView) view.findViewById(R.id.me_login_tv_balance);
        mLoginIvArrowRight = (ImageView) view.findViewById(R.id.me_login_iv_arrow_right);
        mLoginLayout = (RelativeLayout) view.findViewById(R.id.me_login_layout);
        mNologinBtnLogin = (Button) view.findViewById(R.id.me_nologin_btn_login);
        mNologinLayout = (LinearLayout) view.findViewById(R.id.me_nologin_layout);
        mUserTvCart = (TextView) view.findViewById(R.id.me_user_tv_cart);
        mUserTvFavorite = (TextView) view.findViewById(R.id.me_user_tv_favorite);
        mUserTvHistory = (TextView) view.findViewById(R.id.me_user_tv_history);
        mItemUnpaidTvCount = (TextView) view.findViewById(R.id.me_item_unpaid_tv_count);
        mItemUnpaidLayout = (RelativeLayout) view.findViewById(R.id.me_item_unpaid_layout);
        mItemPaidTvCount = (TextView) view.findViewById(R.id.me_item_paid_tv_count);
        mItemPaidTvUncommentCount = (TextView) view.findViewById(R.id.me_item_paid_tv_uncomment_count);
        mItemPaidOrderLayout = (RelativeLayout) view.findViewById(R.id.me_item_paid_order_layout);
        mItemLotteryTvCount = (TextView) view.findViewById(R.id.me_item_lottery_tv_count);
        mItemLotteryLayout = (RelativeLayout) view.findViewById(R.id.me_item_lottery_layout);
        mItemTreasureTvCount = (TextView) view.findViewById(R.id.me_item_treasure_tv_count);
        mItemTreasureLayout = (RelativeLayout) view.findViewById(R.id.me_item_treasure_layout);
        mUserOrderLayout = (LinearLayout) view.findViewById(R.id.me_user_order_layout);
        mItemBankcardTvComplete = (TextView) view.findViewById(R.id.me_item_bankcard_tv_complete);
        mItemBankcardLayout = (RelativeLayout) view.findViewById(R.id.me_item_bankcard_layout);
        mItemCouponsTvCount = (TextView) view.findViewById(R.id.me_item_coupons_tv_count);
        mItemCouponsLayout = (RelativeLayout) view.findViewById(R.id.me_item_coupons_layout);
        mItemRecommendTvNew = (TextView) view.findViewById(R.id.me_item_recommend_tv_new);
        mItemRecommendLayout = (RelativeLayout) view.findViewById(R.id.me_item_recommend_layout);
        mItemQrLayout = (RelativeLayout) view.findViewById(R.id.me_item_qr_layout);

        mLoginIvHead.setOnClickListener(this);
        mLoginIvArrowRight.setOnClickListener(this);
        mNologinBtnLogin.setOnClickListener(this);
        mUserTvCart.setOnClickListener(this);
        mUserTvFavorite.setOnClickListener(this);
        mUserTvHistory.setOnClickListener(this);
        mItemUnpaidLayout.setOnClickListener(this);
        mItemPaidOrderLayout.setOnClickListener(this);
        mItemLotteryLayout.setOnClickListener(this);
        mItemTreasureLayout.setOnClickListener(this);
        mItemBankcardLayout.setOnClickListener(this);
        mItemCouponsLayout.setOnClickListener(this);
        mItemRecommendLayout.setOnClickListener(this);
        mItemQrLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_login_iv_head:

                break;
            case R.id.me_login_iv_arrow_right:

                break;
            case R.id.me_nologin_btn_login:

                break;
            case R.id.me_user_tv_cart:

                break;
            case R.id.me_user_tv_favorite:

                break;
            case R.id.me_user_tv_history:

                break;
            case R.id.me_item_unpaid_layout:

                break;
            case R.id.me_item_paid_order_layout:

                break;
            case R.id.me_item_lottery_layout:

                break;
            case R.id.me_item_treasure_layout:

                break;
            case R.id.me_item_bankcard_layout:

                break;
            case R.id.me_item_coupons_layout:

                break;
            case R.id.me_item_recommend_layout:

                break;
            case R.id.me_item_qr_layout:

                break;
        }
    }
}