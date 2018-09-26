package com.qimai.xinlingshou.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.ui.BaseActivity;
import com.qimai.xinlingshou.utils.Hint;
import com.qimai.xinlingshou.utils.Xutils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ReisterActivity extends BaseActivity implements View.OnClickListener{
    private TimeCount timeCount;
    private static final String TAG = "ReisterActivity";
    @BindView(R.id.Tvtoolbar)
     Toolbar toolbar;
    @BindView(R.id.et_register_mobile)
    EditText etRegistermobile;
    @BindView(R.id.et_register_yzm)
    EditText etRegisteryzm;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterpwd;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.bt_get_yzm)
    Button Btgetyzm;

    @Override
    protected void initData() {
       // toolbar.setNavigationOnClickListener(this);
        timeCount = new TimeCount(60000, 1000,Btgetyzm);




    }

    @Override
    protected void initView(View rootView) {



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d(TAG, "onClick: navi");
            }
        });

    }



    @Override
    protected int setLayoutId() {
        return R.layout.register_activity;
    }

    @OnClick({R.id.Tvtoolbar,R.id.bt_register,R.id.bt_get_yzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Tvtoolbar:
                finish();
                break;
                case R.id.bt_register:

//
        if (TextUtils.isEmpty(etRegistermobile.getText().toString())){
            Hint.Short(ReisterActivity.this,"手机号不能为空！");
            return;
        } if (TextUtils.isEmpty(etRegisteryzm.getText().toString())){
            Hint.Short(ReisterActivity.this,"验证码不能为空！");
            return;
        }if (TextUtils.isEmpty(etRegisterpwd.getText().toString())){
            Hint.Short(ReisterActivity.this,"密码不能为空！");
            return;
        }
        sendRegister(etRegistermobile.getText().toString(),etRegisterpwd.getText().toString(),etRegisteryzm.getText().toString());
                    break;
                case R.id.bt_get_yzm:
                 if (TextUtils.isEmpty(etRegistermobile.getText().toString())){
                            Hint.Short(ReisterActivity.this,"手机号不能为空！");
                            return;
                        }
                    sendCode();
//                    Hint.Short(ReisterActivity.this, etRegistermobile.getText().toString());

                    break;
        }
        }


    @Override
    public void onClick(View v) {

    }
    public void sendRegister(String mobile ,String password,String codeValue){
        String url = App.API_URL+"seller/account/register";
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("mobile", mobile);
        stringMap.put("password",password);
        stringMap.put("codeValue", codeValue);
        stringMap.put("from_tag", "");
        stringMap.put("sem", "");
        Xutils.getInstance().post(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
//
                try {
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
//
                        startActivity(new Intent(ReisterActivity.this,LoginActivity.class));
                    finish();
                    }else {
                        Hint.Short(ReisterActivity.this,message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

        });

    }
    public void sendCode(){
        String url = App.API_URL+"seller/account/send-sms";
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("mobile", etRegistermobile.getText().toString());
        Xutils.getInstance().post(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
//
                try {
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
                        JSONObject mjsonObject =mjsonObjects.getJSONObject("data");
                        Hint.Short(ReisterActivity.this,"短信已发送，请注意查收");
                        timeCount.start();
//                        Hint.Short(ReisterActivity.this,mjsonObject.getString("expiredAt"));
                    }else {
                        Hint.Short(ReisterActivity.this,message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

        });

    }
    /**
     *  点击发送按钮后手机会接收到验证码，倒计时60s
     * @ClassName: TimeCount
     * @date 2017年1月10日
     */
    public class TimeCount extends CountDownTimer {

        private Button btn_count;

        public TimeCount(long millisInFuture, long countDownInterval,Button btn_count) {
            super(millisInFuture, countDownInterval);
            this.btn_count = btn_count;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_count.setEnabled(false);
            btn_count.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btn_count.setEnabled(true);
            btn_count.setText("重新发送");

        }

    }

}
