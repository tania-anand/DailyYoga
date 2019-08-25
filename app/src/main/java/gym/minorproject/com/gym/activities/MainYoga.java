package gym.minorproject.com.gym.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.contract.MainContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.UtilActivity;
import gym.minorproject.com.gym.presenter.MainPresenter;

public class MainYoga extends UtilActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.viewer{
    private static final String TAG = "MainYoga";

    private boolean loginflag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gym);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainPresenter presenter = new MainPresenter(this, this);
        presenter.setAdapter();


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView txtnavText = header.findViewById(R.id.nav_header_text);

        loginflag = SharedPreferencesUtil.getInstance(getApplicationContext()).getLoggedIn();

        if(loginflag) {
         txtnavText.setText("Welcome "+ SharedPreferencesUtil.getInstance(getApplicationContext()).getUser().getEmailId());
        }
        else {
            txtnavText.setText("Welcome User");
        }

        // for making menu item invisible
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        Menu subMenu = menuItem.getSubMenu();
        if(loginflag) {
            MenuItem menuItem2 = subMenu.getItem(0);
            menuItem2.setTitle("ViewData");;
        }
        else {
            MenuItem menuItem1 = subMenu.getItem(1);
            menuItem1.setVisible(false);
            MenuItem menuItem2 = subMenu.getItem(2);
            menuItem2.setVisible(false);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;
        switch(id) {
            case R.id.nav_send :
                 i = new Intent(MainYoga.this, Feedback.class);
                startActivity(i);
            break;
            case R.id.rate_us :
               i = new Intent(MainYoga.this, RateUS.class);
                startActivity(i);
                break;
            case R.id.nav_about_us :
                i = new Intent(MainYoga.this, AboutUs.class);
                startActivity(i);
                break;
            case R.id.nav_user:
                if(loginflag) {
                    i = new Intent(MainYoga.this, ViewUserDetails.class);
                    startActivity(i);
                }
                else {
                    i = new Intent(MainYoga.this, Login.class);
                    startActivity(i);
                }
                break;
            case R.id.nav_logout:
                    SharedPreferencesUtil.getInstance(getApplicationContext()).setLoggedIn(false);
                    SharedPreferencesUtil.getInstance(getApplicationContext()).saveUser(null);
                    new DBHelper(getApplicationContext()).deleteDataFromPhone();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, MainYoga.class);
                    startActivity(intent);
                    finish();
                break;
            case R.id.nav_fav:
                i = new Intent(MainYoga.this, Favourite.class);
                startActivity(i);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

