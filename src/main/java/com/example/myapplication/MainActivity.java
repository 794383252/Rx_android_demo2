package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.loginUtils.loginUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button;
    private EditText userName;
    private EditText password;
    private ProgressDialog dialog;
    private loginUtils utils;

    private static final String LOGIN = "http://192.168.219.28/TestServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        utils = new loginUtils();

        initdialog();
        button = (Button) findViewById(R.id.button);
        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

        button.setOnClickListener(this);
    }

    public void initdialog() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("登录");
    }

    @Override
    public void onClick(View v) {
        Log.i("ln", "执行点击事件");
        Map<String, String> params = new HashMap<>();
        params.put("username", userName.getText().toString().trim());
        params.put("password", password.getText().toString().trim());
        utils.login(LOGIN, params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("ln", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                dialog.show();
            }
        });
    }
}
