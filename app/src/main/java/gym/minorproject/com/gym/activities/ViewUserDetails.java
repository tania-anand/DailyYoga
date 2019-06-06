package gym.minorproject.com.gym.activities;

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

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.SignUpBean;
import gym.minorproject.com.gym.helper.YogaUtil;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class ViewUserDetails extends AppCompatActivity implements View.OnClickListener
{
//    @BindView(R.id.VRName)
    TextView txtName;
//    @BindView(R.id.VREmailId)
    TextView txtEmail;
//    @BindView(R.id.VRUserName)
    TextView txtLogin;
//    @BindView(R.id.VRPhoneNo)
    TextView txtPhoneNo;
//    @BindView(R.id.buttonView)
    Button button;

    SignUpBean bean;
    SignUpBean rcvbean;


    SharedPreferences preferences;
    boolean loginflag=false;
    String preferenceLName="";
    String preferenceLPassword="";

    StringRequest request;
    ProgressDialog dialog;


    void init() {
        txtName = findViewById(R.id.VRName);
        txtEmail = findViewById(R.id.VREmailId);
        txtLogin = findViewById(R.id.VRUserName);
        txtPhoneNo = findViewById(R.id.VRPhoneNo);
        button = findViewById(R.id.buttonView);

        button.setOnClickListener(this);

        preferences = getSharedPreferences(YogaUtil.SPFileName,MODE_PRIVATE);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait .......");


    }

    void setDataToTextView() {
        txtName.setText(rcvbean.getName());
        txtEmail.setText(rcvbean.getEmailId());
        txtPhoneNo.setText(rcvbean.getPhoneNo());
        txtLogin.setText(rcvbean.getULoginId());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user_details);

//        ButterKnife.bind(this);

        init();

        loginflag=preferences.getBoolean(YogaUtil.loginflag,false);

        if(loginflag) {
            preferenceLName = preferences.getString(YogaUtil.loginName,"");
            preferenceLPassword= preferences.getString(YogaUtil.loginPassword,"");

            getDataFromCloud();
            }

        // for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void getDataFromCloud() {

        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        request = new StringRequest(Request.Method.POST, YogaUtil.RETRIEVE_DATA_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    rcvbean = new SignUpBean();
                    String temp="";
                    JSONObject jObj = jsonArray.getJSONObject(0);
                    rcvbean.setName(jObj.getString(YogaUtil.COLNAME));
                    rcvbean.setPhoneNo(jObj.getString(YogaUtil.COLPHONENO));
                    rcvbean.setEmailId(jObj.getString(YogaUtil.COLEMAIL));
                    rcvbean.setULoginId(jObj.getString(YogaUtil.COLLOGINID));
                    rcvbean.setPassword(jObj.getString(YogaUtil.COLPASSWORD));
                    setDataToTextView();

                    dialog.dismiss();

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ViewUserDetails.this, "Some Exception" + e, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

            }}
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserDetails.this, "ON ERRoR Response " + error, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("LOGINID", preferenceLName);
                map.put("PASSWORD", preferenceLPassword);

                return map;

            }
        };
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonView) {
            Intent i = new Intent(ViewUserDetails.this, Register.class);
            i.putExtra("SignUpBean",rcvbean);
            startActivity(i);
            finish();


        }

    }
}
