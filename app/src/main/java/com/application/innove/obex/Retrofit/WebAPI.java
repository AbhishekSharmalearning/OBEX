package com.application.innove.obex.Retrofit;

import android.util.Log;

import com.application.innove.obex.ObexModel.DeleteResponseModel;
import com.application.innove.obex.ObexModel.DocumentResponseModel;
import com.application.innove.obex.ObexModel.DownloadResponseModel;
import com.application.innove.obex.ObexModel.LoginResponseModel;
import com.application.innove.obex.ObexModel.PersonalInformationResponseModel;
import com.application.innove.obex.ObexModel.SpinnerOneResponseModel;
import com.application.innove.obex.ObexModel.SpinnerTwoResponseModel;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Abhishek on 8/22/2017.
 */

public class WebAPI {
    private static final String TAG = "WEBAPI";


//	public interface LoginApiCallback {
//		void onSuccessLoginDetails(List<LoginDetail> result);
//	}

    public interface LoginApiObexCallback {
        // void onSuccessObexLoginDetails(List<Glst> result);

        void onSuccessObexLoginDetails(LoginResponseModel response);

        void onFailure(String failureMessage);
    }

    public interface PersonalInformationApiObexCallback {

        void onObexPersonalInformationdetails(PersonalInformationResponseModel response);

        void onFailure(String failureMessage);
    }


    public interface DocumentsApiObexCallback {
        void onObexDocumentdetailsinformation(DocumentResponseModel response);

        void onFailure(String failureMessage);
    }

    public interface DeleteApiObexCallback {
        // void onSuccessObexLoginDetails(List<Glst> result);

        void onSuccessObexDeleteDocuments(DeleteResponseModel response);

        void onFailure(String failureMessage);
    }

    public interface DownloadApiObexCallback {
        // void onSuccessObexLoginDetails(List<Glst> result);

        void onSuccessObexDownloadDocuments(DownloadResponseModel response);

        void onFailure(String failureMessage);
    }

    public interface SpinnerOneCallback {

        void onSuccessSpinnerOne(SpinnerOneResponseModel response);

        void onFailure(String failureMessage);
    }

    public interface SpinnerTwoCallback {


        void onSuccessSpinnerTwo(SpinnerTwoResponseModel response);

        void onFailure(String failureMessage);
    }

    private static WebAPI objWebAPI;

    public static WebAPI getInstance() {
        if (objWebAPI == null) {
            objWebAPI = new WebAPI();
        }
        return objWebAPI;
    }


    public void doObexLoginDetails(String uid, String pwd, final LoginApiObexCallback callback) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponseModel> call = apiService.GetValidateUser(uid, pwd);


        call.enqueue(new Callback<LoginResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessObexLoginDetails(response.body()); // pass the list
                } else {
                    //response.body.List null
                    callback.onFailure("Failure");

                }
            }

            // If failed
            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {


                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }




            }
        });
    }


    public void doObexPersonalInfo(String innov_id, final PersonalInformationApiObexCallback callback) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PersonalInformationResponseModel> call = apiService.CandidateInformation(innov_id);


        call.enqueue(new Callback<PersonalInformationResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<PersonalInformationResponseModel> call, Response<PersonalInformationResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onObexPersonalInformationdetails(response.body());
                } else {
                    //response unsuccessful
                    callback.onFailure("Failure");


                }
            }

            // If failed
            @Override
            public void onFailure(Call<PersonalInformationResponseModel> call, Throwable t) {
                // Log error here since request failed
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }


            }
        });

    }


    public void doObexDocumentDetails(final DocumentsApiObexCallback callback) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DocumentResponseModel> call = apiService.GetDocumentCheckList();


        call.enqueue(new Callback<DocumentResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<DocumentResponseModel> call, Response<DocumentResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onObexDocumentdetailsinformation(response.body());
                } else {
                    //response unsuccessful
                    callback.onFailure("Failure");


                }
            }

            // If failed
            @Override
            public void onFailure(Call<DocumentResponseModel> call, Throwable t) {
                // Log error here since request failed
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }

            }
        });

    }


    public void doDocumentsDelete(String innovid, String docid, final DeleteApiObexCallback callback) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<DeleteResponseModel> call = apiService.DeleteDocument(innovid, docid);


        call.enqueue(new Callback<DeleteResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<DeleteResponseModel> call, Response<DeleteResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessObexDeleteDocuments(response.body()); // pass the list
                } else {
                    //response.body.List null
                    callback.onFailure("Failure");

                }
            }

            // If failed
            @Override
            public void onFailure(Call<DeleteResponseModel> call, Throwable t) {


                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }




            }
        });
    }

    public void doDocumentsDownload(String innovid, String docid, final DownloadApiObexCallback callback) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<DownloadResponseModel> call = apiService.DonwloadDocument(innovid, docid);


        call.enqueue(new Callback<DownloadResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<DownloadResponseModel> call, Response<DownloadResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessObexDownloadDocuments(response.body()); // pass the list
                } else {
                    //response.body.List null
                    callback.onFailure("Failure");

                }
            }

            // If failed
            @Override
            public void onFailure(Call<DownloadResponseModel> call, Throwable t) {


                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }




            }
        });
    }


    public void doSpinnerOneRequest(String innovid, final SpinnerOneCallback callback) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SpinnerOneResponseModel> call = apiService.GetOfferList(innovid);
        call.enqueue(new Callback<SpinnerOneResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<SpinnerOneResponseModel> call, Response<SpinnerOneResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessSpinnerOne(response.body()); // pass the list
                } else {
                    //response.body.List null
                    callback.onFailure("Failure");
                }
            }
            // If failed
            @Override
            public void onFailure(Call<SpinnerOneResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }
            }
        });
    }

    public void doSpinnerTwoRequest(String offerID, final SpinnerTwoCallback callback) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SpinnerTwoResponseModel> call = apiService.GetOfferGenerationDocument(offerID);
        call.enqueue(new Callback<SpinnerTwoResponseModel>() {
            // If success
            @Override
            public void onResponse(Call<SpinnerTwoResponseModel> call, Response<SpinnerTwoResponseModel> response) {

                if (response.isSuccessful()) {
                    callback.onSuccessSpinnerTwo(response.body()); // pass the list
                } else {
                    //response.body.List null
                    callback.onFailure("Failure");
                }
            }
            // If failed
            @Override
            public void onFailure(Call<SpinnerTwoResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                if (t instanceof SocketTimeoutException) {
                    callback.onFailure(t.getMessage());
                }
            }
        });
    }
}
