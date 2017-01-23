package edu.rosehulman.krystal.rhitclub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.EventListener;

import edu.rosehulman.krystal.rhitclub.fragments.ClubListFragment;
import edu.rosehulman.krystal.rhitclub.fragments.EventListFragment;
import edu.rosehulman.krystal.rhitclub.fragments.HomePageFragment;
import edu.rosehulman.krystal.rhitclub.fragments.LoginFragment;
import edu.rosehulman.krystal.rhitclub.utils.Club;
import edu.rosehulman.krystal.rhitclub.utils.Event;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnStartPressedListener,
        ClubListFragment.OnClubSelectedListener, HomePageFragment.OnHomepageSelectedListener,
        EventListFragment.OnEventSelectedListener{
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
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, mLoginFrag);
                ft.addToBackStack("Loginpage");
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        Fragment fragment = PaintingDetailFragment.newInstance(painting);
//
//        // TODO: Create a transition.
//        Slide slideTransition = new Slide(Gravity.RIGHT);
//        slideTransition.setDuration(200);
//        fragment.setEnterTransition(slideTransition);
//
//        ft.replace(R.id.fragment_container, fragment);
//        ft.addToBackStack("detail");
//        ft.commit();
    }

//
//    @Override
//    public void onSwipe() {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        fm.popBackStackImmediate();
//        ft.commit();
//    }


    @Override
    public void onEventSelected(Event event) {

    }
}
