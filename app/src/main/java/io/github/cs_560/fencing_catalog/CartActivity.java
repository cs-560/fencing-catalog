package io.github.cs_560.fencing_catalog;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    static int[] itemPicsIds = {0, R.mipmap.ic_mask, R.mipmap.ic_foilmask, R.mipmap.ic_sabremask, R.mipmap.ic_foil, R.mipmap.ic_sabre, R.mipmap.ic_epee, R.mipmap.ic_jacket, R.mipmap.ic_glove,
            R.mipmap.ic_pants, R.mipmap.ic_underarmprotector, R.mipmap.ic_chestprotectorwomens, R.mipmap.ic_chestprotector, R.mipmap.ic_foillame, R.mipmap.ic_sabrelame, R.mipmap.ic_bodycord,
            R.mipmap.ic_bodycord3, R.mipmap.ic_maskcord};
    private ArrayList<catalogueItem> itemsList;
    private ArrayList<cartItem> cartList;
    private cartAdapter adapter ;
    static int[][] cartContents = {{1,3},{2,4},{3,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ListView cartListView = (ListView) findViewById(R.id.cartListView);
        itemsList = createItemList();
        cartList = new ArrayList<cartItem>();
        for(int i = 0; i < cartContents.length; ++i) {
            catalogueItem cItem = itemsList.get(cartContents[i][0]-1);
            if(cItem != null) {
                cartItem qItem = new cartItem(cItem, cartContents[i][1]);
                cartList.add(qItem);
            }
        }

        adapter = new cartAdapter(this, cartList);
        cartListView.setAdapter(adapter);
    }
    public void close(View view){
        finish();
    }

    private ArrayList<catalogueItem> createItemList(){
        Resources res = getResources();
        String[] catItems = res.getStringArray(R.array.catalogItems_array);
        ArrayList<catalogueItem> newList = new ArrayList<catalogueItem>();;
        for(String itemString:catItems) {
            catalogueItem an_item = new catalogueItem(itemString);
            if (an_item.num > 0 && an_item.num < itemPicsIds.length)
                an_item.res_id = itemPicsIds[an_item.num];
            newList.add(an_item);
        }
        return newList;
    }
}
