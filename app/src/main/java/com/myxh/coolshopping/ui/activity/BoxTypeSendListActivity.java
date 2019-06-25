package com.myxh.coolshopping.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.common.AppConstant;
import com.myxh.coolshopping.entity.BoxType;
import com.myxh.coolshopping.network.CallServer;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.request.BoxTypeRequest;
import com.myxh.coolshopping.response.BoxTypeResponse;
import com.myxh.coolshopping.ui.base.BaseActivity;
import com.myxh.coolshopping.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BoxTypeSendListActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  Box_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;

    private  String storeCode;

    private  String storeName;

    private  String customerCode;

    private  String customerName;

    private  TextView tvTitle;

    private  String lineCode;

    private  String lineName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_type_send_list);

        initView();
        initData();
    }

    private void initData() {

        storeCode=getIntent().getExtras().getString("storedCode");
        storeName=getIntent().getExtras().getString("storedName");
        customerCode=getIntent().getExtras().getString("customerCode");
        customerName=getIntent().getExtras().getString("customerName");

        lineCode=getIntent().getExtras().getString("lineCode");
        lineName=getIntent().getExtras().getString("lineName");

        tvTitle.setText(storeName);

        BoxTypeRequest request=new BoxTypeRequest();
        request.setCustomerCode(customerCode);
        request.setStoredCode(storeCode);
        Gson gson = new Gson();
        String json=gson.toJson(request);

        Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxType/getAllBoxTypeList",RequestMethod.POST);
        boxTypeRequest.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxTypeSendListActivity.this, Box_REQUEST, boxTypeRequest, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.box_check_listView);
        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                //周转筐名称
                TextView tvBoxTypeCode = (TextView) arg1.findViewById(R.id.tvBoxTypeCode);
                TextView tvBoxTypeName = (TextView) arg1.findViewById(R.id.tvBoxTypeName);
                Bundle bundle = new Bundle();
                bundle.putString("storeCode",storeCode);
                bundle.putString("storeName",storeName);
                bundle.putString("boxTypeCode",tvBoxTypeCode.getText().toString());
                bundle.putString("boxTypeName",tvBoxTypeName.getText().toString());
                bundle.putString("customerCode",customerCode);
                bundle.putString("customerName",customerName);
                bundle.putString("lineCode",lineCode);
                bundle.putString("lineName",lineName);
                openActivity(BoxSendListActivity.class,bundle);

               /* Intent intent=new Intent();
                intent.setClass(BoxTypeSendListActivity.this, BoxSendListActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);*/
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
            case  Box_REQUEST:
                BoxTypeResponse boxTypeResponse=gson.fromJson(response.get(),BoxTypeResponse.class);
                if (boxTypeResponse!=null&&boxTypeResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (boxTypeResponse.getResult()!=null&&boxTypeResponse.getResult().size()>0)
                    {
                        for (BoxType boxType:boxTypeResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("boxTypeCode",boxType.getBoxTypeCode());
                            map.put("boxTypeName",boxType.getBoxTypeName());
                            map.put("remark","颜色:"+boxType.getColor()+",规格:"+boxType.getBoxLength()+"X"+boxType.getBoxWidth().toString()+"X"+boxType.getBoxHigh());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_type_send, new String[] {"boxTypeCode", "boxTypeName", "remark" ,},
                            new int[] {R.id.tvBoxTypeCode, R.id.tvBoxTypeName, R.id.tvRemark});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxTypeSendListActivity.this,"获取周转筐信息失败");
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
