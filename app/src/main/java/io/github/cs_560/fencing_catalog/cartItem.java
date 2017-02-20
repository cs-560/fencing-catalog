package io.github.cs_560.fencing_catalog;

/**
 * Created by Li Yanyi on 2/19/2017.
 */

public class cartItem extends catalogueItem {
    int quantity;
    public cartItem(catalogueItem src, int quant){
        name = new String(src.name);
        num = src.num;
        desc = new String(src.desc);
        price100 = src.price100;
        quantity = quant;
        res_id = src.res_id;
    }
    public void modify_quantity(int by){
        quantity += by;
    }
}
