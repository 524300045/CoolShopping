package com.myxh.coolshopping.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.myxh.coolshopping.R;

public class ListViewPager extends ListView implements android.widget.AbsListView.OnScrollListener  {
    View footer;//定义底部view

    int lastItem;//最后一个可见的数量
    int totalItem;//总的数量

    boolean isload;//是否正在加载

    IDataInterface datainter;

    public ListViewPager(Context context) {
        super(context);
        InitFooter(context);
        // TODO Auto-generated constructor stub

    }



    public ListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitFooter(context);
    }

    public ListViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        InitFooter(context);
    }



    //初始化底部布局栏
    private void InitFooter(Context oontext)
    {
        LayoutInflater inflater=LayoutInflater.from(oontext);
        //添加底部栏
        footer=inflater.inflate(R.layout.footer, null);
        footer.setVisibility(View.GONE);//初始化的时候设置底部不可见

        this.addFooterView(footer);
        this.setOnScrollListener(this);

    }




    @Override
    public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        //arg1可见的第一个数     arg2可见的数量      arg3总的数量
        this.lastItem=arg1+arg2;//最后一个可见的数等于当前第一个可见的数加上可见的数量
        this.totalItem=arg3;


    }

    //加载完成




    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        // TODO Auto-generated method stub  滚动状态等于滚动停止,参数arg1表示滚动状态
        if(lastItem==totalItem&&arg1==SCROLL_STATE_IDLE)
        {
            //最后一个和总的相等说明已经到listview底部
            if(!isload)
            {
                isload=true;
                footer.setVisibility(View.VISIBLE);//设置可见



                datainter.LoadData();//加载数据
                isload=false;
                //footer.setVisibility(View.GONE);//设置可见
            }

        }

    }

    public void setInterface(IDataInterface data)
    {

        datainter=data;
    }

}
