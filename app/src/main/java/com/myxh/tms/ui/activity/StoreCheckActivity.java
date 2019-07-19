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
import com.myxh.tms.entity.StoreCheckInfo;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.StoreCheckRequest;
import com.myxh.tms.response.StoreCheckResponse;
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

public class StoreCheckActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  STORE_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_check);

        initView();
        initData();
    }

    private void initData() {



        StoreCheckRequest request=new StoreCheckRequest();
        request.setWarehouseCode(AppConstant.WareHouse_Code);
        request.setDriverCode(AppConstant.DRIVER_Code);
        Gson gson = new Gson();
        String json=gson.toJson(request);

        Request<String> storeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/storeInfo/getStoreInfoByDriver",RequestMethod.POST);
        storeRequest.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(StoreCheckActivity.this, STORE_REQUEST, storeRequest, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.store_check_listView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                //门店名称
                TextView tvStoreCode = (TextView) arg1.findViewById(R.id.tvStoreCode);
                TextView tvStoreName = (TextView) arg1.findViewById(R.id.tvStoreName);
                TextView tvCustomerCode = (TextView) arg1.findViewById(R.id.tvCustomerCode);
                TextView tvCustomerName = (TextView) arg1.findViewById(R.id.tvCustomerName);

                Bundle bundle = new Bundle();
                bundle.putString("storeCode",tvStoreCode.getText().toString());
                bundle.putString("storeName",tvStoreName.getText().toString());
                bundle.putString("customerCode",tvCustomerCode.getText().toString());
                bundle.putString("customerName",tvCustomerName.getText().toString());
                openActivity(BoxCheckActivity.class,bundle);
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
                StoreCheckResponse storeResponse=gson.fromJson(response.get(),StoreCheckResponse.class);
                if (storeResponse!=null&&storeResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (storeResponse.getResult()!=null&&storeResponse.getResult().size()>0)
                    {
                        for (StoreCheckInfo storeInfo:storeResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("customerCode",storeInfo.getCustomerCode());
                            map.put("customerName",storeInfo.getCustomerName());
                            map.put("storedCode",storeInfo.getStoredCode());
                            map.put("storedName",storeInfo.getStoredName());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_store_check, new String[] {"storedCode", "storedName", "customerCode","customerName" },
                            new int[] {R.id.tvStoreCode, R.id.tvStoreName, R.id.tvCustomerCode,R.id.tvCustomerName});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(StoreCheckActivity.this,"获取门店信息失败");
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
                map = (java.util.Map<String, Object>) it.next();
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
