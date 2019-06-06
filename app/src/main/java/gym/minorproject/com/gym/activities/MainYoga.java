package gym.minorproject.com.gym.activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.adapter.YogaAdapter;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.helper.YogaUtil;

public class MainYoga extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener {

    TextView txtnavText;
    String navViewData;

    ListView list;
    ArrayList<ExerciseNameBean> yogaList;
    YogaAdapter yogaAdapter;
    ContentResolver resolver;

    SharedPreferences preferences;
    boolean loginflag=false;
    String loginname="";

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    boolean IsNetworkConnected() {
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        return(networkInfo!=null&&networkInfo.isConnected());

    }

    ProgressDialog progressDialog;

    void init() {
        list = (ListView)findViewById(R.id.list_view);
        yogaList = new ArrayList<>();
        resolver = getContentResolver();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting Data From Cloud");
    }

    void QueryFromDB() {
        String[] projection = {YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE};
        Cursor cursor = resolver.query(YogaUtil.uri_yoga_type,projection,null,null,null);

        String Exercise_Name="";
        String Exercise_Image="";

        while(cursor.moveToNext()) {
            Exercise_Name=cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            Exercise_Image=cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            yogaList.add(new ExerciseNameBean(Exercise_Name,Exercise_Image));
        }
        cursor.close();
        yogaAdapter = new YogaAdapter(this, R.layout.listview_list_item, yogaList);
        list.setAdapter(yogaAdapter);
        list.setOnItemClickListener(this);


        preferences = getSharedPreferences(YogaUtil.SPFileName,MODE_PRIVATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gym);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        QueryFromDB();


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);

        txtnavText =header.findViewById(R.id.nav_header_text);


        loginflag = preferences.getBoolean(YogaUtil.loginflag,false);
        loginname=preferences.getString(YogaUtil.loginName,"");

        if(loginflag) {
         txtnavText.setText("Welcome "+preferences.getString(YogaUtil.loginName,""));
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

                if(IsNetworkConnected()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(YogaUtil.loginflag, false);
                    editor.putString(YogaUtil.loginName, "");
                    editor.putString(YogaUtil.loginPassword, "");
                    editor.commit();
                    deleteDataFromPhone();
                    Intent intent = new Intent(this, MainYoga.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.nav_fav:
                i = new Intent(MainYoga.this, Favourite.class);
                startActivity(i);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    int pos;
    ExerciseNameBean bean;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos=position;
        bean = yogaList.get(pos);
        Intent i;

        switch(bean.getExercise_name()) {
            case YogaUtil.SITTING:
                i = new Intent(MainYoga.this, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.SITTING);
                startActivity(i);
                break;
            case YogaUtil.STANDING:
                i = new Intent(MainYoga.this, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.STANDING);
                startActivity(i);
                break;
            case YogaUtil.STOMACH:
                i = new Intent(MainYoga.this, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.STOMACH);
                startActivity(i);
                break;
            case YogaUtil.BACK:
                i = new Intent(MainYoga.this, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.BACK);
                startActivity(i);
                break;
            case YogaUtil.BREATHING:
                i = new Intent(MainYoga.this, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.BREATHING);
                startActivity(i);
                break;

        }

    }

    void DialogRateUs() {

    }

    void deleteAllFavCloud() {

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,YogaUtil.DELETEALL_FAV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                Toast.makeText(MainYoga.this,"Response is: "+ response,Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainYoga.this,"That didn't work!"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put("LOGIN_ID",loginname);



                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        progressDialog.dismiss();

    }

    void deleteDataFromPhone() {
        ContentValues values = new ContentValues();

        values.put(YogaUtil.FAV,0);
        resolver.update(YogaUtil.uri_exercise_tab,values,null,null);

        Toast.makeText(getApplicationContext(),"You Successfully logged out",Toast.LENGTH_LONG).show();

    }
}

