package com.myxh.tms.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.myxh.tms.R;
import com.myxh.tms.common.AppConstant;
import com.myxh.tms.common.DownLoadManager;
import com.myxh.tms.entity.VersionInfo;
import com.myxh.tms.entity.WareHouse;
import com.myxh.tms.listener.TextInputWatcher;
import com.myxh.tms.network.CallServer;
import com.myxh.tms.network.HttpListener;
import com.myxh.tms.request.LoginRequest;
import com.myxh.tms.request.VersionInfoRequest;
import com.myxh.tms.request.WareRequest;
import com.myxh.tms.response.UserResponse;
import com.myxh.tms.response.VersionResponse;
import com.myxh.tms.response.WareResponse;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.ApkVersionCodeUtils;
import com.myxh.tms.util.ToastUtil;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class LoginActivity extends BaseActivity implements View.OnClickListener,HttpListener<String>, PlatformActionListener, Handler.Callback {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final int LOGIN_RESULT_CODE = 1002;

    private Spinner mloginWareSpinner;

    private ArrayAdapter<WareHouse> arrWareAdapter;

    private  String wareCode;

    private String wareName;

    private String serviceUrl;

    private List<WareHouse> wareList;

    @Override
    public void onSucceed(int what, Response<String> response) {

        Gson gson = new Gson();
        switch (what){
            case  1:
                Logger.i(response.get());

                UserResponse userInfo = gson.fromJson(response.get(),UserResponse.class);
                if(userInfo!=null&&userInfo.getCode().equals("200"))
                {
                    ToastUtil.show(LoginActivity.this,R.string.login_success);

                    AppConstant.BASE_URL=serviceUrl;
                    AppConstant.WareHouse_Code=wareCode;
                    AppConstant.WareHouse_Name=wareName;
                    AppConstant.User_Name=userInfo.getResult().getName();

                    openActivity(MainActivity.class);
                    finish();
                }
                else
                {

                    ToastUtil.show(LoginActivity.this,R.string.login_failed);
                }
                break;
            case  2:

                WareResponse wareResponse = gson.fromJson(response.get(),WareResponse.class);
                if(wareResponse!=null&&wareResponse.getCode().equals("200"))
                {
                    wareList=wareResponse.getResult();
                }
                else
                {
                    wareList=new ArrayList<>();
                }
                arrWareAdapter= new ArrayAdapter<WareHouse>(this,
                        android.R.layout.simple_spinner_item, wareList);

                arrWareAdapter
                        .setDropDownViewResource(R.layout.ware_item);
                // 加载适配器
                mloginWareSpinner.setAdapter(arrWareAdapter);
                mloginWareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        wareCode = ((WareHouse) mloginWareSpinner.getSelectedItem())
                                .getWarehouseCode();
                        wareName = ((WareHouse) mloginWareSpinner.getSelectedItem())
                                .getWarehouseName();

                        serviceUrl=((WareHouse) mloginWareSpinner.getSelectedItem())
                                .getServiceUrl();
				/*Toast.makeText(getApplicationContext(), String.valueOf(ids),
						Toast.LENGTH_LONG).show();*/


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                break;
            case 3:
                VersionResponse versionResponse = gson.fromJson(response.get(),VersionResponse.class);
                if(versionResponse!=null&&versionResponse.getCode().equals("200"))
                {

                    VersionInfo versionInfo=versionResponse.getResult();
                    if (versionInfo!=null)
                    {
                        if (!versionInfo.getVersionCode().equals(ApkVersionCodeUtils.getVerName(getApplicationContext()))) {
                            if (!versionInfo.getUrl().equals(""))
                            {
                                //下载升级包
                                //downLoadApk(versionInfo.getUrl());
                                showUpdataDialog(versionInfo.getUrl());
                            }


                        }
                    }
                }
                else
                {
                    ToastUtil.show(LoginActivity.this,"获取版本信息失败");
                }
                break;

        };

    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(LoginActivity.this,R.string.login_failed);
    }

    /**第三方登录回调标识**/
    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR= 3;
    private static final int MSG_AUTH_COMPLETE = 4;
    private static final int MSG_USERID_FOUND = 5;

    private ImageView mTitleBarIvBack;
    private TextView mTitleBarTvRegister;
    private TextView mSelectTvQuickLogin;
    private TextView mSelectTvAccountLogin;
    private View mSelectLeftLine;
    private View mSelectRightLine;
    private EditText mQuickLoginEtPhoneNumber;
    private ImageView mQuickLoginIvClearPhoneNumber;
    private EditText mQuickLoginEtCode;
    private Button mQuickLoginBtnGetCode;
    private ImageView mQuickLoginIvClearCode;
    private EditText mEtCheckPicture;
    private ImageView mIvCheckPicture;
    private RelativeLayout mLlCheckPicture;
    private LinearLayout mQuickLoginLayout;
    private EditText mAccountLoginEtUsername;
    private ImageView mAccountLoginIvClearUsername;
    private EditText mAccountLoginEtPassword;
    private CheckBox mAccountLoginCheckBox;
    private ImageView mAccountLoginIvClearPassword;
   /* private EditText mEtCheckCode;
    private ImageView mIvCheckCode;
    private RelativeLayout mLlCheckCode;*/
    private LinearLayout mAccountLoginLayout;
    private Button mQuickLoginBtn;
    private Button mAccountLoginBtn;
   /* private TextView mAccountLoginTvForgetPassword;
    private ImageView mBottomIvQq;
    private ImageView mBottomIvWechat;
    private ImageView mBottomIvWeibo;
    private ImageView mBottomIvAlipay;*/

    private Animation mLeftLineAnimation;
    private Animation mRightLineAnimation;

    private int mSelectedTextColor;
    private int mUnselectedTextColor;

    /**快速登陆界面*/
    private boolean isPhoneNumberNull = true;
    private boolean isCodeNull = true;
    /**账号登陆界面*/
    private boolean isUserNameNull = true;
    private boolean isPasswordNull = true;

   // private boolean isQuickLoginSelected = true;
    private boolean isAccountLoginSelected = false;
    private int mSecCount;
   // private String mPhoneNumber;

    private Handler mHandler;

    private  TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    break;
                }
            }
        }
        initView();
        initData();
        initAnimation();
        setViewListener();

        ShareSDK.initSDK(this);
        mHandler = new Handler(this);
    }

    protected void downLoadApk(final String url) {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(
                            url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {


                    pd.dismiss(); // 结束掉进度条对话框
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()),
                "application/vnd.android.package-archive");

        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    protected void showUpdataDialog(final String url) {
        AlertDialog.Builder builer = new AlertDialog.Builder(LoginActivity.this);
        builer.setTitle("版本升级");
        builer.setMessage("发现新版本,请升级");
        // 当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                downLoadApk(url);
            }
        });

        // 当点取消按钮时进行登录
		/*builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// LoginMain();
			}
		});*/
        builer.setCancelable(false);


        AlertDialog dialog = builer.create();
        dialog.show();
    }

    private  void  initData()
    {
        WareRequest request=new WareRequest();
        Gson gson = new Gson();
        String json=gson.toJson(request);
        Request<String> wareRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"warehouseInfo/getAllWarehouse",RequestMethod.POST);
        wareRequest.setDefineRequestBodyForJson(json);
        CallServer.getInstance().add(LoginActivity.this, 2, wareRequest, this, true, true);

        VersionInfoRequest versionInfoRequest=new VersionInfoRequest();
        versionInfoRequest.setSystemCode("4");
        String versionJson=gson.toJson(versionInfoRequest);
        Request<String> versionRequest = NoHttp.createStringRequest(AppConstant.BASE_URL+"versionInfo/getVersionInfoByCode",RequestMethod.POST);
        versionRequest.setDefineRequestBodyForJson(versionJson);
        CallServer.getInstance().add(LoginActivity.this, 3, versionRequest, this, true, true);


         String versionName=ApkVersionCodeUtils.getVerName(getApplicationContext());
        tvVersionName.setText("Version "+versionName);
    }

    private void setViewListener() {
        //顶部选择
       // mTitleBarIvBack.setOnClickListener(this);
       // mTitleBarTvRegister.setOnClickListener(this);
       // mSelectTvQuickLogin.setOnClickListener(this);
      //  mSelectTvAccountLogin.setOnClickListener(this);
        //快速登录
       /* mQuickLoginIvClearPhoneNumber.setOnClickListener(this);
        mQuickLoginBtnGetCode.setOnClickListener(this);
        mQuickLoginIvClearCode.setOnClickListener(this);
        mQuickLoginBtn.setOnClickListener(this);*/
        //账号登录
        mAccountLoginIvClearUsername.setOnClickListener(this);
        mAccountLoginIvClearPassword.setOnClickListener(this);
        mAccountLoginBtn.setOnClickListener(this);
      //  mAccountLoginTvForgetPassword.setOnClickListener(this);
        //第三方登录
      /*  mBottomIvQq.setOnClickListener(this);
        mBottomIvWechat.setOnClickListener(this);
        mBottomIvWeibo.setOnClickListener(this);
        mBottomIvAlipay.setOnClickListener(this);*/

       mAccountLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                //切换明密文
                if (checked) {
                    mAccountLoginEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mAccountLoginEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //光标在末尾显示
                mAccountLoginEtPassword.setSelection(mAccountLoginEtPassword.length());
            }
        });

     /*   mQuickLoginEtPhoneNumber.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                isPhoneNumberNull = TextUtils.isEmpty(mQuickLoginEtPhoneNumber.getText());
                mQuickLoginIvClearPhoneNumber.setVisibility(isPhoneNumberNull ? View.GONE : View.VISIBLE);
                mQuickLoginIvClearPhoneNumber.setEnabled(!isPhoneNumberNull);
                mQuickLoginBtn.setEnabled((isPhoneNumberNull||isCodeNull) ? false : true);
            }
        });*/

       /* mQuickLoginEtCode.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                isCodeNull = TextUtils.isEmpty(mQuickLoginEtCode.getText());
                mQuickLoginIvClearCode.setVisibility(isCodeNull ? View.GONE : View.VISIBLE);
                mQuickLoginIvClearCode.setEnabled(!isCodeNull);
                mQuickLoginBtn.setEnabled((isPhoneNumberNull||isCodeNull) ? false : true);
            }
        });*/

        mAccountLoginEtUsername.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                isUserNameNull = TextUtils.isEmpty(mAccountLoginEtUsername.getText());
                mAccountLoginIvClearUsername.setVisibility(isUserNameNull ? View.GONE : View.VISIBLE);
                mAccountLoginIvClearUsername.setEnabled(!isUserNameNull);
                mAccountLoginBtn.setEnabled((isUserNameNull||isPasswordNull) ? false : true);
            }
        });

        mAccountLoginEtPassword.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                isPasswordNull = TextUtils.isEmpty(mAccountLoginEtPassword.getText());
                mAccountLoginIvClearPassword.setVisibility(isPasswordNull ? View.GONE : View.VISIBLE);
                mAccountLoginIvClearPassword.setEnabled(!isPasswordNull);
                mAccountLoginBtn.setEnabled((isUserNameNull||isPasswordNull) ? false : true);
            }
        });
    }

    private void initAnimation() {
        mSelectedTextColor = getResources().getColor(R.color.app_yellow);
        mUnselectedTextColor = getResources().getColor(R.color.content_color);
        mLeftLineAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_line_move_right);
        mLeftLineAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSelectTvQuickLogin.setTextColor(mUnselectedTextColor);
                mSelectTvAccountLogin.setTextColor(mSelectedTextColor);
                mQuickLoginLayout.setVisibility(View.GONE);
                mAccountLoginLayout.setVisibility(View.VISIBLE);
                mSelectLeftLine.setVisibility(View.INVISIBLE);
                mSelectRightLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRightLineAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_line_move_left);
        mRightLineAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSelectTvQuickLogin.setTextColor(mSelectedTextColor);
                mSelectTvAccountLogin.setTextColor(mUnselectedTextColor);
                mQuickLoginLayout.setVisibility(View.VISIBLE);
                mAccountLoginLayout.setVisibility(View.GONE);
                mSelectLeftLine.setVisibility(View.VISIBLE);
                mSelectRightLine.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        mloginWareSpinner=(Spinner) findViewById(R.id.login_ware_spinner);

       /* mTitleBarIvBack = (ImageView) findViewById(R.id.login_titleBar_iv_back);
        mTitleBarTvRegister = (TextView) findViewById(R.id.login_titleBar_tv_register);
        mSelectTvQuickLogin = (TextView) findViewById(R.id.login_select_tv_quickLogin);*/
     //   mSelectTvAccountLogin = (TextView) findViewById(R.id.login_select_tv_accountLogin);
       // mSelectLeftLine = findViewById(R.id.login_select_left_line);
        mSelectRightLine = findViewById(R.id.login_select_right_line);
       /* mQuickLoginEtPhoneNumber = (EditText) findViewById(R.id.login_quick_login_et_phoneNumber);
        mQuickLoginIvClearPhoneNumber = (ImageView) findViewById(R.id.login_quick_login_iv_clear_phoneNumber);
        mQuickLoginEtCode = (EditText) findViewById(R.id.login_quick_login_et_code);
        mQuickLoginBtnGetCode = (Button) findViewById(R.id.login_quick_login_btn_getCode);
        mQuickLoginIvClearCode = (ImageView) findViewById(R.id.login_quick_login_iv_clear_code);
        mEtCheckPicture = (EditText) findViewById(R.id.et_check_picture);
        mIvCheckPicture = (ImageView) findViewById(R.id.iv_check_picture);
        mLlCheckPicture = (RelativeLayout) findViewById(R.id.ll_check_picture);
        mQuickLoginLayout = (LinearLayout) findViewById(R.id.login_quick_login_layout);*/
        mAccountLoginEtUsername = (EditText) findViewById(R.id.login_account_login_et_username);
        mAccountLoginIvClearUsername = (ImageView) findViewById(R.id.login_account_login_iv_clear_username);
        mAccountLoginEtPassword = (EditText) findViewById(R.id.login_account_login_et_password);
        mAccountLoginCheckBox = (CheckBox) findViewById(R.id.login_account_login_checkBox);
        mAccountLoginIvClearPassword = (ImageView) findViewById(R.id.login_account_login_iv_clear_password);
        /*mEtCheckCode = (EditText) findViewById(R.id.et_check_code);
        mIvCheckCode = (ImageView) findViewById(R.id.iv_check_code);
        mLlCheckCode = (RelativeLayout) findViewById(R.id.ll_check_code);*/
        mAccountLoginLayout = (LinearLayout) findViewById(R.id.login_account_login_layout);
       // mQuickLoginBtn = (Button) findViewById(R.id.login_quick_login_btn);
        mAccountLoginBtn = (Button) findViewById(R.id.login_account_login_btn);
       /* mAccountLoginTvForgetPassword = (TextView) findViewById(R.id.login_account_login_tv_forget_password);
        mBottomIvQq = (ImageView) findViewById(R.id.login_bottom_iv_qq);
        mBottomIvWechat = (ImageView) findViewById(R.id.login_bottom_iv_wechat);
        mBottomIvWeibo = (ImageView) findViewById(R.id.login_bottom_iv_weibo);
        mBottomIvAlipay = (ImageView) findViewById(R.id.login_bottom_iv_alipay);*/
        tvVersionName=(TextView) findViewById(R.id.tvVersionName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.login_titleBar_iv_back:
                finish();
                break;
            case R.id.login_titleBar_tv_register:
                openActivity(RegisterActivity.class);
                break;
            case R.id.login_select_tv_quickLogin:
                if (!isQuickLoginSelected) {
                    mSelectRightLine.startAnimation(mRightLineAnimation);
                    isQuickLoginSelected = true;
                    isAccountLoginSelected = false;
                }
                break;*/
           /* case R.id.login_select_tv_accountLogin:
                if (!isAccountLoginSelected) {
                    mSelectLeftLine.startAnimation(mLeftLineAnimation);
                    isAccountLoginSelected = true;
                  //  isQuickLoginSelected = false;
                }
                break;*/
           /* case R.id.login_quick_login_iv_clear_phoneNumber:
                mQuickLoginEtPhoneNumber.setText("");
                mQuickLoginIvClearPhoneNumber.setVisibility(View.GONE);
                break;
            case R.id.login_quick_login_iv_clear_code:
                mQuickLoginEtCode.setText("");
                mQuickLoginIvClearCode.setVisibility(View.GONE);
                break;
            case R.id.login_quick_login_btn_getCode:
                mPhoneNumber = mQuickLoginEtPhoneNumber.getText().toString();
                if (LoginHelperUtil.isPhoneNumber(mPhoneNumber)) {
                    BmobManager.getInstance(new BmobMsgSendCallback() {
                        @Override
                        public void onMsgSendSuccess() {
                            ToastUtil.show(LoginActivity.this,R.string.sms_code_send_success);
                            //验证码发送成功，倒计时
                            setCodeTimeDown();
                        }

                        @Override
                        public void onMsgSendFailure() {
                            ToastUtil.show(LoginActivity.this,R.string.sms_code_send_failure);
                        }
                    }).sendMsgCode(mPhoneNumber);
                } else {
                    ToastUtil.show(this,R.string.phone_number_incorrect);
                }
                break;*/
           /* case R.id.login_quick_login_btn:
                mPhoneNumber = mQuickLoginEtPhoneNumber.getText().toString();
                String code = mQuickLoginEtCode.getText().toString();
                if (LoginHelperUtil.isCodeCorrect(code) && LoginHelperUtil.isPhoneNumber(mPhoneNumber)) {
                    BmobManager.getInstance(new BmobLoginCallback() {
                        @Override
                        public void onLoginSuccess() {
                            Log.i(TAG, "onLoginSuccess: 登陆成功");
                            ToastUtil.show(LoginActivity.this,R.string.login_success);
                        }

                        @Override
                        public void onLoginFailure() {
                            Log.i(TAG, "onLoginFailure: 登陆失败");
                            ToastUtil.show(LoginActivity.this,R.string.login_failed);
                        }
                    }).signOrLoginByMsgCode(mPhoneNumber,code);
                } else {
                    ToastUtil.showLong(this,R.string.quick_login_input_incorrect);
                }
                break;*/
            case R.id.login_account_login_iv_clear_username:
                mAccountLoginEtUsername.setText("");
                mAccountLoginIvClearUsername.setVisibility(View.GONE);
                break;
            case R.id.login_account_login_iv_clear_password:
                mAccountLoginEtPassword.setText("");
                mAccountLoginIvClearPassword.setVisibility(View.GONE);
                break;
            case R.id.login_account_login_btn:
                String username = mAccountLoginEtUsername.getText().toString();
                String password = mAccountLoginEtPassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                    String searchUrl =AppConstant.BASE_URL_LOGIN;

                    LoginRequest request = new LoginRequest();
                    request.setName(username);
                    request.setPassword(password);
                    request.setWarehouseCode(wareCode);
                    Gson gson = new Gson();
                    String json=gson.toJson(request);

                    Request<String> loginRequest = NoHttp.createStringRequest(searchUrl,RequestMethod.POST);
                    loginRequest.setDefineRequestBodyForJson(json);

                    CallServer.getInstance().add(LoginActivity.this, 1, loginRequest, this, true, true);


               /*     Request<String> filmRequest = NoHttp.createStringRequest(AppConstant.HOT_FILM_URL,RequestMethod.GET);

                    CallServer.getInstance().add(LoginActivity.this, 1, filmRequest, new OnResponseListener<String>() {
                    }, true, true);*/
                   /* BmobManager.getInstance(new BmobLoginCallback() {
                        @Override
                        public void onLoginSuccess() {
                            ToastUtil.show(LoginActivity.this,R.string.login_success);
                            Intent data = new Intent();
                            setResult(LOGIN_RESULT_CODE,data);
                            finish();
                        }
                        @Override
                        public void onLoginFailure() {
                            ToastUtil.show(LoginActivity.this,R.string.login_failed);
                        }
                    }).login(username,password);*/
                } else {
                    ToastUtil.show(this,R.string.login_input_empty);
                }
                break;
           /* case R.id.login_account_login_tv_forget_password:

                break;
            case R.id.login_bottom_iv_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
            case R.id.login_bottom_iv_wechat:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                break;
            case R.id.login_bottom_iv_weibo:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                *//*Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
                weibo.setPlatformActionListener(this); // 设置分享事件回调
                weibo.authorize();//单独授权
                weibo.showUser(null);//授权并获取用户信息*//*
                break;
            case R.id.login_bottom_iv_alipay:
                break;*/
            default:
                break;
        }
    }

    /**
     * 执行授权,获取用户信息
     * @param plat
     */
    private void authorize(Platform plat) {
        if (plat == null) {
//            popupOthers();
            return;
        }
        //判断指定平台是否已经完成授权
        if(plat.isAuthValid()) {
            ToastUtil.show(this,"已经授权");
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
//                login(plat.getName(), userId, null);
                plat.removeAccount(true);
                return;
            }
        }

        plat.setPlatformActionListener(this);
        //开启SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    /**
     * 验证码倒计时
     */
    private void setCodeTimeDown() {
        mQuickLoginBtnGetCode.setEnabled(false);
        final Timer timer = new Timer();
        mSecCount = 60;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSecCount--;
                        mQuickLoginBtnGetCode.setText(mSecCount+" s");
                        if (mSecCount <= 0) {
                            timer.cancel();
                            mQuickLoginBtnGetCode.setText(getString(R.string.reSend));
                            mQuickLoginBtnGetCode.setEnabled(true);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,1000,1000);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[] {platform.getName(), res};
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        platform.removeAccount(true);
        if (action == Platform.ACTION_USER_INFOR) {
            mHandler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
    }

    @Override
    public void onCancel(Platform platform, int action) {
        platform.removeAccount(true);
        if (action == Platform.ACTION_USER_INFOR) {
            mHandler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_AUTH_CANCEL: {
                //取消授权
                ToastUtil.show(this,R.string.auth_cancel);
            }
            break;
            case MSG_AUTH_ERROR: {
                //授权失败
                ToastUtil.show(this,R.string.auth_error);
            }
            break;
            case MSG_AUTH_COMPLETE: {
                //授权成功
                ToastUtil.show(this,R.string.auth_success);
                Object[] objs = (Object[]) msg.obj;
                String platform = (String) objs[0];
                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
                Iterator<Map.Entry<String, Object>> iterator = res.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    Log.i(TAG, "handleMessage: key="+key.toString()+"  value="+value.toString());
                }
                ToastUtil.show(this,platform+"::::");
            }
            break;
        }
        return false;
    }
}
