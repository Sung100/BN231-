package com.bn231.clinic.model;

import java.io.Serializable;

public class Administrator implements Serializable {
    private final String username;
    private String displayName;
    public Administrator(String username,String displayName){this.username=username;this.displayName=displayName;}
    public String getUsername(){return username;} public String getDisplayName(){return displayName;} public void setDisplayName(String value){displayName=value;}
}
