package com.apps.fae;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by androids on 2016/10/21.
 */
public class IssueAdapter extends BaseAdapter {

    private LayoutInflater mLayInf;

    private List<Issue_Item> Issue_List;

    private String AdapterType;

    private Context mContext;

    public IssueAdapter(Context context, List<Issue_Item> Issue_List, String AdapterType) {
        this.AdapterType = AdapterType;

        if (context != null) {
            mLayInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        mContext = context;

        this.Issue_List = Issue_List;
    }

    @Override
    public int getCount() {
        return Issue_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Issue_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mLayInf.inflate(com.apps.fae.R.layout.issue_item, parent, false);



        TextView txt_Issue_Project_Name = (TextView) v.findViewById(com.apps.fae.R.id.txt_Issue_Project_Name);

        TextView txt_Issue_Date = (TextView) v.findViewById(com.apps.fae.R.id.txt_Issue_Date);

        TextView txt_Issue_Subject = (TextView) v.findViewById(com.apps.fae.R.id.txt_Issue_Subject);

        TextView txt_Issue_WorkNoteCount = (TextView) v.findViewById(com.apps.fae.R.id.txt_Issue_WorkNoteCount);



//        GetServiceData.GetImageByImageLoad("http://172.16.111.114/File/SDQA/Code/Admin/10010670.jpg",Img_Priority);
        //Img_Priority.setImageResource(Integer.valueOf(Project_List.get(position).GetImage().toString()));

        if (AdapterType.equals("MyIssue")) {

            if (Issue_List.get(position).GetProjectName().toLowerCase().contains("ms-")) {
                txt_Issue_Project_Name.setText(Issue_List.get(position).GetProjectName());
            } else {
                txt_Issue_Project_Name.setText("MS-" + Issue_List.get(position).GetProjectName());
            }

        } else {
            txt_Issue_Project_Name.setText("#" + Issue_List.get(position).GetID());
        }

        txt_Issue_Date.setText(Issue_List.get(position).GetDate());

        txt_Issue_Subject.setText(Issue_List.get(position).GetSubject());



        if (Issue_List.get(position).GetRead().equals("0")) {
            txt_Issue_WorkNoteCount.setText("N");

            txt_Issue_WorkNoteCount.setTextColor(Color.parseColor("#ffffff"));
        } else {
            if (Issue_List.get(position).GetWorkNoteCount().equals("0")) {
                txt_Issue_WorkNoteCount.setVisibility(View.GONE);
            } else {
                txt_Issue_WorkNoteCount.setText(Issue_List.get(position).GetWorkNoteCount());
            }
        }




        LinearLayout IssueList_Background = (LinearLayout) v.findViewById(com.apps.fae.R.id.IssueList_Background);

        if (Issue_List.get(position).GetIssueStatus().equals("3")) {
            IssueList_Background.setBackgroundColor(mContext.getResources().getColor(com.apps.fae.R.color.Issue_Status_Close));
        }

        return v;
    }


}
