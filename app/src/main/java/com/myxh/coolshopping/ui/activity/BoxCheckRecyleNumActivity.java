package com.myxh.coolshopping.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxh.coolshopping.R;
import com.myxh.coolshopping.common.AppConstant;
import com.myxh.coolshopping.common.Util;
import com.myxh.coolshopping.entity.WareHouse;
import com.myxh.coolshopping.entity.WarehouseArea;
import com.myxh.coolshopping.listener.TextInputWatcher;
import com.myxh.coolshopping.network.CallServer;
import com.myxh.coolshopping.network.HttpListener;
import com.myxh.coolshopping.request.BoxCheckInfoAddRequest;
import com.myxh.coolshopping.request.BoxCheckInfoRecyleUpdateRequest;
import com.myxh.coolshopping.request.BoxCheckInfoRequest;
import com.myxh.coolshopping.request.WareRequest;
import com.myxh.coolshopping.request.WarehouseAreaRequest;
import com.myxh.coolshopping.response.BoxCheckInfoAddResponse;
import com.myxh.coolshopping.response.BoxCheckInfoRecyleUpdateResponse;
import com.myxh.coolshopping.response.BoxCheckInfoResponse;
import com.myxh.coolshopping.response.WareResponse;
import com.myxh.coolshopping.response.WarehouseAreaResponse;
import com.myxh.coolshopping.ui.base.BaseActivity;
import com.myxh.coolshopping.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BoxCheckRecyleNumActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  Box_CHCEK_REQUEST = 0x01; //根据司机获取门店信息what

    private static final int  Box_CHECK_RECYLE_REQUEST = 2;


    private static final int  AREA_REQUEST =3;

    private ImageView mTitleBarIvBack;



    private  String storeCode;

    private  String storeName;

    private  TextView tvTitle;

    private Button btnSure;

    private EditText etNum;



    private  TextView tvBoxTypeCode;

    private  TextView tvBoxTypeName;

    private ImageView mIvClearNum;

    private  String boxTypeCode;

    private  String boxTypeName;

    private String checkNo;

    private TextView tvStoreName;

    private  String customerCode;


    private List<WarehouseArea> warehouseAreaList;

    private Spinner mWareAreaSpinner;

    private ArrayAdapter<WarehouseArea> arrWareAreaAdapter;

    private  String wareAreaCode;

    private  String wareAreaName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_check_recyle_num);

        initView();
        initData();
    }

    private void initData() {

        storeCode=getIntent().getExtras().getString("storeCode");
        storeName=getIntent().getExtras().getString("storeName");

        boxTypeCode=getIntent().getExtras().getString("boxTypeCode");
        boxTypeName=getIntent().getExtras().getString("boxTypeName");
        customerCode=getIntent().getExtras().getString("customerCode");
        checkNo=getIntent().getExtras().getString("checkNo");

        tvBoxTypeCode.setText(boxTypeCode);
        tvBoxTypeName.setText(boxTypeName);

        tvStoreName.setText(storeName);

        tvTitle.setText("回收入库-"+storeName);

        BoxCheckInfoRequest request=new BoxCheckInfoRequest();
        request.setBoxTypeCode(boxTypeCode);
        request.setCustomerCode(customerCode);
        request.setStoredCode(storeCode);
        request.setStatus(0);
        Gson gson = new Gson();
        String json=gson.toJson(request);
        Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/getBoxCheckInfo",RequestMethod.POST);
        boxTypeRequest.setDefineRequestBodyForJson(json);
        CallServer.getInstance().add(BoxCheckRecyleNumActivity.this, Box_CHCEK_REQUEST, boxTypeRequest, this, true, true);


        WarehouseAreaRequest warehouseAreaRequest=new WarehouseAreaRequest();
        warehouseAreaRequest.setWarehouseCode(AppConstant.WareHouse_Code);
        Gson wareGson = new Gson();
        String areaJson=wareGson.toJson(warehouseAreaRequest);

        Request<String> wareRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/warehouseArea/getWarehouseArea",RequestMethod.POST);
        wareRequest.setDefineRequestBodyForJson(areaJson);

        CallServer.getInstance().add(BoxCheckRecyleNumActivity.this, AREA_REQUEST, wareRequest, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);

        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);
        btnSure=(Button)findViewById(R.id.check_sure);
        tvBoxTypeCode=(TextView) findViewById(R.id.tvBoxTypeCode);
        tvBoxTypeName=(TextView) findViewById(R.id.tvBoxTypeName);
        tvStoreName=(TextView) findViewById(R.id.tvStoreName);
        etNum=(EditText)findViewById(R.id.et_num);

        mIvClearNum=(ImageView) findViewById(R.id.iv_clear_num);
        mIvClearNum.setOnClickListener(this);

        mWareAreaSpinner=(Spinner) findViewById(R.id.ware_area_spinner);
        etNum.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                boolean isNumNull = TextUtils.isEmpty(etNum.getText());
                mIvClearNum.setVisibility(isNumNull ? View.GONE : View.VISIBLE);
                mIvClearNum.setEnabled(!isNumNull);
                btnSure.setEnabled(isNumNull? false : true);
            }
        });

        btnSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_titleBar_iv_back:
                finish();
                break;
            case  R.id.iv_clear_num:

                etNum.setText("");
                mIvClearNum.setVisibility(View.GONE);
                break;
            case R.id.check_sure:
                if(TextUtils.isEmpty(etNum.getText()))
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"数量不能为空!");
                    return;
                }
                if (!Util.isNumeric(etNum.getText().toString()))
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"数量只能输入数字!");
                    return;
                }
                if (TextUtils.isEmpty(wareAreaCode))
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"请选择库区!");
                    return;
                }

                BoxCheckInfoRecyleUpdateRequest request=new BoxCheckInfoRecyleUpdateRequest();

                request.setAreaCode(wareAreaCode);
                request.setAreaName(wareAreaName);
                request.setUserName(AppConstant.User_Name);
                request.setCheckNo(checkNo);
                request.setRealityNum(new BigDecimal(etNum.getText().toString()));
                Gson gson = new Gson();
                String json=gson.toJson(request);

                Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/updateRealityNum",RequestMethod.POST);
                boxTypeRequest.setDefineRequestBodyForJson(json);


                CallServer.getInstance().add(BoxCheckRecyleNumActivity.this, Box_CHECK_RECYLE_REQUEST, boxTypeRequest, this, true, true);


                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case  Box_CHCEK_REQUEST:
                BoxCheckInfoResponse boxCheckInfoResponse=gson.fromJson(response.get(),BoxCheckInfoResponse.class);
                if (boxCheckInfoResponse!=null&&boxCheckInfoResponse.getCode().equals("200")) {

                    tvBoxTypeCode.setText(boxCheckInfoResponse.getResult().getBoxTypeCode());
                    tvBoxTypeName.setText(boxCheckInfoResponse.getResult().getBoxTypeName());
                    tvStoreName.setText(boxCheckInfoResponse.getResult().getStoredName());
                    etNum.setText(String.valueOf(boxCheckInfoResponse.getResult().getCheckNum().intValue()));

                }
                else
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"获取周转筐信息失败");
                }
                break;
            case  Box_CHECK_RECYLE_REQUEST:
                BoxCheckInfoRecyleUpdateResponse boxCheckInfoRecyleUpdateResponse=gson.fromJson(response.get(),BoxCheckInfoRecyleUpdateResponse.class);
                if (boxCheckInfoRecyleUpdateResponse!=null&&boxCheckInfoRecyleUpdateResponse.getCode().equals("200"))
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"提交成功");
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                }
                else
                {
                    ToastUtil.show(BoxCheckRecyleNumActivity.this,"提交失败");
                }
                break;
            case  AREA_REQUEST:
                WarehouseAreaResponse warehouseAreaResponse = gson.fromJson(response.get(),WarehouseAreaResponse.class);
                if(warehouseAreaResponse!=null&&warehouseAreaResponse.getCode().equals("200"))
                {
                    warehouseAreaList=warehouseAreaResponse.getResult();
                }
                else
                {
                    warehouseAreaList=new ArrayList<>();
                }

                arrWareAreaAdapter= new ArrayAdapter<WarehouseArea>(this,
                        android.R.layout.simple_spinner_item, warehouseAreaList);

                arrWareAreaAdapter
                        .setDropDownViewResource(R.layout.ware_item);
                // 加载适配器
                mWareAreaSpinner.setAdapter(arrWareAreaAdapter);
                mWareAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        wareAreaCode = ((WarehouseArea) mWareAreaSpinner.getSelectedItem())
                                .getAreaCode();
                        wareAreaName = ((WarehouseArea) mWareAreaSpinner.getSelectedItem())
                                .getAreaName();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }


}
