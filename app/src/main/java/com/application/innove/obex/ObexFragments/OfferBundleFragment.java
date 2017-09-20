package com.application.innove.obex.ObexFragments;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.application.innove.obex.ObexActivities.AppCompatFragment;
import com.application.innove.obex.ObexAdapters.OfferBundleAdapter;
import com.application.innove.obex.ObexModel.Lst;
import com.application.innove.obex.ObexModel.SpinnerOneResponseModel;
import com.application.innove.obex.ObexModel.SpinnerTwoResponseModel;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.SessionManager;
import com.application.innove.obex.Utilityclass.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by abhisheksharma on 04-Aug-2017.
 */

public class OfferBundleFragment extends AppCompatFragment {
	@InjectView(R.id.Dynamicofferletter)
	RecyclerView Capturedimage;
	@InjectView(R.id.cameraimg)
	ImageView Cameraimage;
	@InjectView(R.id.Dynamicofferletterlayout)
	LinearLayout LinearImageview;
	ValueAnimator mAnimator;
	public String userChoosenTask;
	public Uri fileUri; // file url to store image/video
	OfferBundleAdapter offerbundle;
	public LinearLayout layoutActionSheet;
	private ArrayList<File> photos = new ArrayList<> ();
	public Animation showPicker, hidePicker;
// Spinner click listener

	public static Spinner Spinnerfirst;

	public static Spinner SpinnerTwo;

	public static List<String> spinnerOneLst = new ArrayList<> ();
	public static List<String> spinnerTwoLst = new ArrayList<> ();

	private String mCurrentPhotoPath;
	//private int SELECT_FILE = 235;
	static Context ctx;
	private static int ONE = 1;
	private static int TWO = 2;
	private static int THREE = 3;
	private static int FOUR = 4;
	private static int FIVE = 5;
	private static int SIX = 6;

	//static ProgressDialog progress;

	public OfferBundleFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate ( savedInstanceState );
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater
				.inflate ( R.layout.offerbundlefragment, container, false );
		ButterKnife.inject ( this, rootView );
		ctx = this.getActivity ().getApplicationContext ();
	//	progress = new ProgressDialog ( ctx );
		layoutActionSheet = rootView.findViewById ( R.id.cameraBottomLayout );
		Spinnerfirst = rootView.findViewById ( R.id.Spinnerfirst );
		SpinnerTwo = rootView.findViewById ( R.id.SpinnerTwo );
		animationview ();


		//create and set layout manager for each RecyclerView
		LinearLayoutManager linearLayoutManagerOL = new LinearLayoutManager ( getActivity (), LinearLayoutManager.HORIZONTAL, false );
		Capturedimage.setLayoutManager ( linearLayoutManagerOL );
		ViewGroup.LayoutParams params = Capturedimage.getLayoutParams ();
		params.height = 300;
		Capturedimage.setLayoutParams ( params );


