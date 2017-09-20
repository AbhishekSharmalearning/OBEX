package com.application.innove.obex.ObexModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by abhisheksharma on 08-Aug-2017.
 */

public class LoginResponseModel {


    @SerializedName("Glst")
    @Expose
    private List<Glst> glst = null;

    public List<Glst> getGlst() {
        return glst;
    }

//    public void setGlst(List<Glst> glst) {
//        this.glst = glst;
//    }

}
