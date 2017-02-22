package io.github.cs_560.fencing_catalog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends BaseActivity {
    static String nameSaveFile = "cartsave.dat";
    private ArrayList<cartItem> cartList;
    private cartAdapter adapter ;
    private ArrayList<int[]>  cartContents;
    private Button emptyBtn;
    private Button checkoutBtn;
    private TextView tvTotal;
    private int add_id;
    private int add_quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvTotal = (TextView) findViewById(R.id.total);
        emptyBtn = (Button) findViewById(R.id.empty);
        checkoutBtn = (Button) findViewById(R.id.checkoutBtn);
        ListView cartListView = (ListView) findViewById(R.id.cartListView);
        cartList = new ArrayList<cartItem>();
        cartContents = loadCart();
        Intent intent = getIntent();
        add_id = intent.getIntExtra("itemno",0);
        add_quantity = intent.getIntExtra("quantity", 0);
        adapter = new cartAdapter(this, R.layout.row_cart, cartList);
        cartListView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        if(add_id > 0){
            boolean found = false;
            for(int[] entry : cartContents){
                if(entry[0] == add_id) {
                    entry[1] += add_quantity;
                    found = true;
                }
            }
            if(!found) {
                int[] new_entry = {add_id, add_quantity};
                cartContents.add(new_entry);
            }
            add_id = 0;
        }
        add_quantity = 0;
        int total = 0;
        emptyBtn.setVisibility(View.GONE);
        checkoutBtn.setVisibility(View.GONE);
        for(int[] entry : cartContents) {
            catalogueItem cItem = itemsList.get(entry[0]-1);
            if(cItem != null) {
                cartItem qItem = new cartItem(cItem, entry[1]);
                cartList.add(qItem);
                total += cItem.price100*entry[1];//calculating total cost
            }
        }
        String totalstring = "Cart is Empty";
        if(cartContents.size() != 0){
            totalstring = "Total: $" + Integer.toString(total / 100) + "." + String.format("%02d",total % 100);
            tvTotal.setText(totalstring);
            emptyBtn.setVisibility(View.VISIBLE);
            checkoutBtn.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        saveCart();
        super.onStop();
    }

    private void saveCart(){
        FileOutputStream outputStream;
        try {
            outputStream = this.openFileOutput(nameSaveFile, Context.MODE_PRIVATE);
            for(int[] entry:cartContents) {
                String line = Integer.toString(entry[0])+","+Integer.toString(entry[1])+";\n";
                outputStream.write(line.getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void refreshtotal(){
        if(cartContents.size() == 0){
            Button emptyBtn = (Button) findViewById(R.id.empty);
            emptyBtn.setVisibility(View.GONE);
            tvTotal.setText("Cart is Empty");
        }
        else{
            int total = 0;
            for(int[] entry : cartContents) {
                catalogueItem cItem = itemsList.get(entry[0]-1);
                if(cItem != null) {
                    total += cItem.price100*entry[1];//calculating total cost
                }
            }
            String totalstring = "Total:  $ " + Integer.toString(total / 100) + "." + String.format("%02d",total % 100);
            tvTotal.setText(totalstring);
            emptyBtn.setVisibility(View.VISIBLE);
        }
    }
    private ArrayList<int[]> loadCart(){
        ArrayList<int[]> ret = new ArrayList<int[]>();
        String rawSave = readFromFile(this);
        if(!rawSave.equals("")) {
            try {
                String[] raw_entries = rawSave.split(";");
                for (String line : raw_entries) {
                    String[] id_quant = line.split(",");
                    int[] entry = {Integer.parseInt(id_quant[0]), Integer.parseInt(id_quant[1])};
                    ret.add(entry);
                }
                return ret;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        /*int[][] test_data = {{1,3},{2,4},{3,5}};
        ret.add(test_data[0]);
        ret.add(test_data[1]);
        ret.add(test_data[2]);
        */
        return ret;
    }

    private String readFromFile(Context context) {

        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(nameSaveFile);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            //Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    private void updateTotal(){
        int total = 0;
        TextView tvTotal = (TextView) findViewById(R.id.total);
        for(int[] entry : cartContents) {
            catalogueItem cItem = itemsList.get(entry[0]-1);
            if(cItem != null) {
                cartItem qItem = new cartItem(cItem, entry[1]);
                cartList.add(qItem);
                total += cItem.price100*entry[1];//calculating total cost
            }
        }
        String totalstring = "Total:  $ "+Integer.toString(total/100) + "."+Integer.toString(total%100);
        tvTotal.setText(totalstring);
    }
    private int changeQuant(int index, int change){
        try {
            int quantity = cartContents.get(index)[1];
            quantity += change;
            setQuant(index, quantity);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            if(index > 0)
                index = -index;
        }
        return index;
    }
    private int setQuant(int index, int quantity){
        try {
            if (quantity <= 0){
                removeConfirmer(cartList.get(index).name, index);
            }
            else {
                cartContents.get(index)[1] = quantity;
                cartList.get(index).quantity = quantity;
            }
            refreshtotal();
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            if(index > 0)
                index = -index;
        }
        return index;
    }
    public void close(View view){
        onBackPressed();
    }

    private class cartAdapter extends ArrayAdapter<cartItem> {
        private int layout;

        public cartAdapter(Context context, int resource, ArrayList<cartItem> objects) {
            super(context, resource, objects);
            this.layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            rowHolder mainrowholder = null;
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
                rowHolder rowholder = new rowHolder();
                rowholder.tvName = (TextView) convertView.findViewById(R.id.cname);
                rowholder.tvId = (TextView) convertView.findViewById(R.id.itemNo);
                rowholder.tvPrice = (TextView) convertView.findViewById(R.id.cprice);
                rowholder.tvQInt = (TextView) convertView.findViewById(R.id.qInt);
                rowholder.tvQDeci = (TextView) convertView.findViewById(R.id.qDeci);
                rowholder.etQuant = (EditText) convertView.findViewById(R.id.cartQuant);
                rowholder.etQuant.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        int quantity;
                        if(s.toString().equals(""))
                            quantity = 0;
                        else
                            quantity = Integer.parseInt(s.toString());
                        setQuant(position, quantity);
                    }
                });
                rowholder.btnInc = (ImageButton) convertView.findViewById(R.id.incBtn);
                rowholder.btnInc.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        changeQuant(position, 1);
                        notifyDataSetChanged();
                    }
                });
                rowholder.btnDec = (ImageButton) convertView.findViewById(R.id.decBtn);
                rowholder.btnDec.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        changeQuant(position, -1);
                        notifyDataSetChanged();
                    }
                });
                convertView.setTag(rowholder);
            }
            mainrowholder = (rowHolder)convertView.getTag();
            cartItem cItem = getItem(position);
            if(cItem == null)
                return convertView;
            mainrowholder.etQuant.setText(Integer.toString(cItem.quantity));
            mainrowholder.tvName.setText(cItem.name);
            mainrowholder.tvId.setText(String.format("%04d",cItem.id));
            mainrowholder.tvQInt.setText("$" + String.valueOf(cItem.price100*cItem.quantity/100));
            mainrowholder.tvQDeci.setText("." + String.format("%02d", cItem.price100*cItem.quantity%100));
            String priceString = "$  " + String.valueOf(cItem.price100/100) + "." + String.valueOf(cItem.price100%100);
            mainrowholder.tvPrice.setText(priceString);
            // Return the completed view to render on screen
            return convertView;
        }
    }
    public class rowHolder{
        TextView tvId;
        TextView tvPrice;
        TextView tvQInt;
        TextView tvQDeci;
        TextView tvName;
        EditText etQuant;
        ImageButton btnDec;
        ImageButton btnInc;
    }

    private int removeItem(int position){
        int ret = -1;
        try {
            cartContents.remove(position);
            cartList.remove(position);
            adapter.notifyDataSetChanged();
            refreshtotal();
            ret = position;
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return ret;
    }
    public void emptyCart(View view){
        emptyConfirmer();
    }
    private void emptyConfirmer(){
        EditText quantInput = (EditText) findViewById(R.id.editQuant);
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        String message = "Remove all items from shopping cart?";
        builder.setMessage(message)
                .setTitle(R.string.confirmAdd_title)
                .setPositiveButton(R.string.confirmAdd_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cartList.clear();
                        cartContents.clear();
                        adapter.notifyDataSetChanged();
                        refreshtotal();
                    }
                })
                .setNegativeButton(R.string.confirmAdd_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void removeConfirmer(String item_name, final int position){
        EditText quantInput = (EditText) findViewById(R.id.editQuant);
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        String message = "Remove "+item_name + " from shopping cart?";
        builder.setMessage(message)
                .setTitle(R.string.confirmAdd_title)
                .setPositiveButton(R.string.confirmAdd_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeItem(position);
                    }
                })
                .setNegativeButton(R.string.confirmAdd_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void checkOutConfirmer(){
        EditText quantInput = (EditText) findViewById(R.id.editQuant);
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        String message = "Check Out?";
        builder.setMessage(message)
                .setTitle(R.string.confirmAdd_title)
                .setPositiveButton(R.string.confirmAdd_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(CheckOutPrinter() <=0) {
                            Toast.makeText(CartActivity.this, "ERROR:Failed to Generate Order", Toast.LENGTH_LONG).show();
                            return;
                        }
                        cartList.clear();
                        cartContents.clear();
                        finish();
                    }
                })
                .setNegativeButton(R.string.confirmAdd_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private int CheckOutPrinter(){
        FileOutputStream outputStream;
        try {
            Long tsLong = System.currentTimeMillis()/10;
            String fileName = tsLong.toString()+".order";
            outputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            String line = "Order : " + tsLong.toString()+"\n"+
                    "Item No.\tItem  Name\t\t\t\tPrice\tQuantity\tCost\n";
            outputStream.write(line.getBytes());
            int total = 0;
            for(cartItem entry:cartList) {
                line = String.format("%04d\t\t%-20s\t$%d.%02d\t%d\t\t\t$%d.%02d\n",entry.id,entry.name,entry.price100/100,entry.price100%100,
                entry.quantity,entry.qCost/100,entry.qCost%100);
                total += entry.qCost;
                outputStream.write(line.getBytes());
            }
            line = String.format("\t\t\t\t\t\t\t\t\t\t\tTotal : \t$%d.%02d\n", total/100, total%100);
            outputStream.write(line.getBytes());
            Toast.makeText(CartActivity.this, String.format("Order saved as %d.order",tsLong), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public void CheckOut(View view){
        checkOutConfirmer();
    }
}
