
package com.application.innove.obex.ObexModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteResponseModel {

    @SerializedName("InnovID")
    @Expose
    private String innovID;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Error")
    @Expose
    private String error;

    public String getInnovID() {
        return innovID;
    }

    public void setInnovID(String innovID) {
        this.innovID = innovID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
