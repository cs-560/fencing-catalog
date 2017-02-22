package io.github.cs_560.fencing_catalog;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Li Yanyi on 2/21/2017.
 */

public class BaseActivity extends AppCompatActivity {
    static int[] itemPicsIds = {0, R.mipmap.ic_mask, R.mipmap.ic_foilmask, R.mipmap.ic_sabremask, R.mipmap.ic_foil, R.mipmap.ic_sabre, R.mipmap.ic_epee, R.mipmap.ic_jacket, R.mipmap.ic_glove,
            R.mipmap.ic_pants, R.mipmap.ic_underarmprotector, R.mipmap.ic_chestprotectorwomens, R.mipmap.ic_chestprotector, R.mipmap.ic_foillame, R.mipmap.ic_sabrelame, R.mipmap.ic_bodycord,
            R.mipmap.ic_bodycord3, R.mipmap.ic_maskcord};
    protected ArrayList<catalogueItem> itemsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createItemList();
    }

    protected void createItemList(){
        if(itemsList==null)
            itemsList = new ArrayList<catalogueItem>();
        Resources res = getResources();
        String[] catItems = res.getStringArray(R.array.catalogItems_array);
        for(String itemString:catItems) {
            catalogueItem an_item = new catalogueItem(itemString);
            if (an_item.id > 0 && an_item.id < itemPicsIds.length)
                an_item.pic_id = itemPicsIds[an_item.id];
            itemsList.add(an_item);
        }
    }
}
