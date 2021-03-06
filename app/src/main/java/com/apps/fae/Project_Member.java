package com.apps.fae;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Project_Member extends AppCompatActivity {

    List<Member_Item> Member_List = new ArrayList<Member_Item>();
    List<Member_Item> GroupItem = new ArrayList<Member_Item>();
    private ListView lsv_main;
    private MemberAdapter mListAdapter;

    private ProgressDialog pDialog;

    String ModelName;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.apps.fae.R.layout.activity_project_member);
        pDialog = new ProgressDialog(Project_Member.this);
        //View v = inflater.inflate(R.layout.fragment_my_issue, container, false);
        //宣告 ListView 元件
        lsv_main = (ListView) findViewById(com.apps.fae.R.id.listView);

        lsv_main.setOnItemClickListener(listViewOnItemClickListener);

        Bundle bundle = this.getIntent().getExtras();

        String ModelID = bundle.getString("ModelID");

        ModelName = bundle.getString("ModelName");

        if (!ModelID.matches((""))) {
            GetPM_MemberData(ModelID);
        }

        setTitle(ModelName);


    }

    private AdapterView.OnItemClickListener listViewOnItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };


    private void GetPM_MemberData(String PM_ID) {

        pDialog.setTitle("Loading...");

        pDialog.show();


        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(this);
        }
        String Path = GetServiceData.ServicePath + "/Find_Model_Member_List?PM_ID=" + PM_ID;

        GetServiceData.getString(Path, mQueue, new GetServiceData.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                MemberDataMapping(result);

                pDialog.hide();
            }
        });

    }

    private void MemberDataMapping(JSONObject result) {
        try {
            Member_List.clear();

            JSONArray UserArray = new JSONArray(result.getString("Key"));


            if (UserArray.length() > 0) {

                for (int i = 0; i < UserArray.length(); i++) {
                    JSONObject ModelData = UserArray.getJSONObject(i);

                    String MemberName = ModelData.getString("MemberName");

                    String Header = ModelData.getString("Header");

                    String F_Tel = ModelData.getString("F_Tel");

                    String WorkID = ModelData.getString("WorkID");

                    Member_List.add(i, new Member_Item(Header, MemberName, "", F_Tel, WorkID, false));
                }

                GroupItem = GroupItem();


                mListAdapter = new MemberAdapter(this, Mappingitem());

                //設定 ListView 的 Adapter
                lsv_main.setAdapter(mListAdapter);

                lsv_main.setEmptyView(findViewById(com.apps.fae.R.id.emptyview));

            } else {
                lsv_main.setEmptyView(findViewById(com.apps.fae.R.id.empty));
            }
        } catch (JSONException ex) {

        }
    }

    private List<Member_Item> Mappingitem() {
        List<Member_Item> NewMemberList = new ArrayList<Member_Item>();

        int i = 0;

        for (Member_Item d : GroupItem) {

            NewMemberList.add(i, new Member_Item(d.GetTitle(), "", "", "", "", true));

            i++;

            for (Member_Item e : Member_List) {

                if (e.GetTitle().equals(d.GetTitle())) {


                    NewMemberList.add(i, e);

                    i++;
                }
            }
        }

        return NewMemberList;
    }

    private List<Member_Item> GroupItem() {

        GroupItem.clear();

        int i = 0;

        for (Member_Item d : Member_List) {

            if (!CheckRepeat(GroupItem, d.GetTitle())) {

                GroupItem.add(i, new Member_Item(d.GetTitle(), "", "", "", "", true));

                i++;
            }
        }

        return GroupItem;
    }

    private boolean CheckRepeat(List<Member_Item> _GroupItem, String Value) {
        boolean Check = false;

        for (Member_Item d : _GroupItem) {

            if (d.GetTitle().matches(Value)) {
                Check = true;
            }
        }
        return Check;

    }

}
