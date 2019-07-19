package com.myxh.tms.ui.activity;

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
import com.myxh.tms.R;
import com.myxh.tms.common.AppConstant;
import com.myxh.tms.entity.BoxCheckInfo;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.BoxCheckInfoRequest;
import com.myxh.tms.response.BoxCheckInfoRecyleResponse;
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

public class BoxCheckInfoRecyleActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  Box_Recyle_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;

    private  String lineCode;

    private  String lineName;



    private  TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_check_info_recyle);

        initView();
        initData();
    }

    private void initData() {

        lineCode=getIntent().getExtras().getString("lineCode");
        lineName=getIntent().getExtras().getString("lineName");



        BoxCheckInfoRequest request=new BoxCheckInfoRequest();
        request.setLineCode(lineCode);

        Gson gson = new Gson();
        String json=gson.toJson(request);

        Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/getBoxCheckInfoByLine",RequestMethod.POST);
        boxTypeRequest.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxCheckInfoRecyleActivity.this, Box_Recyle_REQUEST, boxTypeRequest, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.box_check_recyle_listView);
        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                //周转筐名称
                TextView tvBoxTypeCode = (TextView) arg1.findViewById(R.id.tvBoxTypeCode);
                TextView tvBoxTypeName = (TextView) arg1.findViewById(R.id.tvBoxTypeName);

                TextView tvStoreCode = (TextView) arg1.findViewById(R.id.tvStoreCode);
                TextView tvStoreName = (TextView) arg1.findViewById(R.id.tvStoreName);

                TextView tvCheckNo = (TextView) arg1.findViewById(R.id.tvCheckNo);
                TextView tvCustomerCode = (TextView) arg1.findViewById(R.id.tvCustomerCode);

                Bundle bundle = new Bundle();
                bundle.putString("storeCode",tvStoreCode.getText().toString());
                bundle.putString("storeName",tvStoreName.getText().toString());
                bundle.putString("boxTypeCode",tvBoxTypeCode.getText().toString());
                bundle.putString("boxTypeName",tvBoxTypeName.getText().toString());
                bundle.putString("checkNo",tvCheckNo.getText().toString());
                bundle.putString("customerCode",tvCustomerCode.getText().toString());

                Intent intent=new Intent();
                intent.setClass(BoxCheckInfoRecyleActivity.this, BoxCheckRecyleNumActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1)
        {
         /*   BoxTypeRequest request=new BoxTypeRequest();
            Gson gson = new Gson();
            String json=gson.toJson(request);

            Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxType/getAllBoxType",RequestMethod.POST);
            boxTypeRequest.setDefineRequestBodyForJson(json);
*/
            BoxCheckInfoRequest request=new BoxCheckInfoRequest();
            request.setLineCode(lineCode);

            Gson gson = new Gson();
            String json=gson.toJson(request);

            Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/getBoxCheckInfoByLine",RequestMethod.POST);
            boxTypeRequest.setDefineRequestBodyForJson(json);

            CallServer.getInstance().add(BoxCheckInfoRecyleActivity.this, Box_Recyle_REQUEST, boxTypeRequest, this, true, true);
        }
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
            case  Box_Recyle_REQUEST:
                BoxCheckInfoRecyleResponse boxCheckInfoRecyleResponse=gson.fromJson(response.get(),BoxCheckInfoRecyleResponse.class);
                if (boxCheckInfoRecyleResponse!=null&&boxCheckInfoRecyleResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (boxCheckInfoRecyleResponse.getResult()!=null&&boxCheckInfoRecyleResponse.getResult().size()>0)
                    {
                        for (BoxCheckInfo boxCheckInfo:boxCheckInfoRecyleResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("boxTypeCode",boxCheckInfo.getBoxTypeCode());
                            map.put("boxTypeName",boxCheckInfo.getBoxTypeName());
                            map.put("storeCode",boxCheckInfo.getStoredCode());
                            map.put("storeName",boxCheckInfo.getStoredName());
                            map.put("checkNum",boxCheckInfo.getCheckNum());
                            map.put("checkNo",boxCheckInfo.getCheckNo());
                            map.put("customerCode",boxCheckInfo.getCustomerCode());
                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_check_info_recyle, new String[] {"boxTypeCode", "boxTypeName", "storeCode","storeName" ,"checkNum","checkNo","customerCode"},
                            new int[] {R.id.tvBoxTypeCode, R.id.tvBoxTypeName, R.id.tvStoreCode, R.id.tvStoreName,R.id.tvNum,R.id.tvCheckNo,R.id.tvCustomerCode});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxCheckInfoRecyleActivity.this,"获取周转筐信息失败");
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
