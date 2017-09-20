
package com.application.innove.obex.ObexModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalInformationResponseModel {

    @SerializedName("Info")
    @Expose
    private List<Info> info = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
