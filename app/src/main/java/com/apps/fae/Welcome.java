package com.apps.fae;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class Welcome extends Activity {


    private Context _Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.apps.fae.R.layout.activity_welcome);

//        AppClass.Get_Server_All_Image(this);

        FirebaseMessaging.getInstance().subscribeToTopic("dogs");
        //這裡來檢測版本是否需要更新
        _Context = this;

        String token = FirebaseInstanceId.getInstance().getToken();

        //Log.w("Token",token);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                UserData.WorkID = "10003275";
                UserData.Account ="iriswu";
                UserData.Dept="設計品質驗證三部";
                UserData.EName="iriswu";


                Intent intent = new Intent(Welcome.this, MainTab.class);

                startActivity(intent);

                finish();


            }
        }, 2000);//两秒后跳转到另一个页面


    }


}
