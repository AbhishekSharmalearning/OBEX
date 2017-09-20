package com.application.innove.obex.ObexActivities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.innove.obex.ObexFragments.DocumentsFragment;
import com.application.innove.obex.ObexFragments.OfferBundleFragment;
import com.application.innove.obex.ObexModel.Info;
import com.application.innove.obex.ObexModel.PersonalInformationResponseModel;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.InternetStatus;
import com.application.innove.obex.Utilityclass.SessionManager;
import com.application.innove.obex.Utilityclass.StatusBar;
import com.application.innove.obex.Utilityclass.ToastUtils;
import com.application.innove.obex.Utilityclass.TypefacedTextView;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Document_capture_and_upload extends com.application.innove.obex.ObexActivities.AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    @InjectView(R.id.tabs)
    TabLayout tabLayout;
    @InjectView(R.id.percentage)
    TextView textView;
    @InjectView(R.id.DOB)
    TypefacedTextView DOB;
    @InjectView(R.id.mobile)
    TypefacedTextView mobile;
    @InjectView(R.id.innov_id)
    TypefacedTextView innovid;
    @InjectView(R.id.name)
    TypefacedTextView name;
    @InjectView(R.id.sirname)
    TypefacedTextView sirname;
    @InjectView(R.id.img_profile)
    ImageView imageprofiletemp;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.progressbarhorizontal)
    LinearLayout percentagebar;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    public static EditText edtSeach;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    Context ctx;
    ValueAnimator mAnimator;
    public static String InnoveID;
    private AwesomeValidation awesomeValidation;
    public static ProgressDialog progress;
    public Boolean IsInnovIDSelected = false;
    public static LinearLayout parentlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doucuments_upload_capture);
        ctx = this;
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
       // progress = new ProgressDialog(ctx);
        parentlayout = (LinearLayout) findViewById(R.id.parentlayout);
        //initializing awesomevalidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*=======================For statusBar Color===============*/
        StatusBar.MatchStatusBGEqualsAndAboveLollipop(this, ctx);


        //Add onPreDrawListener
        percentagebar.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        percentagebar.getViewTreeObserver().removeOnPreDrawListener(this);
                        percentagebar.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
                        percentagebar.measure(widthSpec, heightSpec);

                        mAnimator = slideAnimator(0, percentagebar.getMeasuredHeight());
                        return true;
                    }
                });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AlertforLogout();
            return true;
        } else {
            handleMenuSearch();
        }
        return super.onOptionsItemSelected(item);


    }

    private void expand() {
        //set Visible
        percentagebar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        mAnimator.start();
    }

    private void collapser() {
        int finalHeight = percentagebar.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                percentagebar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = percentagebar.getLayoutParams();
                layoutParams.height = value;
                percentagebar.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(false); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_white_24dp));


            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            edtSeach.setHint("Enter your Innov ID");
            edtSeach.setHintTextColor(Color.parseColor("#FFFFFF"));


            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (InternetStatus.checkConnection(ctx)) {
                            InnoveID = edtSeach.getText().toString().trim();
                            if (!InnoveID.equals("")) {
                                IsInnovIDSelected = true;
                                SessionManager.putBooleanInPreferences(ctx, true, "InnovIDSelected");
                                doSearch(InnoveID);
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            } else {
                                ToastUtils.showToastWithoutStrResources(ctx, "Please enter Innove ID");
                                return true;
                            }
                        } else {
                            ToastUtils.showToast(ctx, R.string.InternetError);
                        }
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_cancel_white_24dp));

            isSearchOpened = true;
        }
    }

    private void doSearch(String inn_id) {


        ObexPersonalInformation(inn_id);
        showProgressDialog();

    }

    private void ObexPersonalInformation(final String inn_id) {
        WebAPI.getInstance().doObexPersonalInfo(inn_id, new WebAPI.PersonalInformationApiObexCallback() {

            @Override
            public void onObexPersonalInformationdetails(PersonalInformationResponseModel response) {
                if (response != null) {
                    if (response.getStatus().equals("1")) {
                        if (response.getInfo() != null) {
                            //List <Info> lst=response.getStatus ();
                            List<Info> listofinformation = response.getInfo();
                            if (listofinformation.size() > 0) {

                                String Mobile_no = listofinformation.get(0).getMobile();
                                String Innov_ID = listofinformation.get(0).getInnovID();
                                SessionManager.putStringInPreferences(ctx, Innov_ID, "sharedInnovIDafterparse");
                                String Name = listofinformation.get(0).getFirstName();
                                String Surname = listofinformation.get(0).getLastName();
                                String Dateofbirth = listofinformation.get(0).getDOB();
                                String Imageprofile = listofinformation.get(0).getPicture();
                                byte[] imageByteArray = Base64.decode(Imageprofile, Base64.DEFAULT);
                                Glide.with(ctx)
                                        .load(imageByteArray)
                                        .asBitmap()
                                        .centerCrop()
                                        .into(new BitmapImageViewTarget(imageprofiletemp) {
                                            @Override
                                            protected void setResource(Bitmap resource) {
                                                RoundedBitmapDrawable circularBitmapDrawable =
                                                        RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
                                                circularBitmapDrawable.setCircular(true);
                                                imageprofiletemp.setImageDrawable(circularBitmapDrawable);
                                            }
                                        });

                                if (Mobile_no != null) {
                                    mobile.setText(Mobile_no);
                                } else {
                                    mobile.setText("--");
                                }

                                if (Innov_ID != null) {
                                    innovid.setText(Innov_ID);
                                } else {
                                    innovid.setText("--");
                                }

                                if (Name != null) {
                                    name.setText(Name);
                                } else {
                                    name.setText("--");
                                }

                                if (Surname != null) {
                                    sirname.setText(Surname);
                                } else {
                                    sirname.setText("--");
                                }

                                if (Dateofbirth != null) {
                                    DOB.setText(Dateofbirth);
                                } else {
                                    DOB.setText("--");
                                }

                                hideProgressDialog();


                            } else {
                                ToastUtils.showToast(ctx, R.string.Invalidattempt);
                            }

                        } else {
                            //login invalid
                            ToastUtils.showToast(ctx, R.string.Invalidattempt);
                        }
                    } else {
                        //Invalid credentials
                        ToastUtils.showToast(ctx, R.string.Invalidattempt);
                    }

                } else {

                }
            }

            @Override
            public void onFailure(String failureMessage) {
                hideProgressDialog();
                failureMessage = "Something went wrong Please try again Later";
//                ToastUtils.showToast(ctx, R.string.Invalidattempt);


            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // onBackPressed();
        Intent intent = new Intent();
        intent.setClassName(ctx, "com.application.innove.obex.ObexActivities.HomescreenActivity");
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClassName(ctx, "com.application.innove.obex.ObexActivities.HomescreenActivity");
        startActivity(intent);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DocumentsFragment(), "DOCUMENTS");
        adapter.addFragment(new OfferBundleFragment(), "OFFER BUNDLE");


        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (position == 1)
                {
                    boolean SearchInnovID = SessionManager.getBooleanFromPreferences(ctx, "InnovIDSelected");
                    if (SearchInnovID)
                    {
                        String innov_ID = SessionManager.getStringFromPreferences(ctx, "sharedInnovIDafterparse");
                       // String Offer_ID = SessionManager.getStringFromPreferences(ctx,"OfferIDAfterPArse");
                        if(InternetStatus.checkConnection ( ctx ))
                        {
                            OfferBundleFragment.LoadValuesForSpinnerOne ( innov_ID );

                        }
                        else
                        {
                            ToastUtils.showToast ( ctx,R.string.InternetError );
                        }
                       // OfferBundleFragment.LoadValuesForSpinnerTwo(Offer_ID);

                    }
                    else
                    {
                        ToastUtils.showToastWithoutStrResources(ctx, "Please Enter Innov_ID first");
                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void AlertforLogout() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.luanchicon);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ctx, LoginScreen.class);
//                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }
}
