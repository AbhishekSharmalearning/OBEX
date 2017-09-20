package com.application.innove.obex.ObexModel;

/**
 * Created by Jay on 18-09-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpinnerTwoResponseModel{

    @SerializedName("DocumentList")
    @Expose
    private List<DocumentList> documentList = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<DocumentList> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentList> documentList) {
        this.documentList = documentList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}