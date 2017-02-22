package io.github.cs_560.fencing_catalog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemPageActivity extends BaseActivity {
    static int[] itemPicsIds = {0, R.mipmap.ic_mask, R.mipmap.ic_foilmask, R.mipmap.ic_sabremask, R.mipmap.ic_foil, R.mipmap.ic_sabre, R.mipmap.ic_epee, R.mipmap.ic_jacket, R.mipmap.ic_glove,
            R.mipmap.ic_pants, R.mipmap.ic_underarmprotector, R.mipmap.ic_chestprotectorwomens, R.mipmap.ic_chestprotector, R.mipmap.ic_foillame, R.mipmap.ic_sabrelame, R.mipmap.ic_bodycord,
            R.mipmap.ic_bodycord3, R.mipmap.ic_maskcord};
    int item_no;
    String item_name;
    TextView tvName;
    TextView tvPrice;
    TextView tvDesc;
    TextView tvId;
    ImageView ivPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);
        tvName = (TextView) findViewById(R.id.itemName);
        tvPrice = (TextView) findViewById(R.id.itemPrice);
        tvDesc = (TextView) findViewById(R.id.itemDescription);
        tvId = (TextView) findViewById(R.id.itemId);
        ivPic = (ImageView) findViewById(R.id.itemPic);
        Intent intent = getIntent();
        item_no = intent.getIntExtra("open_pos", 0) + 1;
        if(item_no == 0)
            finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        catalogueItem cItem = itemsList.get(item_no-1);
        tvName.setText(cItem.name);
        item_name = cItem.name;
        String priceString = "$  " + String.valueOf(cItem.price100/100) + "." + String.valueOf(cItem.price100%100);
        tvPrice.setText(priceString);
        tvDesc.setText(cItem.desc);
        tvId.setText(String.format("No.%04d",cItem.id));
        ivPic.setImageResource(cItem.pic_id);
    }

    public void onClick_AddtoCart(View view){
        addConfirmer();
    }

    private void addToCart(int quantity){
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("itemno", item_no);
        intent.putExtra("quantity", quantity);
        onBackPressed();
        startActivity(intent);
    }

    private void addConfirmer(){
        EditText quantInput = (EditText) findViewById(R.id.editQuant);
        final int quantity = Integer.parseInt(quantInput.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemPageActivity.this);
        String message = "Adding "+Integer.toString(quantity)+" of "+item_name + " to your shopping cart?";
        builder.setMessage(message)
                .setTitle(R.string.confirmAdd_title)
                .setPositiveButton(R.string.confirmAdd_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addToCart(quantity);
                    }
                })
                .setNegativeButton(R.string.confirmAdd_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkCart(View view){
        Intent intent = new Intent(ItemPageActivity.this, CartActivity.class);
        startActivity(intent);
    }

    public void close(View view){
        onBackPressed();
    }
}
