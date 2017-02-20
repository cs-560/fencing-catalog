package io.github.cs_560.fencing_catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class catalogueAdapter extends ArrayAdapter<catalogueItem> {
    public catalogueAdapter(Context context, ArrayList<catalogueItem> cItems) {
        super(context, 0, cItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        catalogueItem cItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_catalogue, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.firstLine);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceLine);
        // Populate the data into the template view using the data object
        ImageView ivPic = (ImageView) convertView.findViewById(R.id.image);

        tvName.setText(cItem.name);
        String priceString = "$  " + String.valueOf(cItem.price100/100) + "." + String.valueOf(cItem.price100%100);
        tvPrice.setText(priceString);

        ivPic.setImageResource(cItem.res_id);
        // Return the completed view to render on screen
        return convertView;
    }

}
