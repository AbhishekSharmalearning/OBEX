package com.application.innove.obex.ObexActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.application.innove.obex.ObexAdapters.DrawerAdapter;
import com.application.innove.obex.ObexModel.Document;
import com.application.innove.obex.ObexModel.DocumentResponseModel;
import com.application.innove.obex.ObexModel.DrawerItem;
import com.application.innove.obex.ObexModel.Glst;
import com.application.innove.obex.ObexModel.Info;
import com.application.innove.obex.ObexModel.LoginResponseModel;
import com.application.innove.obex.R;
import com.application.innove.obex.Retrofit.WebAPI;
import com.application.innove.obex.Utilityclass.SimpleDividerItemDecoration;
import com.application.innove.obex.Utilityclass.StatusBar;
import com.application.innove.obex.Utilityclass.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomescreenActivity extends com.application.innove.obex.ObexActivities.AppCompatActivity
{

    private Toolbar mToolbar;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerAdapter mDrawerAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<DrawerItem> mDrawerItemList;
    private boolean isSearchOpened = false;
    private DrawerLayout mDrawerLayout;
    private EditText edtSeach;
    private MenuItem mSearchAction;
    private ActionBarDrawerToggle mDrawerToggle;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreenactivity);
        ButterKnife.inject(this);
        ctx = this;
        StatusBar.MatchStatusBGEqualsAndAboveLollipop(this, ctx);


        mToolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Dummy Data
        mDrawerItemList = new ArrayList<DrawerItem>();
        DrawerItem item = new DrawerItem();
        item.setIcon(R.drawable.ic_home_white_24dp);
        item.setTitle("Upload Acknowledgement");
        mDrawerItemList.add(item);

        DrawerItem item2 = new DrawerItem();
        item2.setIcon(R.drawable.ic_notifications_white_24dp);
        item2.setTitle("Upload Documents");
        mDrawerItemList.add(item2);


        DrawerItem item3 = new DrawerItem();
        item3.setIcon(R.drawable.ic_timer_white_24dp);
        item3.setTitle("Mark as Acknowledgement");
        mDrawerItemList.add(item3);


        mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);

        DrawerAdapter adapter = new DrawerAdapter(mDrawerItemList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                //TODO Add some action here
                //Executed when drawer closes


            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                //TODO Add some action here
                //executes when drawer open

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        adapter.setOnItemClickLister(new DrawerAdapter.OnItemSelecteListener()
        {
            @Override
            public void onItemSelected(View v, int position)
            {
                if (position == 1)
                {
//                    GetDocumentDetails();
//                    showProgressDialog();
//                    Intent in = new Intent(ctx, Document_capture_and_upload.class);
//                    startActivity(in);
                    Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.setClassName(ctx,"com.application.innove.obex.ObexActivities.Document_capture_and_upload");
                    startActivity(intent);


                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            AlertforLogout();
            return true;
        }
        else
        {
            handleMenuSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch()
    {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened)
        { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(false); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_white_24dp));

            isSearchOpened = false;
        }
        else
        { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            edtSeach.setHint("Enter your Innov ID");
            edtSeach.setHintTextColor(Color.parseColor("#FFFFFF"));


            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    {
                        doSearch();
                        return true;
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

    private void doSearch()
    {
        //
    }


    @Override
    public void onBackPressed()
    {
        backButtonHandler();
    }

    private void backButtonHandler()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.luanchicon);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
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
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }

    private void AlertforLogout()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.luanchicon);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent intent = new Intent(ctx, LoginScreen.class);
//                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
//                        Intent in = new Intent(ctx, LoginScreen.class);
//                        startActivity(in);
//                        finish();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }
}
