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
import com.myxh.tms.entity.Line;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.LineRequest;
import com.myxh.tms.response.LineResponse;
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

public class BoxSendLineActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  LINE_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;


    private  TextView tvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_send_line);

        initView();
        initData();
    }

    private void initData() {



        LineRequest lineRequest=new LineRequest();
        lineRequest.setWarehouseCode(AppConstant.WareHouse_Code);

        Gson gson = new Gson();
        String json=gson.toJson(lineRequest);

        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/line/getWareLine",RequestMethod.POST);
        request.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxSendLineActivity.this, LINE_REQUEST, request, this, true, true);


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
                Bundle bundle = new Bundle();

                bundle.putString("lineCode",tvLineCode.getText().toString());
                bundle.putString("lineName",tvLineName.getText().toString());

                openActivity(BoxSendLineStoreActivity.class,bundle);


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
                LineResponse lineResponse=gson.fromJson(response.get(),LineResponse.class);
                if (lineResponse!=null&&lineResponse.getCode().equals("200"))
                {
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    if (lineResponse.getResult()!=null&&lineResponse.getResult().size()>0)
                    {
                        for (Line line:lineResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("lineCode",line.getLineCode());
                            map.put("lineName",line.getLineName());

                            mapList.add(map);
                        }
                    }

                    SpecialAdapter adp = new SpecialAdapter(this, mapList,
                            R.layout.item_box_recyle_line, new String[] {"lineCode", "lineName"},
                            new int[] {R.id.tvLineCode, R.id.tvLineName});
                    mListView.setAdapter(adp);

                }
                else
                {
                    ToastUtil.show(BoxSendLineActivity.this,"获取周转筐信息失败");
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
