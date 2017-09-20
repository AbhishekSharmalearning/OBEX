package com.application.innove.obex.ObexModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AbhishekSharma on 18-09-2017.
 */
public class SpinnerOneResponseModel {
    @SerializedName("InnovID")
    @Expose
    private String innovID;
    @SerializedName("lst")
    @Expose
    private List<Lst> lst = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getInnovID() {
        return innovID;
    }

    public void setInnovID(String innovID) {
        this.innovID = innovID;
    }

    public List<Lst> getLst() {
        return lst;
    }

    public void setLst(List<Lst> lst) {
        this.lst = lst;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

