package gym.minorproject.com.gym;

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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Favourite extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    GridView list;
    ArrayList<exercise_nameBean> favYogaList;
    exercises_adapter favYogaAdapter;
    ContentResolver resolver;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    String Name_E = "";
    String Name_H = "";
    String Steps = "";
    String Benefits = "";
    String Contraindications = "";
    String Video_Url = "";
    String Images = "";
    String Steps_Images = "";
    String image;
    String exercise_type = "";
    int asan_id = 0;
    Exercise_Yoga_Bean exercise_yoga_bean;
    String muscles="";

    SharedPreferences preferences;
    boolean loginflag=false;
    String spLoginId="";



    void init()
    {
        list = (GridView)findViewById(R.id.grid_view);
        favYogaList = new ArrayList<>();
        resolver = getContentResolver();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        preferences = getSharedPreferences(YogaUtil.SPFileName,MODE_PRIVATE);
        loginflag = preferences.getBoolean(YogaUtil.loginflag,false);
        if(loginflag)
        {
            spLoginId=preferences.getString(YogaUtil.loginName,"");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        init();
        retrieve_all_fav();

        setTitle("Favourites");

        if(!IsNetworkConnected())
        {
         Toast.makeText(getApplicationContext(),"Please Connect TO Mobile Data or Wifi To View Updates",Toast.LENGTH_LONG).show();
        }


    }
    boolean IsNetworkConnected()
    {
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        return(networkInfo!=null&&networkInfo.isConnected());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void QueryFromDB1()
    {
        String[] projection =
                {
                        YogaUtil.EXERCISE_ID, YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE, YogaUtil.EXERCISE_NAME_H,
                        YogaUtil.EXERCISE_STEPS, YogaUtil.EXERCISE_STEP_IMAGES, YogaUtil.EXERCISE_CONTRAINDICATIONS,
                        YogaUtil.EXERCISE_BENEFITS, YogaUtil.EXERCISE_VIDEO, YogaUtil.EXERCISE_MUSCLES, YogaUtil.EXERCISE_TYPE
                };

        String where = YogaUtil.EXERCISE_ID + " = " + eb.getId();
        Cursor cursor = resolver.query(YogaUtil.uri_exercise_tab, projection, where, null, null);

        if (cursor != null)
        {
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

    void retrieve_all_fav()
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,YogaUtil.RETRIEVE_ALL_FAV,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                    Toast.makeText(Favourite.this,"JSON DATA  "+response,Toast.LENGTH_LONG).show();
                    if (jsonArray != null)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {

                            JSONObject jObj = jsonArray.getJSONObject(i);
                            exercise_nameBean exerciseYogaBean = new exercise_nameBean();
                            exerciseYogaBean.setId(jObj.getInt("ASAN_ID"));
                            exerciseYogaBean.setExercise_name(jObj.getString(YogaUtil.EXERCISE_NAME_E));
                            exerciseYogaBean.setExercise_image(jObj.getString(YogaUtil.EXERCISE_IMAGE));

                            favYogaList.add(exerciseYogaBean);


                        }
                    }
                    favYogaAdapter = new exercises_adapter(Favourite.this, R.layout.grid_list_item, favYogaList);
                    list.setAdapter(favYogaAdapter);
                    list.setOnItemClickListener(Favourite.this);

                }
                catch (JSONException e)
                {
                    Toast.makeText(Favourite.this,"Some JSON Exception "+e ,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Toast.makeText(Favourite.this,"That didn't work! "+error,Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String ,String> map = new HashMap<>();
                map.put(YogaUtil.COLLOGINID,spLoginId);


                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void alertDialogBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"Delete","View"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        deleteExerciseFromFav();


                        break;
                    case 1:
                        QueryFromDB1();
                        put_IN_YOGABEAN();
                        Intent j = new Intent(Favourite.this, Exercise_Details.class);
                        j.putExtra("yoga_bean", exercise_yoga_bean);
                        startActivity(j);
                        break;
                }
            }
        });
        builder.create().show();
    }

    void deleteFAVInCLoud()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,YogaUtil.DELETE_FAV, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                // Display the first 500 characters of the response string.
                Toast.makeText(Favourite.this,"Response is: "+ response,Toast.LENGTH_LONG).show();

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Toast.makeText(Favourite.this,"That didn't work!"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String ,String> map = new HashMap<>();
                map.put(YogaUtil.COLLOGINID,spLoginId);
                map.put("ASAN_ID",String.valueOf(exercise_yoga_bean.getId()));
                map.put(YogaUtil.EXERCISE_TYPE,exercise_yoga_bean.getExercise_Type());


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

    void deleteExerciseFromFav()
    {
        eb.setFav_id(0);
        QueryFromDB1();
        put_IN_YOGABEAN();
        updateFavintoDB();
        deleteFAVInCLoud();
        favYogaList.remove(pos);
        favYogaAdapter.notifyDataSetChanged();


    }

    void updateFavintoDB()
    {
        ContentValues values = new ContentValues();
        values.put(YogaUtil.FAV, eb.getFav_id());
        String where = YogaUtil.EXERCISE_ID + " = " + eb.getId();
        int i = resolver.update(YogaUtil.uri_exercise_tab, values, where, null);
        if (i > 0)
        {
            Toast.makeText(this, "Favourite Removed: " + i, Toast.LENGTH_LONG).show();
        }
    }

    void put_IN_YOGABEAN()
    {
        exercise_yoga_bean = new Exercise_Yoga_Bean(Name_E, Name_H, Steps, Benefits,
                Contraindications, Video_Url, Images, Steps_Images, muscles, exercise_type, asan_id);
    }


    int pos;
    exercise_nameBean eb;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        pos = position;
        eb = favYogaList.get(pos);

        alertDialogBox();

    }
}

