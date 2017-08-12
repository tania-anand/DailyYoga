package gym.minorproject.com.gym;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewUserDetails extends AppCompatActivity implements View.OnClickListener
{
    @InjectView(R.id.VRName)
    TextView txtName;
    @InjectView(R.id.VREmailId)
    TextView txtEmail;
    @InjectView(R.id.VRUserName)
    TextView txtLogin;
    @InjectView(R.id.VRPhoneNo)
    TextView txtPhoneNo;
//    @InjectView(R.id.VRPassword)
//    TextView txtPassword;
    @InjectView(R.id.buttonView)
    Button button;

    signUpBean bean;
    signUpBean rcvbean;


    SharedPreferences preferences;
    boolean loginflag=false;
    String preferenceLName="";
    String preferenceLPassword="";

    StringRequest request;
    ProgressDialog dialog;




    void init()
    {
        button.setOnClickListener(this);

        preferences = getSharedPreferences(YogaUtil.SPFileName,MODE_PRIVATE);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait .......");


    }

    void setDataToTextView()
    {
        txtName.setText(rcvbean.getName());
        txtEmail.setText(rcvbean.getEmailId());
//        txtPassword.setText(rcvbean.getPassword());
        txtPhoneNo.setText(rcvbean.getPhoneNo());
        txtLogin.setText(rcvbean.getULoginId());

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user_details);

        ButterKnife.inject(this);

        init();

        loginflag=preferences.getBoolean(YogaUtil.loginflag,false);

        if(loginflag)
        {
            preferenceLName = preferences.getString(YogaUtil.loginName,"");
            preferenceLPassword= preferences.getString(YogaUtil.loginPassword,"");
//
//            Toast.makeText(ViewUserDetails.this, "Login Name :"+preferenceLName +" Login Password " +preferenceLPassword, Toast.LENGTH_LONG).show();



            getDataFromCloud();

        }

        // for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void getDataFromCloud()
    {

        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        request = new StringRequest(Request.Method.POST, YogaUtil.RETRIEVE_DATA_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
//                 Toast.makeText(ViewUserDetails.this, "response" + response, Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                 // Toast.makeText(ViewUserDetails.this, "in try 2" + String.valueOf(jsonObject), Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                  // Toast.makeText(ViewUserDetails.this, "in try 3" + String.valueOf(jsonArray), Toast.LENGTH_LONG).show();

                    rcvbean = new signUpBean();
                    String temp="";
                    JSONObject jObj = jsonArray.getJSONObject(0);
//                    Toast.makeText(ViewUserDetails.this,"JOBJ "+jObj.toString(),Toast.LENGTH_LONG).show();
                    rcvbean.setName(jObj.getString(YogaUtil.COLNAME));
//                     Toast.makeText(ViewUserDetails.this,""+temp,Toast.LENGTH_LONG).show();
                    rcvbean.setPhoneNo(jObj.getString(YogaUtil.COLPHONENO));
//                    Toast.makeText(ViewUserDetails.this,""+temp,Toast.LENGTH_LONG).show();
                    rcvbean.setEmailId(jObj.getString(YogaUtil.COLEMAIL));
//                     Toast.makeText(ViewUserDetails.this,""+temp,Toast.LENGTH_LONG).show();
                    rcvbean.setULoginId(jObj.getString(YogaUtil.COLLOGINID));
//                    Toast.makeText(ViewUserDetails.this,""+temp,Toast.LENGTH_LONG).show();
                    rcvbean.setPassword(jObj.getString(YogaUtil.COLPASSWORD));
//                    Toast.makeText(ViewUserDetails.this,""+temp,Toast.LENGTH_LONG).show();
                    setDataToTextView();

                 //   Toast.makeText(ViewUserDetails.this, "data" + rcvbean.toString(), Toast.LENGTH_LONG).show();

                    dialog.dismiss();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(ViewUserDetails.this, "Some Exception" + e, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ViewUserDetails.this, "ON ERRoR Response " + error, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> map = new HashMap<>();

                map.put("LOGINID", preferenceLName);
                map.put("PASSWORD", preferenceLPassword);

                return map;

            }
        };

        queue.add(request);
      //  dialog.dismiss();
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonView)
        {
            Intent i = new Intent(ViewUserDetails.this,Yoga_Register.class);
            i.putExtra("signUpBean",rcvbean);
            startActivity(i);
            finish();


        }

    }
}
