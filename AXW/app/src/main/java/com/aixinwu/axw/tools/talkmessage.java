package com.aixinwu.axw.tools;

/**
 * Created by Cross_Life on 2016/7/24.
 */
public class talkmessage {
    private int sender;
    private int receiver;
    private String doc;
    public void setSender(int _sender){
        sender = _sender;
    }
    public int getSender(){
        return sender;
    }
    public void setReceiver(int _receiver){
        receiver = _receiver;
    }
    public int getReceiver(){
        return receiver;
    }
    public void setDoc(String _doc){
        doc = _doc;
    }
    public String getDoc(){
        return doc;
    }

}
