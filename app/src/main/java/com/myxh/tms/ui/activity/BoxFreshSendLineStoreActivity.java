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
import com.myxh.tms.entity.LineStore;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.LineStoreRequest;
import com.myxh.tms.response.LineStoreResponse;
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

public class BoxFreshSendLineStoreActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  LINE_STORE_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;


    private  TextView tvTitle;

    private  String lineCode;

    private  String lineName;

    private  String customerCode;

    private  String storedCode;

    private Integer isFresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_fresh_send_line_store);

        initView();
        initData();
    }

    private void initData() {


       // lineCode=getIntent().getExtras().getString("lineCode");
      //  lineName=getIntent().getExtras().getString("lineName");

       // tvTitle.setText("发运出库-"+lineName);

        isFresh=getIntent().getIntExtra("isFresh",0);
        customerCode=getIntent().getExtras().getString("customerCode");
        storedCode=getIntent().getExtras().getString("storedCode");

        LineStoreRequest lineStoreRequest=new LineStoreRequest();
        lineStoreRequest.setCustomerCode(customerCode);
        lineStoreRequest.setStoredCode(storedCode);
        lineStoreRequest.setWarehouseCode(AppConstant.WareHouse_Code);

        Gson gson = new Gson();
        String json=gson.toJson(lineStoreRequest);

        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/lineStore/getLineStoreByStore",RequestMethod.POST);
        request.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxFreshSendLineStoreActivity.this, LINE_STORE_REQUEST, request, BoxFreshSendLineStoreActivity.this, true, true);


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
                TextView tvLineCode = (TextView) arg1.findViewById(R.id.tvLineCode);
                TextView tvLineName = (TextView) arg1.findViewById(R.id.tvLineName);

                TextView tvCustomerCode = (TextView) arg1.findViewById(R.id.tvCustomerCode);
                TextView tvCustomerName= (TextView) arg1.findViewById(R.id.tvCustomerName);

                TextView tvStoreCode = (TextView) arg1.findViewById(R.id.tvStoreCode);
                TextView tvStoreName = (TextView) arg1.findViewById(R.id.tvStoreName);

                Bundle bundle = new Bundle();

                bundle.putString("lineCode",tvLineCode.getText().toString());
                bundle.putString("lineName",tvLineName.getText().toString());
                bundle.putString("customerCode",tvCustomerCode.getText().toString());
                bundle.putString("customerName",tvCustomerName.getText().toString());
                bundle.putString("storedCode",tvStoreCode.getText().toString());
                bundle.putString("storedName",tvStoreName.getText().toString());
                bundle.putInt("isFresh",isFresh);

                openActivity(BoxTypeSendListActivity.class,bundle);


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
            case  LINE_STORE_REQUEST:
                LineStoreResponse lineStoreResponse=gson.fromJson(response.get(),LineStoreResponse.class);
                if (lineStoreResponse!=null&&lineStoreResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (lineStoreResponse.getResult()!=null&&lineStoreResponse.getResult().size()>0)
                    {
                        for (LineStore lineStore:lineStoreResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("lineCode",lineStore.getLineCode());
                            map.put("lineName",lineStore.getLineName());
                            map.put("customerCode",lineStore.getCustomerCode());
                            map.put("customerName",lineStore.getCustomerName());
                            map.put("storedCode",lineStore.getStoredCode());
                            map.put("storedName",lineStore.getStoredName());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_send_line_store, new String[] {"lineCode", "lineName","customerCode","customerName","storedCode","storedName"},
                            new int[] {R.id.tvLineCode, R.id.tvLineName,R.id.tvCustomerCode, R.id.tvCustomerName,R.id.tvStoreCode, R.id.tvStoreName});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxFreshSendLineStoreActivity.this,"获取线路门店失败");
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
