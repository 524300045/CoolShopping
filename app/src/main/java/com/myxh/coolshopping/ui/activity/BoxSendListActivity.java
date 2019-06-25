package com.myxh.coolshopping.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.common.AppConstant;
import com.myxh.coolshopping.common.DateUtils;
import com.myxh.coolshopping.entity.BoxOutDetail;
import com.myxh.coolshopping.entity.LineStore;
import com.myxh.coolshopping.network.CallServer;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.request.BoxOutDetailQueryRequest;
import com.myxh.coolshopping.request.LineRequest;
import com.myxh.coolshopping.response.BoxOutDetailResponse;
import com.myxh.coolshopping.response.LineStoreResponse;
import com.myxh.coolshopping.ui.base.BaseActivity;
import com.myxh.coolshopping.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BoxSendListActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  LINE_STORE_REQUEST = 0x01; //根据司机获取门店信息what


    private ImageView mTitleBarIvBack;

    private ListView mListView;

    private  TextView tvTitle;

    private Button btnSure;

    private  String storeCode;

    private  String storeName;

    private  String customerCode;

    private  String customerName;


    private String boxTypeCode;

    private  String boxTypeName;

    private  String lineCode;

    private  String lineName;

    SpecialAdapter adapter;
    private boolean loadFinishFlag;
    private int startIndex;
    private int endIndex;
    private final int pageSize = 2;

    private View footer;

    private boolean isload;//是否正在加载

    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_send_list);

        loadFinishFlag = true;
        startIndex = 0;
        endIndex =1;

        initView();
        initData();


    }

    private void initData() {

        storeCode=getIntent().getExtras().getString("storeCode");
        storeName=getIntent().getExtras().getString("storeName");
        customerCode=getIntent().getExtras().getString("customerCode");
        customerName=getIntent().getExtras().getString("customerName");
        boxTypeCode=getIntent().getExtras().getString("boxTypeCode");
        boxTypeName=getIntent().getExtras().getString("boxTypeName");

        lineCode=getIntent().getExtras().getString("lineCode");
        lineName=getIntent().getExtras().getString("lineName");

        tvTitle.setText(boxTypeName);

        BoxOutDetailQueryRequest boxOutDetailQueryRequest=new BoxOutDetailQueryRequest();
        boxOutDetailQueryRequest.setStoredCode(storeCode);
        boxOutDetailQueryRequest.setCustomerCode(customerCode);
        boxOutDetailQueryRequest.setBoxTypeCode(boxTypeCode);
        boxOutDetailQueryRequest.setPageIndex(endIndex);
        boxOutDetailQueryRequest.setPageSize(pageSize);
       // Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        String json=gson.toJson(boxOutDetailQueryRequest);

        Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxOutDetail/getOutList",RequestMethod.POST);
        request.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxSendListActivity.this, LINE_STORE_REQUEST, request, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.box_send_listView);
        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);
        btnSure=(Button) findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        footer = getLayoutInflater().inflate(R.layout.footer, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        System.out.print(resultCode);
        if (resultCode==-1)
        {
          // mapList.clear();
            mapList = new ArrayList<Map<String, Object>>();

            endIndex=1;
           BoxOutDetailQueryRequest boxOutDetailQueryRequest=new BoxOutDetailQueryRequest();
            boxOutDetailQueryRequest.setStoredCode(storeCode);
            boxOutDetailQueryRequest.setCustomerCode(customerCode);
            boxOutDetailQueryRequest.setBoxTypeCode(boxTypeCode);
            boxOutDetailQueryRequest.setPageIndex(endIndex);
            boxOutDetailQueryRequest.setPageSize(pageSize);
            Gson gson = new Gson();


            String json=gson.toJson(boxOutDetailQueryRequest);

            System.out.print(json);

           Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxOutDetail/getOutList",RequestMethod.POST);
            request.setDefineRequestBodyForJson(json);


           CallServer.getInstance().add(BoxSendListActivity.this, LINE_STORE_REQUEST, request, this, true, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_titleBar_iv_back:
                finish();
                break;
            case R.id.btn_sure:

                Bundle bundle = new Bundle();
                bundle.putString("storeCode",storeCode);
                bundle.putString("storeName",storeName);
                bundle.putString("boxTypeCode",boxTypeCode);
                bundle.putString("boxTypeName",boxTypeName);
                bundle.putString("customerCode",customerCode);
                bundle.putString("customerName",customerName);

                bundle.putString("lineCode",lineCode);
                bundle.putString("lineName",lineName);

                Intent intent=new Intent();
                intent.setClass(BoxSendListActivity.this, BoxSendNumActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;

        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        /*Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();*/
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson =builder.create();
        switch (what) {
            case  LINE_STORE_REQUEST:
                BoxOutDetailResponse boxOutDetailResponse=gson.fromJson(response.get(),BoxOutDetailResponse.class);
                if (boxOutDetailResponse!=null&&boxOutDetailResponse.getCode().equals("200"))
                {

                    if (boxOutDetailResponse.getResult()!=null&&boxOutDetailResponse.getResult().size()>0)
                    {
                        for (BoxOutDetail boxOutDetail:boxOutDetailResponse.getResult())
                        {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("boxTypeCode",boxOutDetail.getBoxTypeCode());
                            map.put("boxTypeName",boxOutDetail.getBoxTypeName());
                            map.put("storedCode",boxOutDetail.getStoredCode());
                            map.put("storedName",boxOutDetail.getStoredName());
                            map.put("realityNum",boxOutDetail.getRealityNum());
                            map.put("id",boxOutDetail.getId());
                            try {
                                map.put("createTime", DateUtils.dateFormat(boxOutDetail.getCreateTime(),DateUtils.HOUR_PATTERN));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mapList.add(map);
                        }

                        adapter = new SpecialAdapter(this, mapList,
                                R.layout.item_box_out_detail, new String[] {"boxTypeCode", "boxTypeName","storedName" ,"realityNum","createTime"},
                                new int[] {R.id.tvBoxTypeCode, R.id.tvBoxTypeName, R.id.tvStoreName,R.id.tvNum,R.id.tvTime});
                        mListView.setAdapter(adapter);
                        mListView.addFooterView(footer);
                        mListView.setOnScrollListener(new ScrollListener());
                        mListView.removeFooterView(footer);
                        isload=false;
                       // startIndex += pageSize;
                        endIndex += 1;

                        //loadFinishFlag=true;
                    }
                    else
                    {
                        mListView.removeFooterView(footer);
                    }



                }
                else
                {
                    ToastUtil.show(BoxSendListActivity.this,"获取出库信息失败");
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

    public final class ScrollListener implements AbsListView.OnScrollListener {

        int lastItem;//最后一个可见的数量
        int totalItem;//总的数量



        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

           /* switch (scrollState) {
                case SCROLL_STATE_IDLE:

                    break;
                case SCROLL_STATE_TOUCH_SCROLL:

                    break;
                case SCROLL_STATE_FLING:
                    break;
            }*/
            if(lastItem==totalItem&&scrollState==SCROLL_STATE_IDLE)
            {
                //最后一个和总的相等说明已经到listview底部
                if(!isload)
                {
                    isload=true;
                    footer.setVisibility(View.VISIBLE);//设置可见

                    mListView.addFooterView(footer);

                    BoxOutDetailQueryRequest boxOutDetailQueryRequest=new BoxOutDetailQueryRequest();
                    boxOutDetailQueryRequest.setStoredCode(storeCode);
                    boxOutDetailQueryRequest.setCustomerCode(customerCode);
                    boxOutDetailQueryRequest.setBoxTypeCode(boxTypeCode);
                    boxOutDetailQueryRequest.setPageIndex(endIndex);
                    boxOutDetailQueryRequest.setPageSize(pageSize);
                    Gson gson = new Gson();
                    String json=gson.toJson(boxOutDetailQueryRequest);

                    Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxOutDetail/getOutList",RequestMethod.POST);
                    request.setDefineRequestBodyForJson(json);


                    CallServer.getInstance().add(BoxSendListActivity.this, LINE_STORE_REQUEST, request, BoxSendListActivity.this, true, true);



                    //footer.setVisibility(View.GONE);//设置可见
                }

            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //获取屏幕最后Item的ID
           /* int lastVisibleItem = mListView.getLastVisiblePosition();
            if (lastVisibleItem + 1 == totalItemCount) {
                if (loadFinishFlag) {
                    //标志位，防止多次加载
                    loadFinishFlag = false;
                    mListView.addFooterView(footer);

                    BoxOutDetailQueryRequest boxOutDetailQueryRequest=new BoxOutDetailQueryRequest();
                    boxOutDetailQueryRequest.setStoredCode(storeCode);
                    boxOutDetailQueryRequest.setCustomerCode(customerCode);
                    boxOutDetailQueryRequest.setBoxTypeCode(boxTypeCode);
                    boxOutDetailQueryRequest.setPageIndex(endIndex);
                    boxOutDetailQueryRequest.setPageSize(pageSize);
                    Gson gson = new Gson();
                    String json=gson.toJson(boxOutDetailQueryRequest);

                    Request<String> request = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxOutDetail/getOutList",RequestMethod.POST);
                    request.setDefineRequestBodyForJson(json);


                    CallServer.getInstance().add(BoxSendListActivity.this, LINE_STORE_REQUEST, request, BoxSendListActivity.this, true, true);


                }
            }*/
            this.lastItem=firstVisibleItem+visibleItemCount;//最后一个可见的数等于当前第一个可见的数加上可见的数量
            this.totalItem=totalItemCount;
        }
    }
}
