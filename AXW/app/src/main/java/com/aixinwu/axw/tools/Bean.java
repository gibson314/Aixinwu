package com.aixinwu.axw.tools;

import java.util.Iterator;

/**
 * Created by Cross_Life on 2016/4/18.
 */
public class Bean {
    private int picId;
    private String type;
    private String doc;
    private int itemId;
    public Bean(int itemId, int picId, String type, String doc) {
        this.picId = picId;
        this.itemId = itemId;
        this.type = type;
        this.doc = doc;
    }
    public void setItemId(int itemID){
        this.itemId = itemID;
    }
    public int getItemId(){
        return itemId;
    }
    public int getPicId() {
        return picId;
    }

    public void setIconId(int picId) {
        this.picId = picId;
    }

    public String getType() {
        return type;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setType(String type) {
        this.type = type;
    }
}
