package com.apps.fae;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Welcome extends Activity {


    private Context _Context;
    //private RequestQueue mQueue;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.apps.fae.R.layout.activity_welcome);

        mQueue = Volley.newRequestQueue(Welcome.this);
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

                //將登入資訊寫入DB
//                UserDB UserDB = new UserDB(getApplicationContext());
//
//                UserDB.insert(new UserData(Account, Password, WorkID, Name, Phone, Dept, Account, LastTab));

                //IP記錄功能


                if (mQueue == null) {
                    mQueue = Volley.newRequestQueue(Welcome.this);
                }

                String Path = GetServiceData.IPCount + "/Insert_IPCount";


                StringRequest mStringRequest = new StringRequest(Request.Method.POST,
                        Path, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Log.d("Response", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("F_Keyin", UserData.WorkID);
                        params.put("F_IP", getIPAddress(true));
                        params.put("F_Root", "FAE App Android");
                        params.put("F_Page", "FAE App Android");
                        params.put("F_Memo", "FAE App Android");
                        params.put("F_URL", "");

                        return params;
                    }

                };

                mQueue.add(mStringRequest);




                Intent intent = new Intent(Welcome.this, MainTab.class);

                startActivity(intent);

                finish();


            }
        }, 2000);//两秒后跳转到另一个页面


    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
