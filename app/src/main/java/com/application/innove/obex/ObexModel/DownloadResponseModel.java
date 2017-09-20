
package com.application.innove.obex.ObexModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadResponseModel {

    @SerializedName("InnovID")
    @Expose
    private Object innovID;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Error")
    @Expose
    private Object error;

    public Object getInnovID() {
        return innovID;
    }

    public void setInnovID(Object innovID) {
        this.innovID = innovID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
