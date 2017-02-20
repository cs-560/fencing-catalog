package io.github.cs_560.fencing_catalog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class catalogueItem{

    public String name;
    public int num;
    public String desc;
    public int price100;
    public int res_id;
    public catalogueItem(String input_string){
        String string;
        String[] parts = input_string.split("\\|");
        name = new String(parts[0]);
        num = Integer.parseInt(parts[1]);
        desc = new String(parts[2]);
        price100 = Integer.parseInt(parts[3]);
    }
    public catalogueItem(){
        name = null;
        num = 0;
        desc = null;
        price100 = 0;
    }
    public catalogueItem(catalogueItem src){
        name = new String(src.name);
        num = src.num;
        desc = new String(src.desc);
        price100 = src.price100;
    }
}
