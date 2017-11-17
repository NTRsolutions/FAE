package com.apps.fae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by androids on 2016/10/21.
 */
public class MyHistoryAdapter extends BaseAdapter {

    private LayoutInflater mLayInf;

    private List<MyHistory_Item> MyHistory_List;

    public MyHistoryAdapter(Context context, List<MyHistory_Item> MyHistory_List) {
        mLayInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.MyHistory_List = MyHistory_List;
    }

    @Override
    public int getCount() {
        return MyHistory_List.size();
    }

    @Override
    public Object getItem(int position) {
        return MyHistory_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mLayInf.inflate(R.layout.myhistory_item, parent, false);




        TextView txt_MyHistory_Title = (TextView) v.findViewById(R.id.txt_MyHistory_Title);
        TextView txt_MyHistory_Content = (TextView) v.findViewById(R.id.txt_MyHistory_Content);
        TextView txt_MyHistory_Date = (TextView) v.findViewById(R.id.txt_MyHistory_Date);
        TextView txt_MyHistory_Desc = (TextView) v.findViewById(R.id.txt_MyHistory_Desc);

//        GetServiceData.GetUserPhoto(Notification_List.get(position).GetAuthor_WorkID(),Img_Notification_Author);

        txt_MyHistory_Title.setText(MyHistory_List.get(position).GetTitle());
        txt_MyHistory_Content.setText(MyHistory_List.get(position).GetContent());
        txt_MyHistory_Date.setText(MyHistory_List.get(position).GetDate());
        txt_MyHistory_Desc.setText(MyHistory_List.get(position).Get_Desc());

        return v;
    }

}
