package com.myxh.tms.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxh.tms.R;
import com.myxh.tms.common.AppConstant;
import com.myxh.tms.entity.CustomerInfo;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.CustomerInfoRequest;
import com.myxh.tms.response.CustomerInfoResponse;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BoxStandSendActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  LINE_REQUEST = 0x01;


    private ImageView mTitleBarIvBack;

    private ListView mListView;


    private  TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_stand_send);

        initView();
        initData();
    }

    private void initData() {



        CustomerInfoRequest customerInfoRequest=new CustomerInfoRequest();
        customerInfoRequest.setWarehouseCode(AppConstant.WareHouse_Code);

        Gson gson = new Gson();
        String json=gson.toJson(customerInfoRequest);

        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/customerInfo/getAllCustomerInfo",RequestMethod.POST);
        request.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxStandSendActivity.this, LINE_REQUEST, request, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.box_recyle_line_listView);
        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                //线路
                TextView tvCustomerCode = (TextView) arg1.findViewById(R.id.tvCustomerCode);
                TextView tvCustomerName = (TextView) arg1.findViewById(R.id.tvCustomerName);
                Bundle bundle = new Bundle();

                bundle.putString("customerCode",tvCustomerCode.getText().toString());
                bundle.putString("customerName",tvCustomerName.getText().toString());
                bundle.putInt("isFresh",0);

                openActivity(BoxFreshSendStoreActivity.class,bundle);


            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_titleBar_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case  LINE_REQUEST:
                CustomerInfoResponse customerInfoResponse=gson.fromJson(response.get(),CustomerInfoResponse.class);
                if (customerInfoResponse!=null&&customerInfoResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (customerInfoResponse.getResult()!=null&&customerInfoResponse.getResult().size()>0)
                    {
                        for (CustomerInfo customerInfo:customerInfoResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("customerCode",customerInfo.getCustomerCode());
                            map.put("customerName",customerInfo.getCustomerName());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_fresh_send_customer, new String[] {"customerCode", "customerName"},
                            new int[] {R.id.tvCustomerCode, R.id.tvCustomerName});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxStandSendActivity.this,"获取周客户信息失败");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }

     class SpecialAdapter extends SimpleAdapter {
        private int[] colors = new int[] { 0xFFFFF, 0x300000FF, 0x300000FF };
        private List<? extends Map<String, ?>> list;
        private Map<String, Object> map = new HashMap<String, Object>();

        @SuppressWarnings("unchecked")
        public SpecialAdapter(Context context,
                              List<? extends Map<String, ?>> data, int resource,
                              String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.list = data;
            // TODO Auto-generated constructor stub
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.SimpleAdapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = super.getView(position, convertView, parent);
            Iterator<? extends Map<String, ?>> it = list.iterator();
            int colorPos = 0;
            int index = 0;
            while (it.hasNext()) {
                map = (Map<String, Object>) it.next();
                Iterator<?> iter = map.entrySet().iterator();

                while (iter.hasNext()) {
                    @SuppressWarnings("rawtypes")
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();

                }

            }

            return view;
        }
    }
}
