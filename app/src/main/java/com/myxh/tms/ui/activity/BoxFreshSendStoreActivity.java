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
import com.myxh.tms.entity.StoreInfo;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.LineStoreRequest;
import com.myxh.tms.request.StoreInfoRequest;
import com.myxh.tms.response.LineStoreResponse;
import com.myxh.tms.response.StoreInfoResponse;
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

public class BoxFreshSendStoreActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  STORE_REQUEST = 0x01; //根据司机获取门店信息what

    private static final int  LINE_STORE_REQUEST =2;

    private ImageView mTitleBarIvBack;

    private ListView mListView;


    private  TextView tvTitle;

    private  String customerCode;

    private  String customerName;

    private  String storedCode;

    private  String storedName;

    private Integer isFresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_fresh_send_store);

        initView();
        initData();
    }

    private void initData() {


        customerCode=getIntent().getExtras().getString("customerCode");
        customerName=getIntent().getExtras().getString("customerName");
        isFresh=getIntent().getIntExtra("isFresh",0);

        tvTitle.setText(customerName);

        StoreInfoRequest storeInfoRequest=new StoreInfoRequest();
        storeInfoRequest.setCustomerCode(customerCode);

        Gson gson = new Gson();
        String json=gson.toJson(storeInfoRequest);

        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/storeInfo/getAllStoreInfo",RequestMethod.POST);
        request.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxFreshSendStoreActivity.this, STORE_REQUEST, request, this, true, true);


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



                TextView tvStoreCode = (TextView) arg1.findViewById(R.id.tvStoreCode);
                TextView tvStoreName = (TextView) arg1.findViewById(R.id.tvStoreName);

                storedCode=tvStoreCode.getText().toString();
                storedName=tvStoreName.getText().toString();

                LineStoreRequest lineStoreRequest=new LineStoreRequest();
                lineStoreRequest.setCustomerCode(customerCode);
                lineStoreRequest.setStoredCode(tvStoreCode.getText().toString());
                lineStoreRequest.setWarehouseCode(AppConstant.WareHouse_Code);

                Gson gson = new Gson();
                String json=gson.toJson(lineStoreRequest);

                Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/lineStore/getLineStoreByStore",RequestMethod.POST);
                request.setDefineRequestBodyForJson(json);


                CallServer.getInstance().add(BoxFreshSendStoreActivity.this, LINE_STORE_REQUEST, request, BoxFreshSendStoreActivity.this, true, true);





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
            case  STORE_REQUEST:
                StoreInfoResponse storeInfoResponse=gson.fromJson(response.get(),StoreInfoResponse.class);
                if (storeInfoResponse!=null&&storeInfoResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (storeInfoResponse.getResult()!=null&&storeInfoResponse.getResult().size()>0)
                    {
                        for (StoreInfo storeInfo:storeInfoResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("storedCode",storeInfo.getStoredCode());
                            map.put("storedName",storeInfo.getStoredName());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_fresh_send_store, new String[] {"storedCode","storedName"},
                            new int[] {R.id.tvStoreCode, R.id.tvStoreName});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxFreshSendStoreActivity.this,"获取门店失败");
                }
                break;
            case  LINE_STORE_REQUEST:
                LineStoreResponse lineStoreResponse=gson.fromJson(response.get(),LineStoreResponse.class);
                if (lineStoreResponse!=null&&lineStoreResponse.getCode().equals("200"))
                {
                    if (lineStoreResponse.getResult()!=null)
                    {
                        if (lineStoreResponse.getResult().size()==0)
                        {

                            Bundle bundle = new Bundle();
                            bundle.putString("lineCode","");
                            bundle.putString("lineName","");
                            bundle.putString("customerCode",customerCode);
                            bundle.putString("customerName",customerName);
                            bundle.putString("storedCode",storedCode);
                            bundle.putString("storedName",storedName);
                            bundle.putInt("isFresh",isFresh);

                            openActivity(BoxTypeSendListActivity.class,bundle);
                        }
                        else if (lineStoreResponse.getResult().size()==1)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putInt("isFresh",isFresh);
                            bundle.putString("lineCode",lineStoreResponse.getResult().get(0).getLineCode());
                            bundle.putString("lineName",lineStoreResponse.getResult().get(0).getLineName());
                            bundle.putString("customerCode",customerCode);
                            bundle.putString("customerName",customerName);
                            bundle.putString("storedCode",lineStoreResponse.getResult().get(0).getStoredCode());
                            bundle.putString("storedName",lineStoreResponse.getResult().get(0).getStoredName());
                            openActivity(BoxTypeSendListActivity.class,bundle);
                        }
                        else
                        {
                            //跳转到线路选择界面

                            Bundle bundle = new Bundle();
                            bundle.putInt("isFresh",isFresh);
                            bundle.putString("lineCode","");
                            bundle.putString("lineName","");
                            bundle.putString("customerCode",customerCode);
                            bundle.putString("customerName",customerName);
                            bundle.putString("storedCode",storedCode);
                            bundle.putString("storedName",storedName);
                            openActivity(BoxFreshSendLineStoreActivity.class,bundle);
                        }
                    }
                    else
                    {
                        ToastUtil.show(BoxFreshSendStoreActivity.this,"当前门店没有绑定线路");

                        Bundle bundle = new Bundle();
                        bundle.putInt("isFresh",isFresh);
                        bundle.putString("lineCode","");
                        bundle.putString("lineName","");
                        bundle.putString("customerCode",customerCode);
                        bundle.putString("customerName",customerName);
                        bundle.putString("storedCode",storedCode);
                        bundle.putString("storedName",storedName);
                        openActivity(BoxTypeSendListActivity.class,bundle);

                    }
                }
                else
                {
                    ToastUtil.show(BoxFreshSendStoreActivity.this,"获取门店所属线路失败");
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
