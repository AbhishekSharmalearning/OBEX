package com.application.innove.obex.ObexModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhisheksharma on 21-Aug-2017.
 */

public class Glst {


    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("InnovID")
    @Expose
    private Object innovID;
    @SerializedName("Status")
    @Expose
    private String status;

    public Glst(String username, String password, String innovID, String status) {
        this.username = username;
        this.password = password;
        this.innovID = innovID;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getInnovID() {
        return innovID;
    }

    public void setInnovID(Object innovID) {
        this.innovID = innovID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
