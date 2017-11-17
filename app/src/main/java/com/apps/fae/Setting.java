package com.apps.fae;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.request.animation.GlideAnimation;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Setting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView lsv_main;
    private SettingAdapter mListAdapter;
    List<Setting_Item> Setting_List = new ArrayList<Setting_Item>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LayoutInflater minflater;

    private ViewGroup mcontainer;

    private Myhistory.OnFragmentInteractionListener mListener;

    private View mView;
    private RequestQueue mQueue;

    public Setting() {
        // Required empty public constructor
    }
    //ListView 要顯示的內容　改到全域變數
    public String[][] str  = {{"Current Version","1.2.0"},{"Update Version","1.0.0"},{"Notifications","ON"},{"Font Size","Medium"},{"Feedback",""}};

    public int[][] image  = {{R.mipmap.ic_setting_version,0},{R.mipmap.ic_setting_updateversion,0},{R.mipmap.ic_setting_notifications,0},{R.mipmap.ic_setting_fontsize,R.mipmap.btn_setting_common_arrow},{R.mipmap.ic_setting_feedback,R.mipmap.btn_setting_common_arrow}};

    private LayoutInflater mLayInf;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      //super.onCreate(savedInstanceState);

//        Intent intent = new Intent(getActivity(), feedback.class);
//
//
//        // 呼叫「startActivity」，參數為一個建立好的Intent物件
//        // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
//        startActivity(intent);


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

        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        //宣告 ListView 元件
        lsv_main = (ListView) mView.findViewById(R.id.list_view);

//        View v = mLayInf.inflate(R.layout.fragment_settings, parent, false);
//        Button Test = (Button)  v.findViewById(R.id.button3);
//        Test.OnClickListener();





//        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mSwipeRefreshLayout.setRefreshing(false);
//
//
//            }
//        });
        Setting_List.clear();
        for (int i=0; i<str.length; ++i)
        {
            Setting_List.add(0, new Setting_Item(image[i][0],str[i][0],str[i][1],image[i][1]));
        }
        //Setting_List.add(0,new Setting_Item("Test Main","Test sub"));
        mListAdapter = new SettingAdapter(getActivity(), Setting_List);
        lsv_main.setAdapter(mListAdapter);

        lsv_main.setOnItemClickListener(listViewOnItemClickListener);


        return mView;
    }

    /***
     * 點擊ListView事件Method
     */
    private AdapterView.OnItemClickListener listViewOnItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SettingAdapter SettingAdapter = (SettingAdapter) parent.getAdapter();

            Setting_Item Setting_Item = (Setting_Item) SettingAdapter.getItem(position);

            if(Setting_Item.GetID()=="Feedback"){
            Intent intent = new Intent(getActivity(), feedback.class);
            // 呼叫「startActivity」，參數為一個建立好的Intent物件
            // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
            startActivity(intent);}
            else if(Setting_Item.GetID()=="Font Size"){
                Intent intent = new Intent(getActivity(), fontsize.class);
                // 呼叫「startActivity」，參數為一個建立好的Intent物件
                // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
                startActivity(intent);

            }
        }

    };

    private void GoToNext(Class page) {

        Bundle bundle = new Bundle();


        Intent intent = new Intent(getActivity(), page);

        intent.putExtras(bundle);
        // 呼叫「startActivity」，參數為一個建立好的Intent物件
        // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
        startActivity(intent);
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


}
