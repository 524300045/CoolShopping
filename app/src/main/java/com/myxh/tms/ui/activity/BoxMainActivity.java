package com.myxh.tms.ui.activity;

import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.myxh.tms.R;
import com.myxh.tms.common.AppConstant;
import com.myxh.tms.entity.GoodsInfo;
import com.myxh.tms.entity.HomeGridInfo;
import com.myxh.tms.ui.adapter.GoodsListAdapter;
import com.myxh.tms.ui.adapter.GridAdapter;
import com.myxh.tms.ui.adapter.ViewPageAdapter;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class BoxMainActivity extends BaseActivity implements View.OnClickListener  {

    private ImageView mTitleBarIvBack;

    private List<HomeGridInfo> pageOneData = new ArrayList<>();

    private List<View> mViewList = new ArrayList<>();

    private PullToRefreshListView mRefreshListView;

    private ListView mListView;

    private GoodsListAdapter mGoodsListAdapter;

    private List<GoodsInfo.ResultBean.GoodlistBean> mGoodlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_main);
        initData();
        initView();
    }

    private void initData() {
        String[] gridTitles = getResources().getStringArray(R.array.box_home_bar_labels);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.box_home_bar_icon);

        String[] gridCodes = getResources().getStringArray(R.array.box_home_bar_code);
        for (int i = 0; i < gridTitles.length; i++) {
            if (i < 8) {
                pageOneData.add(new HomeGridInfo(typedArray.getResourceId(i,0),gridTitles[i],gridCodes[i]));
            }
        }

    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);

        mRefreshListView = (PullToRefreshListView)findViewById(R.id.home_pull_to_refresh_listView);

        View headView = LayoutInflater.from(BoxMainActivity.this).inflate(R.layout.home_head_page,null);
        ViewPager headPager = (ViewPager) headView.findViewById(R.id.home_head_pager);

        View pageOne = LayoutInflater.from(BoxMainActivity.this).inflate(R.layout.home_gridview,null);
        GridView gridView1 = (GridView) pageOne.findViewById(R.id.home_gridView);
        gridView1.setAdapter(new GridAdapter(BoxMainActivity.this,pageOneData));

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              TextView tv=(TextView)view.findViewById(R.id.grid_code);
                System.out.print(tv.getText());
                if (tv.getText().toString().equals("boxcheck"))
                {
                    if (AppConstant.DRIVER_Code.equals(""))
                    {
                        ToastUtil.show(BoxMainActivity.this,"您的账号还未绑定司机");
                        return;
                    }
                    //门店清点
                    openActivity(StoreCheckActivity.class);
                }
                else if (tv.getText().toString().equals("boxrecyle"))
                {
                    //回收入库
                    openActivity(BoxRecyleLineActivity.class);
                }
                else if (tv.getText().toString().equals("boxsend"))
                {
                    //发运出库
                    openActivity(BoxSendLineActivity.class);
                }
                else if (tv.getText().toString().equals("boxfreshsend"))
                {
                    //生鲜发运出库
                    openActivity(BoxFreshSendActivity.class);
                }
                else if (tv.getText().toString().equals("boxstandsend"))
                {
                    //干货发运出库
                    openActivity(BoxStandSendActivity.class);
                }
            }
        });

        mViewList.add(pageOne);
        // mViewList.add(pageTwo);
        headPager.setAdapter(new ViewPageAdapter(mViewList));

        mListView = mRefreshListView.getRefreshableView();
        mListView.addHeaderView(headView);
        mListView.setHeaderDividersEnabled(false);
        int headerViewsCount = mListView.getHeaderViewsCount();
        mGoodsListAdapter = new GoodsListAdapter(BoxMainActivity.this,mGoodlist,headerViewsCount);
        mRefreshListView.setAdapter(mGoodsListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             //   openActivity(DetailActivity.class,bundle);
            }
        });


        mTitleBarIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_titleBar_iv_back:
                finish();
                break;
        }
    }
}
