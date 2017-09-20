package com.application.innove.obex.ObexActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.application.innove.obex.ObexModel.Glst;
import com.application.innove.obex.ObexModel.LoginResponseModel;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.InternetStatus;
import com.application.innove.obex.Utilityclass.PermissionUtil;
import com.application.innove.obex.Utilityclass.StatusBar;
import com.application.innove.obex.Utilityclass.ToastUtils;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by abhisheksharma on 28-Jul-2017.
 */

public class LoginScreen extends com.application.innove.obex.ObexActivities.AppCompatActivity {

    Context ctx;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    /*Initialization of Layout components*/

    @InjectView(R.id.etusername)
    EditText UsernameInnov;
    @InjectView(R.id.etpassword)
    EditText PasswordInnov;
    @InjectView(R.id.Login)
    Button Login;
    @InjectView(R.id.imageView)
    ImageView Logo;
    PermissionUtil permission;
    public static ProgressDialog progress;
    public static boolean fIsErrorInService = true;

    private String username;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginscreen);
        ButterKnife.inject(this);
        ctx = this;
        progress = new ProgressDialog(ctx);

        StatusBar.MatchStatusBGEqualsAndAboveLollipop(this, ctx);


        //==========================Runtime Permission----------------------------
        //region RuntimePermission

        if (PermissionUtil.checkPermissions(ctx)) {

        }
        if (PermissionUtil.checkPermissionForCamera(ctx)) {

        }

        //endregion


        //initializing awesomevalidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        /*==============================Font of Password will be as it when you set inputtype is password=====================*/
        PasswordInnov.setTypeface(Typeface.DEFAULT);
        PasswordInnov.setTransformationMethod(new PasswordTransformationMethod());

        awesomeValidation.addValidation(this, R.id.etusername, "\\d+", R.string.name_error);
        String regexPassword = ".{1,}";
        awesomeValidation.addValidation(this, R.id.etpassword, regexPassword, R.string.Passworderror);

        Logo.setScaleType(ImageView.ScaleType.FIT_XY);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    //region RequestPermission Result
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //accessing files it can be pdf & image also
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            case 1234:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }
    }

    //endregion


    @OnClick(R.id.Login)
    void Submitbutton(View view) {
        if (view == Login) {

            if (InternetStatus.checkConnection(ctx)) {


                username = UsernameInnov.getText().toString().trim();
                password = PasswordInnov.getText().toString().trim();
                submitForm(username, password);
            } else {
                ToastUtils.showToast(ctx, R.string.InternetError);
            }


        }
    }


    //region  Submitbuttonclick
    private void submitForm(String usern, String Passw) {
        if (awesomeValidation.validate()) {
            ObexLogin(usern, Passw);
            showProgressDialog();
        }
    }


    private void ObexLogin(String userName, String pwd) {

        WebAPI.getInstance().doObexLoginDetails(userName, pwd, new WebAPI.LoginApiObexCallback() {

            @Override
            public void onSuccessObexLoginDetails(LoginResponseModel response) {

                if (response != null) {
                    if (response.getGlst() != null) {
                        List<Glst> lst = response.getGlst();
                        if (lst.size() > 0) {
                            if (lst.get(0).getStatus().equals("1")) {
                                Intent in = new Intent(ctx, HomescreenActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                hideProgressDialog();
                                startActivity(in);
                                finish();
                            } else {

                                ToastUtils.showToastWithoutStrResources(ctx, "Invalid Login Credentials");
                                hideProgressDialog();

                            }
                        }
                    } else {
                        ToastUtils.showToast(ctx, R.string.Invalidattempt);
                        hideProgressDialog();
                        //login invalid
                    }

                } else {
                    ToastUtils.showToast(ctx, R.string.Invalidattempt);
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(String failureMessage) {
                hideProgressDialog();
                failureMessage = "Something went wrong Please try again Later";

            }


        });
    }

    @Override
    public void onBackPressed() {

    }
}



