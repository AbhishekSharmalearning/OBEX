
package com.application.innove.obex.ObexModel;

import com.application.innove.obex.ObexExpandableModel.DocumentDummy;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentResponseModel {

    @SerializedName("Documents")
    @Expose
    private List<DocumentDummy> documents = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<DocumentDummy> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDummy> documents) {
        this.documents = documents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
