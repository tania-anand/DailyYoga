package gym.minorproject.com.gym;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Yoga_Register extends AppCompatActivity implements View.OnClickListener
{

    @InjectView(R.id.RName)
    EditText edName;

    @InjectView(R.id.REmailId)
    EditText edEmail;

    @InjectView(R.id.RPhoneNo)
    EditText edPhoneNo;

    @InjectView(R.id.RUserName)
    EditText edLogin;

    @InjectView(R.id.RPassword)
    EditText edPassword;

    @InjectView(R.id.RCPassword)
    EditText edCPassword;

    @InjectView(R.id.Rbtn)
    Button btnRegister;

    boolean updateMode;
    signUpBean rcvbean;

    ProgressDialog progressDialog;

    String URL;



    private void init()
    {
        btnRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
    }

    void setDataInEditText()
    {
        edName.setText(rcvbean.getName());
        edEmail.setText(rcvbean.getEmailId());
        edPassword.setText(rcvbean.getPassword());
        edCPassword.setText(rcvbean.getPassword());
        edPhoneNo.setText(rcvbean.getPhoneNo());
        edLogin.setText(rcvbean.getULoginId());
        edLogin.setEnabled(false);
    }


    void clearFields()
    {
        edName .setText("");
        edLogin .setText("");
        edPhoneNo .setText("");
        edPassword .setText("");
        edCPassword .setText("");
        edEmail .setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__register);

        ButterKnife.inject(this);

        init();

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("signUpBean");

        if(updateMode)
        {
         rcvbean = (signUpBean)rcv.getSerializableExtra("signUpBean");
         setDataInEditText();
        }

    }

    void insertIntoCloud()
    {
        progressDialog.show();
        if(updateMode)
            URL=YogaUtil.UPDATE_DATA_PHP;
        else
            URL=YogaUtil.INSERT_DATA_PHP;

        // Instantiate the RequestQueue.
//        Toast.makeText(this,"update mode value "+updateMode,Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                String message;

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    message = jsonObject.getString("message");

                    Toast.makeText(Yoga_Register.this, message, Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {

                }

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Toast.makeText(Yoga_Register.this,"That didn't work!"+error.getMessage(),Toast.LENGTH_LONG).show();
                        clearFields();

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String ,String> map = new HashMap<>();

                map.put(YogaUtil.COLNAME,edName.getText().toString().trim());
                map.put(YogaUtil.COLPHONENO,edPhoneNo.getText().toString().trim());
                map.put(YogaUtil.COLEMAIL,edEmail.getText().toString().trim());
                map.put(YogaUtil.COLLOGINID,edLogin.getText().toString().trim());
                map.put(YogaUtil.COLPASSWORD,edPassword.getText().toString().trim());
                map.put("TOKEN", FirebaseInstanceId.getInstance().getToken());


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

    boolean validations()
    {
        boolean flag=true;

        if(edName.getText().toString().isEmpty())
        {
            edName.setError("Name cant be Empty");
            flag=false;
        }
        if(edPhoneNo.getText().toString().isEmpty())
        {
            edPhoneNo.setError("PhoneNo cant be Empty");
            flag=false;
        }
        else
        {
            if(edPhoneNo.getText().toString().length()!=10)
            {
                edPhoneNo.setError("Length of Phone Number should be 10");
                flag=false;
            }
        }
        if(edEmail.getText().toString().isEmpty())
        {
            edEmail.setError("EmailId cant be Empty");
            flag=false;
        }
        else
        {
            if(!(edEmail.getText().toString().contains("@") && edEmail.getText().toString().contains("."))){

                flag = false;

                edEmail.setError("Please Enter correct Email");

            }
        }

        if(edPassword.getText().toString().isEmpty())
        {
            edPassword.setError("Password cant be Empty");
            flag=false;
        }
        else
        {
            if(edPassword.getText().toString().length()<8)
            {
                edPassword.setError("Minimum Length of Password should be 8");
                flag = false;
            }

        }

        if(edCPassword.getText().toString().isEmpty())
        {
            edCPassword.setError("Confirm Password  cant be Empty");
            flag=false;
        }
        else
        {
            if(!edCPassword.getText().toString().equals(edPassword.getText().toString()))
            {
                edCPassword.setError("Confirm Password and Password Fields are Not Same");
                flag = false;
            }
        }

        if(edLogin.getText().toString().isEmpty())
        {
            edLogin.setError("User Name cant be Empty");
            flag=false;
        }

        return flag;

    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what==100)
            {
                insertIntoCloud();
            }
        }
    };



    @Override
    public void onClick(View v)
    {
        int i = v.getId();

        if(i==R.id.Rbtn)
        {
            if(validations())
            {

                handler.sendEmptyMessage(100);
                if(updateMode)
                {
                    Intent intent = new Intent(this, MainYoga.class);
                    startActivity(intent);

                }
                else
                    {
                    Intent intent = new Intent(this, Yoga_Login.class);
                    startActivity(intent);
                }

                finish();
            }
        }


    }
}
