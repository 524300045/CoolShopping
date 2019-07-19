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
import com.myxh.tms.request.BoxOutDetailRequest;
import com.myxh.tms.response.BoxOutDetailAddResponse;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.ToastUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.math.BigDecimal;

public class BoxSendNumActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>  {


    private static final int  Box_OUT_ADD_REQUEST = 0x01; //根据司机获取门店信息what



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

    private  String lineCode;

    private  String lineName;

    private Integer isFresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_send_num);

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

        isFresh=getIntent().getIntExtra("isFresh",0);

        tvBoxTypeCode.setText(boxTypeCode);
        tvBoxTypeName.setText(boxTypeName);

        tvTitle.setText(storeName);

       /* BoxCheckInfoRequest request=new BoxCheckInfoRequest();
        request.setBoxTypeCode(boxTypeCode);
        request.setCustomerCode(customerCode);
        request.setStoredCode(storeCode);
        request.setStatus(0);

        Gson gson = new Gson();
        String json=gson.toJson(request);

        Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxCheckInfo/getBoxCheckInfo",RequestMethod.POST);
        boxTypeRequest.setDefineRequestBodyForJson(json);


        CallServer.getInstance().add(BoxSendNumActivity.this, Box_CHCEK_REQUEST, boxTypeRequest, this, true, true);*/


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
                    ToastUtil.show(BoxSendNumActivity.this,"数量不能为空!");
                    return;
                }
                if (!Util.isNumeric(etNum.getText().toString()))
                {
                    ToastUtil.show(BoxSendNumActivity.this,"数量只能输入数字!");
                    return;
                }

                BoxOutDetailRequest request=new BoxOutDetailRequest();
                request.setBoxTypeCode(boxTypeCode);
                request.setBoxTypeName(tvBoxTypeName.getText().toString());
                request.setCustomerCode(customerCode);
                request.setCustomerName(customerName);
                request.setStoredCode(storeCode);
                request.setStoredName(storeName);
                request.setCreateUser(AppConstant.User_Name);
                request.setUpdateUser(AppConstant.User_Name);
                request.setLineCode(lineCode);
                request.setLineName(lineName);
                request.setIsFresh(isFresh);
                request.setWarehouseCode(AppConstant.WareHouse_Code);
                request.setWarehouseName(AppConstant.WareHouse_Name);

                request.setRealityNum(new BigDecimal(etNum.getText().toString()));
                Gson gson = new Gson();
                String json=gson.toJson(request);

                Request<String> boxTypeRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"/boxOutDetail/add",RequestMethod.POST);
                boxTypeRequest.setDefineRequestBodyForJson(json);


                CallServer.getInstance().add(BoxSendNumActivity.this, Box_OUT_ADD_REQUEST, boxTypeRequest, this, true, true);


                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {

            case  Box_OUT_ADD_REQUEST:
                BoxOutDetailAddResponse boxOutDetailAddResponse=gson.fromJson(response.get(),BoxOutDetailAddResponse.class);
                if (boxOutDetailAddResponse!=null&&boxOutDetailAddResponse.getCode().equals("200"))
                {
                    ToastUtil.show(BoxSendNumActivity.this,"提交成功");
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                }
                else
                {
                    ToastUtil.show(BoxSendNumActivity.this,"提交失败");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }


}
