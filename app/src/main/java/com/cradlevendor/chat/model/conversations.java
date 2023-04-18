package com.cradlevendor.chat.model;


import androidx.annotation.NonNull;

public class conversations implements Comparable<conversations> {
    public String fileName;
    public String type;
    public String content;
    public String timestamp;
    public String queryId;
    public String image;
    public String toID;
    public String fromID;

    @Override
    public int compareTo(@NonNull conversations o) {
        return  timestamp.compareTo(o.timestamp);
    }
}