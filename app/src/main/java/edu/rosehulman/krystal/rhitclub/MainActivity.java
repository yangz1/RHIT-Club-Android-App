package edu.rosehulman.krystal.rhitclub;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.krystal.rhitclub.fragments.ClubDetailFragment;
import edu.rosehulman.krystal.rhitclub.fragments.ClubListFragment;
import edu.rosehulman.krystal.rhitclub.fragments.EventDetailFragment;
import edu.rosehulman.krystal.rhitclub.fragments.EventListFragment;
import edu.rosehulman.krystal.rhitclub.fragments.HomePageFragment;
import edu.rosehulman.krystal.rhitclub.fragments.LoginFragment;
import edu.rosehulman.krystal.rhitclub.fragments.MyAccountFragment;
import edu.rosehulman.krystal.rhitclub.utils.Club;
import edu.rosehulman.krystal.rhitclub.utils.Event;
import edu.rosehulman.krystal.rhitclub.utils.User;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnStartPressedListener,
        ClubListFragment.OnClubSelectedListener, HomePageFragment.OnHomepageSelectedListener,
        EventListFragment.OnEventSelectedListener, ClubDetailFragment.OnFlingListener,
        MyAccountFragment.OnMyAccountSelectedListener{
    private LoginFragment mLoginFrag;
    private HomePageFragment mHomeFrag;
    private FloatingActionButton mFab;
    private Menu mMenu;
    private static User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUser = new User("Krystal",Club.initializeClubs(),Event.initializeEvents());
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnMyAccountSelected(mUser);
            }
        });

        if (savedInstanceState == null) {
            mFab.setVisibility(View.GONE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mLoginFrag = new LoginFragment();
            ft.add(R.id.content_main, mLoginFrag);
            ft.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        menu.findItem(R.id.menu_home).setVisible(false);
        menu.findItem(R.id.log_out).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.log_out:
                mFab.setVisibility(View.GONE);
                mMenu.findItem(R.id.menu_home).setVisible(false);
                mMenu.findItem(R.id.log_out).setVisible(false);
                fm.popBackStackImmediate();
                mLoginFrag = new LoginFragment();
                ft.replace(R.id.content_main, mLoginFrag);
                ft.commit();
                return true;
            case R.id.menu_home:
                ft.replace(R.id.content_main, mHomeFrag);
                ft.addToBackStack("Homepage");
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static User getmUser(){
        return mUser;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public  void onStartPressed() {
        mFab.setVisibility(View.VISIBLE);
        mMenu.findItem(R.id.menu_home).setVisible(true);
        mMenu.findItem(R.id.log_out).setVisible(true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mHomeFrag = new HomePageFragment();
        ft.replace(R.id.content_main,mHomeFrag);
        ft.addToBackStack("Homepage");
        ft.commit();
    }

    @Override
    public void onClubButtonSelected() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment clubListFrag = new ClubListFragment();
        ft.replace(R.id.content_main,clubListFrag);
        ft.addToBackStack("Club_List");
        ft.commit();
    }

    @Override
    public void onEventButtonSelected() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment eventListFrag = new EventListFragment();
        ft.replace(R.id.content_main,eventListFrag);
        ft.addToBackStack("Event_List");
        ft.commit();
    }

    @Override
    public void onClubSelected(Club club) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = ClubDetailFragment.newInstance(club);
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack("Club_detail");
        ft.commit();
    }

    @Override
    public void onSwipe() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fm.popBackStackImmediate();
        ft.commit();
    }

    @Override
    public void onEventSelected(Event event) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = EventDetailFragment.newInstance(event);
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack("Event_detail");
        ft.commit();
    }

    @Override
    public void OnMyAccountSelected(User user) {
        mUser = user;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment myAccountFrag = MyAccountFragment.newInstance(user);
        ft.replace(R.id.content_main,myAccountFrag);
        ft.addToBackStack("my_account");
        ft.commit();
    }

}
