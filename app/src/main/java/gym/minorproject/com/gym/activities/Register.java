package gym.minorproject.com.gym.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.bean.SignUpBean;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.RName)
    EditText edName;

    @BindView(R.id.REmailId)
    EditText edEmail;

    @BindView(R.id.RPassword)
    EditText edPassword;

    @BindView(R.id.Rbtn)
    Button btnRegister;

    boolean updateMode;
    SignUpBean rcvbean;

    ProgressDialog progressDialog;

    String URL;

    private void init() {
        btnRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
    }

    void setDataInEditText() {
        edName.setText(rcvbean.getName());
        edEmail.setText(rcvbean.getEmailId());
        edPassword.setText(rcvbean.getPassword());
    }


    void clearFields() {
        edName .setText(null);
        edPassword .setText(null);
        edEmail .setText(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__register);
        ButterKnife.bind(this);

        init();

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("SignUpBean");

        if(updateMode) {
         rcvbean = (SignUpBean)rcv.getSerializableExtra("SignUpBean");
         setDataInEditText();
        }

    }

    void insertIntoCloud() {
        progressDialog.show();
        if(updateMode)
            URL= YogaUtil.UPDATE_DATA_PHP;
        else
            URL=YogaUtil.INSERT_DATA_PHP;

        // Instantiate the RequestQueue.
//        Toast.makeText(this,"update mode value "+updateMode,Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message;

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    message = jsonObject.getString("message");

                    Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {

                }

            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Register.this,"That didn't work!"+error.getMessage(),Toast.LENGTH_LONG).show();
                    clearFields();

                }
            }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();

                map.put(YogaUtil.COLNAME,edName.getText().toString().trim());
                map.put(YogaUtil.COLEMAIL,edEmail.getText().toString().trim());
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

    boolean validations() {
        boolean flag=true;

        if(edName.getText().toString().isEmpty()) {
            edName.setError("Name cant be Empty");
            flag=false;
        }
        if(edEmail.getText().toString().isEmpty()) {

            edEmail.setError("EmailId cant be Empty");
            flag=false;
        }
        else {
            if(!(edEmail.getText().toString().contains("@") && edEmail.getText().toString().contains("."))){
                flag = false;
                edEmail.setError("Please Enter correct Email");
                }
        }

        if(edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Password cant be Empty");
            flag=false;
        }
        else {
            if(edPassword.getText().toString().length()<8) {
                edPassword.setError("Minimum Length of Password should be 8");
                flag = false;
            }

        }

        return flag;

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what==100) {
                insertIntoCloud();
            }
        }
    };



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.Rbtn) {
            if(validations()) {
                handler.sendEmptyMessage(100);
                if(updateMode) {
                    Intent intent = new Intent(this, MainYoga.class);
                    startActivity(intent);
                }
                else
                    {
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }
    }
}
