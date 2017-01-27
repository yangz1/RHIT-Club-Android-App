package edu.rosehulman.krystal.rhitclub;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (savedInstanceState == null) {
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.log_out:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                LoginFragment login = new LoginFragment();
                ft.replace(R.id.content_main, login);
                fm.popBackStackImmediate();
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    }
}
