package gym.minorproject.com.gym.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RatingBar;
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
import gym.minorproject.com.gym.adapter.ExerciseAdapter1;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;

public class ExerciseList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    RatingBar ratingBar;
    GridView list;
    ArrayList<ExerciseNameBean> yogaList;
    ExerciseAdapter1 yogaAdapter;
    ContentResolver resolver;
    String Name_E = "";
    String Name_H = "";
    String Steps = "";
    String Benefits = "";
    String Contraindications = "";
    String Video_Url = "";
    String Steps_Images = "";
    String image="";
    String exercise_type = "";
    String keyExerciseType = "";
    int asan_id = 0;
    ExerciseYogaBean exercise_yoga_bean;
    String muscles;
    int exercise_id;
    int fav_id;
    String text_fav_dialog_box = "Mark Exercise As Favourite";

    ProgressDialog progressDialog;


    SharedPreferences preferences;
    boolean loginflag=false;
    String spLoginId="";

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    void init() {
        list = findViewById(R.id.grid_view);
        yogaList = new ArrayList<>();
        resolver = getContentResolver();
        ratingBar = findViewById(R.id.fav);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait .......");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void QueryFromDB() {
        String[] projection = {YogaUtil.EXERCISE_TYPE, YogaUtil.EXERCISE_ID, YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE, YogaUtil.FAV};
        String where = YogaUtil.EXERCISE_TYPE + " like '" + keyExerciseType + "'";
        Cursor cursor = resolver.query(YogaUtil.uri_exercise_tab, projection, where, null, null);
        while (cursor.moveToNext()) {
            Name_E = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            image = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            exercise_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.EXERCISE_ID));
            fav_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.FAV));
            yogaList.add(new ExerciseNameBean(Name_E, image, fav_id, exercise_id));
        }
        cursor.close();
        yogaAdapter = new ExerciseAdapter1(this, R.layout.grid_list_item, yogaList);

        list.setAdapter(yogaAdapter);
        list.setOnItemClickListener(this);
    }

    void QueryFromDB1() {
        String[] projection = {
                        YogaUtil.EXERCISE_ID, YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE, YogaUtil.EXERCISE_NAME_H,
                        YogaUtil.EXERCISE_STEPS, YogaUtil.EXERCISE_STEP_IMAGES, YogaUtil.EXERCISE_CONTRAINDICATIONS,
                        YogaUtil.EXERCISE_BENEFITS, YogaUtil.EXERCISE_VIDEO, YogaUtil.EXERCISE_MUSCLES, YogaUtil.EXERCISE_TYPE
                };

        String where = YogaUtil.EXERCISE_ID + " = " + eb.getId();
        Cursor cursor = resolver.query(YogaUtil.uri_exercise_tab, projection, where, null, null);

        if (cursor != null) {
            cursor.moveToNext();
            asan_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.EXERCISE_ID));
            exercise_type = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_TYPE));
            Name_E = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            image = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            Name_H = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_H));
            Steps = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_STEPS));
            Steps_Images = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_STEP_IMAGES));
            Contraindications = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_CONTRAINDICATIONS));
            Benefits = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_BENEFITS));
            Video_Url = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_VIDEO));
            muscles = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_MUSCLES));
        }
        cursor.close();
    }

    void put_IN_YOGABEAN() {
        exercise_yoga_bean = new ExerciseYogaBean(Name_E, Name_H, Steps, Benefits,
                Contraindications, Video_Url,
                image, Steps_Images, muscles, exercise_type, asan_id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        init();

        Intent rcv = getIntent();
        boolean flag = rcv.hasExtra("keyExerciseType");
        if (flag) {

            keyExerciseType = rcv.getStringExtra("keyExerciseType");
        }

        QueryFromDB();
        setTitle(keyExerciseType);
        preferences = getSharedPreferences(YogaUtil.SPFileName,MODE_PRIVATE);
        loginflag = preferences.getBoolean(YogaUtil.loginflag,false);
        if(loginflag) {
            spLoginId=preferences.getString(YogaUtil.loginName,"");
        }
    }


    int pos;
    ExerciseNameBean eb;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        eb = yogaList.get(pos);

        if (eb.getFav_id() == 0) {
            text_fav_dialog_box = "Mark Exercise As Favourite";
        }
        if (eb.getFav_id() == 1) {
            text_fav_dialog_box = "Remove As Favourite";
        }
        alertDialogBox();
    }

    void alertDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {text_fav_dialog_box, "View Exercise"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (IsNetworkConnected()) {
                            if (loginflag) {
                                if (eb.getFav_id() == 0) {
                                    addExerciseToFav();
                                } else
                                    {
                                    deleteExerciseFromFav();
                                }
                                yogaList.set(pos, new ExerciseNameBean(eb.getExercise_name(), eb.getExercise_image(), eb.getFav_id(), eb.getId()));
                                yogaAdapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(ExerciseList.this, "First Register Yourself to use this Feature", Toast.LENGTH_LONG).show();
                                }
                        }

                        else {
                            Toast.makeText(getApplicationContext(),"First On Mobile Data Or WiFi",Toast.LENGTH_LONG).show();
                        }

                        break;
                    case 1:
                        QueryFromDB1();
                        put_IN_YOGABEAN();
                        Intent j = new Intent(ExerciseList.this, ExerciseDetails.class);
                        j.putExtra("yoga_bean", exercise_yoga_bean);
                        startActivity(j);
                        break;
                }
            }
        });
        builder.create().show();
    }

    void addExerciseToFav() {
        eb.setFav_id(1);
        QueryFromDB1();
        put_IN_YOGABEAN();
        insertFAVInCLoud();
        updateFavintoDB();
    }

    void deleteExerciseFromFav()
    {
        eb.setFav_id(0);
        QueryFromDB1();
        put_IN_YOGABEAN();
        updateFavintoDB();
        deleteFAVInCLoud();

    }

    void updateFavintoDB() {
        ContentValues values = new ContentValues();
        values.put(YogaUtil.FAV, eb.getFav_id());
        String where = YogaUtil.EXERCISE_ID + " = " + eb.getId();
        int i = resolver.update(YogaUtil.uri_exercise_tab, values, where, null);
        if (i > 0) {
            Toast.makeText(this, "Favourite Updated: " + i, Toast.LENGTH_LONG).show();
        }
    }

    boolean IsNetworkConnected() {
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        return(networkInfo!=null&&networkInfo.isConnected());

    }

    void insertFAVInCLoud() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,YogaUtil.INSERT_FAV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                Toast.makeText(ExerciseList.this,"Response is: "+ response,Toast.LENGTH_LONG).show();

            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ExerciseList.this,"That didn't work!"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put("LOGIN_ID",spLoginId);
                map.put("ASAN_ID",String.valueOf(exercise_yoga_bean.getId()));
                map.put(YogaUtil.EXERCISE_TYPE,exercise_yoga_bean.getExercise_Type());
                map.put(YogaUtil.EXERCISE_NAME_E,exercise_yoga_bean.getName_E());
                map.put(YogaUtil.EXERCISE_NAME_H,exercise_yoga_bean.getName_H());
                map.put(YogaUtil.EXERCISE_STEPS,exercise_yoga_bean.getStringSteps());
                map.put(YogaUtil.EXERCISE_IMAGE,exercise_yoga_bean.getImages());
                map.put(YogaUtil.EXERCISE_BENEFITS,exercise_yoga_bean.getStringBenefits());
                map.put(YogaUtil.EXERCISE_MUSCLES,exercise_yoga_bean.getStringMuscles());
                map.put(YogaUtil.EXERCISE_STEP_IMAGES,exercise_yoga_bean.getStringStepsImages());
                map.put(YogaUtil.EXERCISE_VIDEO,exercise_yoga_bean.getVideo_Url());
                map.put(YogaUtil.EXERCISE_CONTRAINDICATIONS,exercise_yoga_bean.getStringContraindications());

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

    void deleteFAVInCLoud() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,YogaUtil.DELETE_FAV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                Toast.makeText(ExerciseList.this,"Response is: "+ response,Toast.LENGTH_LONG).show();
                }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ExerciseList.this, "That didn't work!" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put(YogaUtil.COLLOGINID,spLoginId);
                map.put("ASAN_ID",String.valueOf(exercise_yoga_bean.getId()));
                map.put(YogaUtil.EXERCISE_TYPE,exercise_yoga_bean.getExercise_Type());


                return map;
            }};
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        progressDialog.dismiss();

    }
}
