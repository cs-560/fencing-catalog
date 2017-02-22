package io.github.cs_560.fencing_catalog;

import android.app.Application;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class catalogueItem{
    static int[] itemPicsIds = {0, R.mipmap.ic_mask, R.mipmap.ic_foilmask, R.mipmap.ic_sabremask, R.mipmap.ic_foil, R.mipmap.ic_sabre, R.mipmap.ic_epee, R.mipmap.ic_jacket, R.mipmap.ic_glove,
            R.mipmap.ic_pants, R.mipmap.ic_underarmprotector, R.mipmap.ic_chestprotectorwomens, R.mipmap.ic_chestprotector, R.mipmap.ic_foillame, R.mipmap.ic_sabrelame, R.mipmap.ic_bodycord,
            R.mipmap.ic_bodycord3, R.mipmap.ic_maskcord};
    static int itemStrings= R.array.catalogItems_array;
    public String name;
    public int id;
    public String desc;
    public int price100;
    public int pic_id;
    public catalogueItem(String input_string){
        String string;
        String[] parts = input_string.split("\\|");
        name = new String(parts[0]);
        id = Integer.parseInt(parts[1]);
        desc = new String(parts[2]);
        price100 = Integer.parseInt(parts[3]);
    }
    public catalogueItem(){
        name = null;
        id = 0;
        desc = null;
        price100 = 0;
    }
    public catalogueItem(catalogueItem src){
        name = new String(src.name);
        id = src.id;
        desc = new String(src.desc);
        price100 = src.price100;
    }
}
