package com.cradlevendor.chat.model;

import java.util.ArrayList;


public class Consersation {
    private final ArrayList<conversations> listMessageData;

    public Consersation(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<conversations> getListMessageData() {
        return listMessageData;
    }
}
