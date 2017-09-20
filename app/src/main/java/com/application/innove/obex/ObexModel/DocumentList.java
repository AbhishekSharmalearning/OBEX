package com.application.innove.obex.ObexModel;

/**
 * Created by AbhishekSharma on 18-09-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentList {

    @SerializedName("OfferPatternID")
    @Expose
    private Integer offerPatternID;
    @SerializedName("OfferPatternName")
    @Expose
    private String offerPatternName;

    public Integer getOfferPatternID() {
        return offerPatternID;
    }

    public void setOfferPatternID(Integer offerPatternID) {
        this.offerPatternID = offerPatternID;
    }

    public String getOfferPatternName() {
        return offerPatternName;
    }

    public void setOfferPatternName(String offerPatternName) {
        this.offerPatternName = offerPatternName;
    }

}