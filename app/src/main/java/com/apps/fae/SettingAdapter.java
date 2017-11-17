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
 * Created by Yu on 2017/11/13.
 */

public class SettingAdapter extends BaseAdapter {

    private LayoutInflater mLayInf;

    private List<Setting_Item> Setting_List;

    public SettingAdapter(Context context, List<Setting_Item> Setting_List) {
        mLayInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.Setting_List = Setting_List;
    }

    @Override
    public int getCount() {
        return Setting_List.size();
    }

    @Override
    public Object getItem(int position) {
        return Setting_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mLayInf.inflate(R.layout.setting_item, parent, false);
        ImageView Setting_image =(ImageView) v.findViewById(R.id.Setting_image);
        ImageView Next_image =(ImageView) v.findViewById(R.id.Next_image);
        TextView txt_Setting_main = (TextView) v.findViewById(R.id.txt_Setting_main);
        TextView txt_Setting_sub = (TextView) v.findViewById(R.id.txt_Setting_sub);



//        GetServiceData.GetUserPhoto(Notification_List.get(position).GetAuthor_WorkID(),Img_Notification_Author);
        Setting_image.setImageResource(Setting_List.get(position).Setting_image());
        Next_image.setImageResource(Setting_List.get(position).Next_image());
        txt_Setting_main.setText(Setting_List.get(position).Setting_main());
        txt_Setting_sub.setText(Setting_List.get(position).Setting_sub());


        return v;
    }
}
