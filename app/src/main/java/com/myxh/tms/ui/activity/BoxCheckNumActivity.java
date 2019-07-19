package com.myxh.tms.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myxh.tms.R;
import com.myxh.tms.common.AppConstant;
import com.myxh.tms.common.Util;
import com.myxh.tms.listener.TextInputWatcher;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.BoxCheckInfoAddRequest;
import com.myxh.tms.request.BoxCheckInfoRequest;
import com.myxh.tms.response.BoxCheckInfoAddResponse;
import com.myxh.tms.response.BoxCheckInfoResponse;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.math.BigDecimal;

public class BoxCheckNumActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  Box_CHCEK_REQUEST = 0x01; //根据司机获取门店信息what

    private static final int  Box_ADD_REQUEST = 2;

    private ImageView mTitleBarIvBack;



    private  String storeCode;

    private  String storeName;

    private  TextView tvTitle;

    private Button btnSure;

    private EditText etNum;

    private  String customerCode;

    private  String customerName;

    private  TextView tvBoxTypeCode;

    private  TextView tvBoxTypeName;

    private ImageView mIvClearNum;

    private  String boxTypeCode;

    private  String boxTypeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_check_num);

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

        tvBoxTypeCode.setText(boxTypeCode);
        tvBoxTypeName.setText(boxTypeName);

        tvTitle.setText("门店清点-"+storeName);

        BoxCheckInfoRequest request=new BoxCheckInfoRequest();
        request.setBoxTypeCode(boxTypeCode);
        request.setCustomerCode(customerCode);
        request.setStoredCode(storeCode);
        request.setStatus(0);

        Gson gson = new Gson();
        String json=gson.toJson(request);

        Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/getBoxCheckInfo",RequestMethod.POST);
        boxTypeRequest.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxCheckNumActivity.this, Box_CHCEK_REQUEST, boxTypeRequest, this, true, true);


    }

    private void initView() {
        mTitleBarIvBack = (ImageView) findViewById(R.id.common_titleBar_iv_back);
        mTitleBarIvBack.setOnClickListener(this);

        tvTitle=(TextView) findViewById(R.id.common_titleBar_tv_title);
        btnSure=(Button)findViewById(R.id.check_sure);
        tvBoxTypeCode=(TextView) findViewById(R.id.tvBoxTypeCode);
        tvBoxTypeName=(TextView) findViewById(R.id.tvBoxTypeName);

        etNum=(EditText)findViewById(R.id.et_num);

        mIvClearNum=(ImageView) findViewById(R.id.iv_clear_num);
        mIvClearNum.setOnClickListener(this);

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
                    ToastUtil.show(BoxCheckNumActivity.this,"数量不能为空!");
                    return;
                }
                if (!Util.isNumeric(etNum.getText().toString()))
                {
                    ToastUtil.show(BoxCheckNumActivity.this,"数量只能输入数字!");
                    return;
                }

                BoxCheckInfoAddRequest request=new BoxCheckInfoAddRequest();
                request.setBoxTypeCode(boxTypeCode);
                request.setBoxTypeName(tvBoxTypeName.getText().toString());
                request.setCustomerCode(customerCode);
                request.setCustomerName(customerName);
                request.setStoredCode(storeCode);
                request.setStoredName(storeName);
                request.setUserName(AppConstant.User_Name);
                request.setDriverCode(AppConstant.DRIVER_Code);
                request.setDriverName(AppConstant.DRIVER_Name);
                request.setWarehouseCode(AppConstant.WareHouse_Code);
                request.setWarehouseName(AppConstant.WareHouse_Name);

                request.setCheckNum(new BigDecimal(etNum.getText().toString()));
                Gson gson = new Gson();
                String json=gson.toJson(request);

                Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/add",RequestMethod.POST);
                boxTypeRequest.setDefineRequestBodyForJson(json);


                CallServer.getInstance().add(BoxCheckNumActivity.this, Box_ADD_REQUEST, boxTypeRequest, this, true, true);


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
                    if(boxCheckInfoResponse.getResult().getCheckNum().intValue()==0)
                    {
                        etNum.setText("");
                    }
                    else
                    {
                        etNum.setText(String.valueOf(boxCheckInfoResponse.getResult().getCheckNum().intValue()));
                    }


                }
                else
                {
                    ToastUtil.show(BoxCheckNumActivity.this,"获取周转筐信息失败");
                }
                break;
            case  Box_ADD_REQUEST:
                BoxCheckInfoAddResponse boxCheckInfoAddResponse=gson.fromJson(response.get(),BoxCheckInfoAddResponse.class);
                if (boxCheckInfoAddResponse!=null&&boxCheckInfoAddResponse.getCode().equals("200"))
                {
                    ToastUtil.show(BoxCheckNumActivity.this,"提交成功");
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                }
                else
                {
                    ToastUtil.show(BoxCheckNumActivity.this,"提交失败");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }


}
