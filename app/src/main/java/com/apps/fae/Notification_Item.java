package com.apps.fae;

/**
 * Created by androids on 2016/10/21.
 */
public class Notification_Item {
//Release info
    String Model_Title;

    String Type_Title;

    String Date;

    String Version;

//Product
    String Product_Title;

    String Product_Date;
//哪個分頁
    int Page;



    public Notification_Item(String Model_Title, String Type_Title, String Date, String Version, String Product_Title, String Product_Date, int Page) {
        this.Model_Title = Model_Title;

        this.Type_Title = Type_Title;

        this.Date = Date;

        this.Version = Version;

        this.Product_Title = Product_Title;

        this.Product_Date = Product_Date;

        this.Page = Page;

    }

    public String GetModel_Title() {
        return this.Model_Title;
    }

    public String GetType_Title() {
        return this.Type_Title;
    }

    public String GetDate() {
        return this.Date;
    }

    public String GetVersion() {
        return this.Version;
    }

    public String GetProduct_Title() {
        return this.Product_Title;
    }

    public String GetProduct_Date() {
        return this.Product_Date;
    }



    public int Get_Page() {
        return this.Page;
    }
}
