package io.github.cs_560.fencing_catalog;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    private ArrayList<catalogueItem> itemsList;
    static int[] itemPicsIds = {0, R.mipmap.ic_mask, R.mipmap.ic_foilmask, R.mipmap.ic_sabremask, R.mipmap.ic_foil, R.mipmap.ic_sabre, R.mipmap.ic_epee, R.mipmap.ic_jacket, R.mipmap.ic_glove,
            R.mipmap.ic_pants, R.mipmap.ic_underarmprotector, R.mipmap.ic_chestprotectorwomens, R.mipmap.ic_chestprotector, R.mipmap.ic_foillame, R.mipmap.ic_sabrelame, R.mipmap.ic_bodycord,
            R.mipmap.ic_bodycord3, R.mipmap.ic_maskcord};
    private ListView itemListView;


    private catalogueAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView itemListView = (ListView) findViewById(R.id.cataloguelist);

        itemsList = createItemList();
        adapter = new catalogueAdapter(this, itemsList);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Main.this, ItemPage.class);
                intent.putExtra("open_id", position);
                startActivity(intent);
            }
        });

        // Set the ArrayAdapter as the ListView's adapter.
        itemListView.setAdapter( adapter );
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

    public void openCart(View view){
        Intent intent = new Intent(Main.this, CartActivity.class);
        startActivity(intent);
    }

    public catalogueItem getItem(int num){
        return itemsList.get(num);
    }
}
