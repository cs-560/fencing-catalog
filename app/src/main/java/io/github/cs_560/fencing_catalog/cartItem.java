package io.github.cs_560.fencing_catalog;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class cartItem extends catalogueItem {
    int quantity;
    int qCost;
    public cartItem(catalogueItem src, int quant){
        name = new String(src.name);
        id = src.id;
        desc = new String(src.desc);
        price100 = src.price100;
        quantity = quant;
        pic_id = src.pic_id;
        qCost = price100*quantity;
    }
    public void modify_quantity(int by){
        quantity += by;
    }
}
