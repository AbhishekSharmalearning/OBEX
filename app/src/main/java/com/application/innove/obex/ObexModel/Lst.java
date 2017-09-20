package com.application.innove.obex.ObexModel;

/**
 * Created by AbhishekSharma on 18-09-2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lst {

    @SerializedName("OfferID")
    @Expose
    private String offerID;
    @SerializedName("OfferName")
    @Expose
    private String offerName;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

}