package com.apps.fae;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationContent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView lsv_main;
    private NotificationAdapter mListAdapter;
    private NotificationAdapter2 mListAdapter2;
    List<Notification_Item> Notification_List = new ArrayList<Notification_Item>();
    List<Notification_Item> Notification_List2 = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LayoutInflater minflater;

    private ViewGroup mcontainer;

    private OnFragmentInteractionListener mListener;

    private View mView;
    private RequestQueue mQueue;
    private Button Release_Info;
    private Button Product;
    private Boolean Button = true;

    public NotificationContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notification.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationContent newInstance(String param1, String param2) {
        NotificationContent fragment = new NotificationContent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mcontainer = container;

        minflater = inflater;

        mView = inflater.inflate(R.layout.fragment_notificationcontent, mcontainer, false);
        //宣告 ListView 元件
        lsv_main = (ListView) mView.findViewById(com.apps.fae.R.id.listView);




        Release_Info = (Button) mView.findViewById(R.id.Release_Info);
        Product= (Button) mView.findViewById(R.id.Product);
        Release_Info.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){
                // TODO Auto-generated method stub
                Release_Info.setBackground(getResources().getDrawable(R.drawable.notification_button));
                Product.setBackgroundColor(Color.parseColor("#00000000"));
                Button = true;
                if (!UserData.WorkID.matches("")) {

                    Find_Notification(UserData.WorkID);
                }
                lsv_main.setOnItemClickListener(listViewOnItemClickListener);
            }
        });
        Product.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){
                // TODO Auto-generated method stub
                Product.setBackground(getResources().getDrawable(R.drawable.notification_button));
                Release_Info.setBackgroundColor(Color.parseColor("#00000000"));
                Button = false;
                if (!UserData.WorkID.matches("")) {
                    Find_Notification(UserData.WorkID);
                }
                lsv_main.setOnItemClickListener(listViewOnItemClickListener2);

            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(com.apps.fae.R.id.refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);

                if (!UserData.WorkID.matches("")) {

                    Find_Notification(UserData.WorkID);
                }
            }
        });

        if (!UserData.WorkID.matches("")) {

            Find_Notification(UserData.WorkID);
        }



        return mView;
    }


    /***
     * 點擊ListView事件Method
     */
    private AdapterView.OnItemClickListener listViewOnItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            NotificationAdapter NotificationAdapter = (NotificationAdapter) parent.getAdapter();


            Notification_Item Notification_Item = (Notification_Item) NotificationAdapter.getItem(position);


            if(Notification_Item.GetType_Title()=="BIOS"){
                shareTo("Share BIOS","Share BIOS","");
            }
            else if(Notification_Item.GetType_Title()=="EC Fireware"){
                shareTo("Share EC Fireware","Share EC Fireware","");
            }
        }
    };

    private AdapterView.OnItemClickListener listViewOnItemClickListener2
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            NotificationAdapter2 NotificationAdapter2 = (NotificationAdapter2) parent.getAdapter();


            Notification_Item Notification_Item = (Notification_Item) NotificationAdapter2.getItem(position);


            if(Notification_Item.GetProduct_Title()=="電競熱銷超值大Fun送~"){
                shareTo("電競熱銷超值大Fun送~","電競熱銷超值大Fun送~","");
            }



        }

    };
    private void Find_Notification(String WorkID) {

        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(getActivity());
        }
        String Path = GetServiceData.ServicePath + "/Find_MobileSystemMessage";

        GetServiceData.getString(Path, mQueue, new GetServiceData.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {


                NotificationDataMapping(result);
            }
        });
    }

    private void NotificationDataMapping(JSONObject result) {
        try {
            Notification_List.clear();
            Notification_List2.clear();

            JSONArray UserArray = new JSONArray(result.getString("Key"));

            for (int i = 0; i < UserArray.length(); i++) {

                JSONObject ModelData = UserArray.getJSONObject(i);

                String F_Owner = "";

                String F_MsgType = ModelData.getString("Title");

                String F_Subject = ModelData.getString("F_Content");

                F_Subject = F_Subject.replace("<br />", "\n");

                String F_CreateDate = AppClass.ConvertDateString(ModelData.getString("F_CreateDate"));

                String F_Desc = ModelData.getString("F_Desc");

                F_Desc = Html.fromHtml(F_Desc).toString();

                //String F_Keyin = ModelData.getString("F_Keyin");

                //String F_Master_ID = String.valueOf(ModelData.getInt("F_Master_ID"));

                //Notification_List.add(i, new Notification_Item("", F_Owner, F_CreateDate, F_MsgType, F_Subject, F_Desc, "", ""));
            }

            if (Notification_List.size() > 0) {

            }
            Notification_List.add(0, new Notification_Item("GE63VR 7RE RAIDER", "BIOS", "2017/07/25", "版本:E16P1MS.102", "", "",0 ));
            Notification_List2.add(0, new Notification_Item("GE63VR 7RE RAIDER", "BIOS", "2017/07/25", "版本:E16P1MS.102", "電競熱銷超值大Fun送~", "2017/07/25",0 ));
            // ListView 中所需之資料參數可透過修改 Adapter 的建構子傳入
            mListAdapter = new NotificationAdapter(getActivity(), Notification_List);
            mListAdapter2 = new NotificationAdapter2(getActivity(), Notification_List2);

            lsv_main.setEmptyView(mView.findViewById(com.apps.fae.R.id.emptyview));

            if(Button){
                //設定 ListView 的 Adapter
                lsv_main.setAdapter(mListAdapter);

            }
            else {

                lsv_main.setAdapter(mListAdapter2);
            }



        } catch (JSONException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
        }

    }



    private void GoIssueInfo(String IssueID) {
        Bundle bundle = new Bundle();

        bundle.putString("IssueID", IssueID);
        // 建立啟動另一個Activity元件需要的Intent物件
        // 建構式的第一個參數：「this」
        // 建構式的第二個參數：「Activity元件類別名稱.class」
        Intent intent = new Intent(getContext(), IssueInfo.class);

        intent.putExtras(bundle);
        // 呼叫「startActivity」，參數為一個建立好的Intent物件
        // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
        startActivity(intent);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void shareTo(String subject, String body, String chooserTitle) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sharingIntent, chooserTitle));
    }
}