		Spinnerfirst.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				//Here right the code for spinner click
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		} );


		// LoadValuesForSpinnerTwo();
		return rootView;
	}


	//region ONCLICK OF CAMERA
	@OnClick(R.id.cameraimg)
	public void cameraclick() {
		if (layoutActionSheet.getVisibility () == View.GONE) {
			layoutActionSheet.startAnimation ( showPicker );
			layoutActionSheet.setVisibility ( View.VISIBLE );
		} else {
			layoutActionSheet.startAnimation ( hidePicker );
			layoutActionSheet.setVisibility ( View.GONE );
		}
	}

	//endregion


	@OnClick(R.id.btnCamera)
	public void onCamera() {
		if (layoutActionSheet.getVisibility () == View.VISIBLE) {
			layoutActionSheet.startAnimation ( hidePicker );
		}
		EasyImage.openCamera ( this, 70 );
	}


	@OnClick(R.id.btnGallery)
	public void onGallery() {

		if (layoutActionSheet.getVisibility () == View.VISIBLE) {
			layoutActionSheet.startAnimation ( hidePicker );
		}
		EasyImage.openGallery ( this, 66 );

	}

	@OnClick(R.id.btnCancel)
	public void onCancel() {
		if (layoutActionSheet.getVisibility () == View.VISIBLE) {
			layoutActionSheet.startAnimation ( hidePicker );
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult ( requestCode, resultCode, data );

		EasyImage.handleActivityResult ( requestCode, resultCode, data, getActivity (), new DefaultCallback () {
			@Override
			public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
				//Some error handling
				e.printStackTrace ();
			}


			@Override
			public void onImagePicked(File imageFiles, EasyImage.ImageSource source, int type) {
				List<File> lstFiles = new ArrayList<File> ();
				lstFiles.add ( imageFiles );
				onPhotosReturned ( lstFiles );
			}

			@Override
			public void onCanceled(EasyImage.ImageSource source, int type) {
				//Cancel handling, you might wanna remove taken photo if it was canceled
				if (source == EasyImage.ImageSource.CAMERA) {
					File photoFile = EasyImage.lastlyTakenButCanceledPhoto ( getActivity () );
					if (photoFile != null) photoFile.delete ();
				}
			}
		} );
	}


	private void onPhotosReturned(List<File> returnedPhotos) {
		photos.addAll ( returnedPhotos );
		offerbundle = new OfferBundleAdapter ( ctx, photos );
		Capturedimage.setAdapter ( offerbundle );
		offerbundle.notifyDataSetChanged ();
		Capturedimage.scrollToPosition ( photos.size () - 1 );
	}


	@Override
	public void onDestroy() {
		// Clear any configuration that was done!
		EasyImage.clearConfiguration ( getActivity () );
		super.onDestroy ();
	}

	public static void LoadValuesForSpinnerOne(String InnovID) {
//		progress.setMessage ( "Loading..." );
//		progress.setProgressStyle ( ProgressDialog.STYLE_SPINNER );
//		progress.setCancelable ( false );
//		progress.setIndeterminate ( true );
//
//		progress.show ();

		WebAPI.getInstance ().doSpinnerOneRequest ( InnovID, new WebAPI.SpinnerOneCallback () {
			@Override
			public void onSuccessSpinnerOne(SpinnerOneResponseModel response) {
//				progress.dismiss ();

				if (response != null) {
					List<Lst> lstoffer = response.getLst ();
					if (lstoffer != null) {

						if (lstoffer.size () > 0) {

							for (int i = 0; i < lstoffer.size (); i++) {

								spinnerOneLst.add ( response.getLst ().get ( i ).getOfferName () );
								String OfferID = response.getLst ().get ( i ).getOfferID ();
								SessionManager.putStringInPreferences ( ctx, OfferID, "OfferIDAfterPArse" );


							}


							// Creating adapter for spinner
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> ( ctx, android.R.layout.simple_spinner_item, spinnerOneLst );

							// Drop down layout style - list view with radio button
							dataAdapter.setDropDownViewResource ( R.layout.spinner_item );


							// attaching data adapter to spinner
							Spinnerfirst.setAdapter ( dataAdapter );

						}
					}
				}
				else
				{
					ToastUtils.showToast ( ctx, R.string.Invalidattempt );
					//login invalid
				}
			}

			@Override
			public void onFailure(String failureMessage) {

			}
		} );
	}


	public static void LoadValuesForSpinnerTwo(String offerID) {
		WebAPI.getInstance ().doSpinnerTwoRequest ( offerID, new WebAPI.SpinnerTwoCallback () {
			@Override
			public void onSuccessSpinnerTwo(SpinnerTwoResponseModel response) {
				if (response != null) {
					if (response.getDocumentList () != null) {

						if (response.getDocumentList ().size () > 0) {
							for (int i = 0; i < response.getDocumentList ().size (); i++) {
								spinnerTwoLst.add ( response.getDocumentList ().get ( i ).getOfferPatternName () );
							}


							// Creating adapter for spinner
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
									( ctx, android.R.layout.simple_spinner_item, spinnerTwoLst );

							// Drop down layout style - list view with radio button
							dataAdapter.setDropDownViewResource ( R.layout.spinner_item );


							// attaching data adapter to spinner
							SpinnerTwo.setAdapter ( dataAdapter );
						}
					}
				}
			}

			@Override
			public void onFailure(String failureMessage) {

			}
		} );
	}


	private void animationview() {

		AlphaAnimation alpha = new AlphaAnimation ( 1.0F, 0.0F );
		alpha.setDuration ( 0 ); // Make animation instant
		alpha.setFillAfter ( true ); // Tell it to persist after the animation ends
		layoutActionSheet.startAnimation ( alpha );
		showPicker = AnimationUtils.loadAnimation ( getActivity (), R.anim.slide_up );
		hidePicker = AnimationUtils.loadAnimation ( getActivity (), R.anim.slide_down );
		showPicker.setAnimationListener ( animListener );
		hidePicker.setAnimationListener ( animListener );
	}

	private Animation.AnimationListener animListener = new Animation.AnimationListener () {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			if (animation == showPicker) {
				layoutActionSheet.setVisibility ( View.VISIBLE );
			}

			if (animation == hidePicker) {
				layoutActionSheet.setVisibility ( View.GONE );
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if (animation == showPicker) {
				layoutActionSheet.setVisibility ( View.VISIBLE );
			}

			if (animation == hidePicker) {
				layoutActionSheet.setVisibility ( View.GONE );
			}
		}
	};

}
