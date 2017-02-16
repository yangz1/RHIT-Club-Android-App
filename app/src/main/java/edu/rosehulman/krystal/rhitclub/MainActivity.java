package edu.rosehulman.krystal.rhitclub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
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
import edu.rosehulman.krystal.rhitclub.utils.GetClubs;
import edu.rosehulman.krystal.rhitclub.utils.GetEvents;
import edu.rosehulman.krystal.rhitclub.utils.GetUser;
import edu.rosehulman.krystal.rhitclub.utils.User;
import edu.rosehulman.rosefire.Rosefire;
import edu.rosehulman.rosefire.RosefireResult;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnStartPressedListener,
        ClubListFragment.OnClubSelectedListener, HomePageFragment.OnHomepageSelectedListener,
        EventListFragment.OnEventSelectedListener, ClubDetailFragment.OnFlingListener,
        MyAccountFragment.OnMyAccountSelectedListener, LoginFragment.OnLoginListener,
        GetClubs.ClubsConsumer,GetEvents.EventsConsumer, GetUser.UserConsumer{

    private static final int RC_ROSEFIRE_LOGIN = 1;
    private LoginFragment mLoginFrag;
    private HomePageFragment mHomeFrag;
    private FloatingActionButton mFab;
    private Menu mMenu;
    private static User mUser;
    private List<Club> clubs;
    private List<Event> events;
    public static String token;
    public static String username;
    private boolean completeFlag1=false;
    private boolean completeFlag2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clubs = new ArrayList<>();
        events = new ArrayList<>();

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnMyAccountSelected(mUser);
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
        mMenu = menu;
        changeMenuFAB(false);
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
                changeMenuFAB(false);
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

    public static User getUser(){
        return mUser;
    }

    public void setUser(User us){
        mUser = us;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Club findClubByName(String name){
        for(Club c:clubs){
            Log.d("FindClubByName ",c.getName()+" "+name);
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    public Event findEventByName(String name){
        for(Event e:events){
            Log.d("FindEventByName ",e.getName()+" "+name);
            if(e.getName().equals(name)){
                return e;
            }
        }
        return null;
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
        if(mLoginFrag.getLogin()) {
            changeMenuFAB(true);
            initial();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mHomeFrag = new HomePageFragment();
            ft.replace(R.id.content_main, mHomeFrag);
            ft.commit();
        }else{
            showLoginError("Wrong username or password. Please use your Rose account.");
        }
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

    @Override
    public void onRosefireLogin(){
        Intent signInIntent = Rosefire.getSignInIntent(this, getString(R.string.rosefire_key));
        startActivityForResult(signInIntent, RC_ROSEFIRE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_ROSEFIRE_LOGIN){
            RosefireResult result = Rosefire.getSignInResultFromIntent(data);
            if(result.isSuccessful()){
                token = result.getToken();
                username = result.getUsername();
                mLoginFrag.getmListener().onStartPressed();
            }else{
                showLoginError("Rosefire sign-in failed");
            }
        }
    }

    private void showLoginError(String message) {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("Login");
        loginFragment.onLoginError(message);
    }

    public void changeMenuFAB(boolean flag){
        if(flag){
            mFab.setVisibility(View.VISIBLE);
            mMenu.findItem(R.id.menu_home).setVisible(true);
            mMenu.findItem(R.id.log_out).setVisible(true);
        }else{
            mFab.setVisibility(View.GONE);
            mMenu.findItem(R.id.menu_home).setVisible(false);
            mMenu.findItem(R.id.log_out).setVisible(false);
        }
    }

    public String getToken(){
        return token;
    }
    public String getUsername(){
        return username;
    }

    public void initial(){
        (new GetClubs(this)).execute("https://club-app.csse.rose-hulman.edu/api/clubs/");
        (new GetEvents(this)).execute("https://club-app.csse.rose-hulman.edu/api/events/");
        (new GetUser(this)).execute("https://club-app.csse.rose-hulman.edu/api/users/"+username);
    }

    @Override
    public void onClubsLoaded(HashMap<String,Club> club) {
        Log.d("Keys",club.keySet().toString());
        for(String c:club.keySet()){
            clubs.add(club.get(c));
        }
        Log.d("Clubs",clubs.toString());
    }

    @Override
    public void onEventsLoaded(HashMap<String, Event> event) {
        // TODO
        for(String e:event.keySet()){
            Event eve = event.get(e);
            eve.setRoom("Kahn Room - Union");
            events.add(eve);
        }
    }

    @Override
    public void onUserLoaded(User user) {
        mUser = user;
    }
}
