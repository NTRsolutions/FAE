package com.apps.fae;

import java.net.URI;

/**
 * Created by androids on 2016/10/21.
 */
public class Setting_Item {
    String ID;
    int Setting_image;
    int Next_image;
    String Setting_main;
    String Setting_sub;




    public Setting_Item(int Setting_image,String Setting_main, String Setting_sub,int Next_image) {

        this.Setting_image = Setting_image;
        this.Next_image = Next_image;
        this.Setting_main = Setting_main;
        this.Setting_sub = Setting_sub;

    }
    public int Setting_image() {
        return this.Setting_image;
    }
    public int Next_image() {
        return this.Next_image;
    }
    public String Setting_main() {
        return this.Setting_main;
    }
    public String Setting_sub() {
        return this.Setting_sub;
    }

    public String GetID() {
        return this.Setting_main;
    }


}
