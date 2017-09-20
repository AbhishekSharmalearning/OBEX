package com.application.innove.obex.Retrofit;

import com.application.innove.obex.ObexModel.DeleteResponseModel;
import com.application.innove.obex.ObexModel.DocumentResponseModel;
import com.application.innove.obex.ObexModel.DownloadResponseModel;
import com.application.innove.obex.ObexModel.LoginResponseModel;
import com.application.innove.obex.ObexModel.PersonalInformationResponseModel;
import com.application.innove.obex.ObexModel.SpinnerOneResponseModel;
import com.application.innove.obex.ObexModel.SpinnerTwoResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abhisheksharma on 08-Aug-2017.
 */

public interface ApiInterface {


    //http://obexdev.innov.in/api/LoginDemo/GetValidateUser?Username=9002737&Password=1234
    @GET("LoginDemo/GetValidateUser")
    Call<LoginResponseModel> GetValidateUser(@Query("Username") String un, @Query("Password") String pwd);


    //http://obexdev.innov.in/api/LoginDemo/CandidateInformation?InnovID=1000065
    @GET("LoginDemo/CandidateInformation")
    Call<PersonalInformationResponseModel> CandidateInformation(@Query("InnovID") String innovid);


    //http://obexdev.innov.in/api/LoginDemo/GetDocumentCheckList
    @GET("LoginDemo/GetDocumentCheckList")
    Call<DocumentResponseModel> GetDocumentCheckList();

    //http://obexdev.innov.in/api/LoginDemo/DeleteDocument?InnovID=1000086&DocID=10
    @GET("LoginDemo/DeleteDocument")
    Call<DeleteResponseModel> DeleteDocument(@Query("InnovID") String innovid, @Query("DocID") String docid);

    //http://obexdev.innov.in/api/LoginDemo/DonwloadDocument?InnovID=1000184&DocID=20
    @GET("LoginDemo/DonwloadDocument")
    Call<DownloadResponseModel> DonwloadDocument(@Query("InnovID") String innovid, @Query("DocID") String docid);


    //http://obexdev.innov.in/api/LoginDemo/GetOfferList?InnovID=1004567
    @GET("LoginDemo/GetOfferList")
    Call<SpinnerOneResponseModel> GetOfferList(@Query("InnovID") String innovid);

    // http://obexdev.innov.in/api/LoginDemo/GetOfferGenerationDocument?OfferID=1402
    @GET("LoginDemo/GetOfferGenerationDocument")
    Call<SpinnerTwoResponseModel> GetOfferGenerationDocument(@Query("OfferID") String offerID);

}
