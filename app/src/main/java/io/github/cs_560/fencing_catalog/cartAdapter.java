package io.github.cs_560.fencing_catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class cartAdapter extends ArrayAdapter<cartItem> {
    public cartAdapter(Context context, ArrayList<cartItem> cItems) {
        super(context, 0, cItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        cartItem cItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_cart, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.firstLine);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceLine);
        // Populate the data into the template view using the data object
        ImageView ivPic = (ImageView) convertView.findViewById(R.id.image);
        TextView tvQuant = (TextView) convertView.findViewById(R.id.cartQuant);
        tvQuant.setText(Integer.toString(cItem.quantity));
        tvName.setText(cItem.name);
        String priceString = "$  " + String.valueOf(cItem.price100/100) + "." + String.valueOf(cItem.price100%100);
        tvPrice.setText(priceString);

        ivPic.setImageResource(cItem.res_id);
        // Return the completed view to render on screen
        return convertView;
    }

}
