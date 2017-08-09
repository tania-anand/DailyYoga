package gym.minorproject.com.gym;

import android.*;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Yoga_Login extends AppCompatActivity implements View.OnClickListener
{

    @InjectView(R.id.user_id)
    EditText eduserId;

    @InjectView(R.id.password)
    EditText edpassword;

    @InjectView(R.id.submit)
    Button btnlogin;

    @InjectView(R.id.register_page)
    TextView txtRegisterPage;


    SharedPreferences preferences;
    boolean loginflag = false;


    signUpBean bean;

    ProgressDialog progressDialog;

    int login_success = 0;

    ContentResolver resolver;

    ArrayList<Integer> asan_ids;


    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    boolean IsNetworkConnected()
    {
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        return(networkInfo!=null&&networkInfo.isConnected());

    }

    private void init()
    {

        btnlogin.setOnClickListener(this);
        txtRegisterPage.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.........");
        progressDialog.setCancelable(false);

        resolver = getContentResolver();


        asan_ids= new ArrayList<>();
    }

    void insertDataIntoSignUpBean()
    {
        bean = new signUpBean();
        bean.setPassword(edpassword.getText().toString());
        bean.setULoginId(eduserId.getText().toString());
    }

    void clearFields()
    {
        edpassword.setText("");
        eduserId.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__login);

        ButterKnife.inject(this);

        init();

        // for action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void CheckFromCloud()
    {
        progressDialog.show();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, YogaUtil.LOGINQUERY_DATA_PHP, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
//                        Toast.makeText(getApplicationContext(),"In Response ",Toast.LENGTH_LONG).show();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            login_success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            if (login_success == 1)
                            {
                                Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();

                                preferences = getSharedPreferences(YogaUtil.SPFileName, MODE_PRIVATE);
                                loginflag = true;

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(YogaUtil.loginflag, loginflag);
                                editor.putString(YogaUtil.loginName, bean.getULoginId());
                                editor.putString(YogaUtil.loginPassword, bean.getPassword());
                                editor.commit();

                                handler.sendEmptyMessage(101);

//                                Toast.makeText(Yoga_Login.this, "login flag of shared prefrences " + loginflag, Toast.LENGTH_LONG).show();
//                                Toast.makeText(Yoga_Login.this, "hello " + response, Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                    progressDialog.dismiss();

                                    Toast.makeText(Yoga_Login.this, message, Toast.LENGTH_LONG).show();
//                                    AlertDialog.Builder dialog = new AlertDialog.Builder(Yoga_Login.this);
//                                    dialog.setMessage("User Id Or Password You Entered is Wrong Enter Again");
//                                    dialog.setCancelable(false);
//                                    dialog.setPositiveButton("OK", null);
//                                    AlertDialog dialog1 = dialog.create();
//                                    dialog1.show();
                                    clearFields();

                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Yoga_Login.this, "That didn't work!" + error, Toast.LENGTH_LONG).show();
                            }
                        }
                )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();

                map.put(YogaUtil.COLLOGINID, bean.getULoginId());
                map.put(YogaUtil.COLPASSWORD, bean.getPassword());

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

    // validations for login page
    boolean validateLoginFields()
    {
        boolean Flag = true;

        if (eduserId.getText().toString().isEmpty())
        {
            eduserId.setError("User Id Cant Be Empty");
            Flag = false;
        }
        if (edpassword.getText().toString().isEmpty())
        {
            edpassword.setError("Password Cant Be Empty");
            Flag = false;
        }
        return Flag;
    }


    @Override
    public void onClick(View v)
    {
        int i = v.getId();

        if(IsNetworkConnected())
        {
            switch (i)
            {
                case R.id.submit:

                    if (validateLoginFields())
                    {
                        insertDataIntoSignUpBean();
                        handler.sendEmptyMessage(100);


                    }

                    break;

                case R.id.register_page:
                    Intent intent = new Intent(Yoga_Login.this, Yoga_Register.class);
                    startActivity(intent);
                    finish();

                    break;
            }
        }

        else
            Toast.makeText(getApplicationContext(),"Please Connect TO Internet",Toast.LENGTH_LONG).show();
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what==100)
            {
                CheckFromCloud();
            }

            if(msg.what==101)
            {
                retrieve_all_fav();
            }
        }
    };


    void updateFavintoDB(int asan_id)
    {
        ContentValues values = new ContentValues();
        values.put(YogaUtil.FAV, 1);
        String where = YogaUtil.EXERCISE_ID + " = " +asan_id ;
        int i = resolver.update(YogaUtil.uri_exercise_tab, values, where, null);
        if (i > 0)
        {
            Toast.makeText(this, "Favourite Updated: " , Toast.LENGTH_LONG).show();
        }
    }

    void retrieve_all_fav()
    {
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

                     int asan_id;
//                    Toast.makeText(Yoga_Login.this,"JSON DATA  "+response,Toast.LENGTH_LONG).show();
                    if (jsonArray != null)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {

                            JSONObject jObj = jsonArray.getJSONObject(i);
                            asan_id=jObj.getInt("ASAN_ID");

                            updateFavintoDB(asan_id);

                        }
                    }
                    progressDialog.dismiss();
                    Intent i = new Intent(Yoga_Login.this, MainYoga.class);
                    startActivity(i);


                }
                catch (JSONException e)
                {
                    Toast.makeText(Yoga_Login.this,"NO DATA FOR FAVOURITES " ,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }



            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Toast.makeText(Yoga_Login.this,"That didn't work! "+error,Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String ,String> map = new HashMap<>();
                map.put(YogaUtil.COLLOGINID,bean.getULoginId());


                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy
                (
                50000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
