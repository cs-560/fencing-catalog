package io.github.cs_560.fencing_catalog;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ListView itemListView;


    private catalogueAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView itemListView = (ListView) findViewById(R.id.cataloguelist);
        adapter = new catalogueAdapter(this, itemsList);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemPageActivity.class);
                intent.putExtra("open_pos", position);
                startActivity(intent);
            }
        });

        // Set the ArrayAdapter as the ListView's adapter.
        itemListView.setAdapter( adapter );
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    public void openCart(View view){
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        startActivity(intent);
    }

    public catalogueItem getItem(int num){
        return itemsList.get(num);
    }
}
