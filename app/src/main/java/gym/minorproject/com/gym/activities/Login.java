package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.contract.LoginContract;
import gym.minorproject.com.gym.helper.UtilActivity;
import gym.minorproject.com.gym.bean.SignUpBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import gym.minorproject.com.gym.interfaces.MyResponseConnectivity;
import gym.minorproject.com.gym.presenter.LoginPresenter;

public class Login extends UtilActivity implements View.OnClickListener, LoginContract.viewer, MyResponseConnectivity {

    @BindView(R.id.user_id)
    EditText eduserId;

    @BindView(R.id.password)
    EditText edpassword;

    @BindView(R.id.submit)
    Button btnlogin;

    @BindView(R.id.register_page)
    TextView txtRegisterPage;

    SignUpBean bean;

    LoginPresenter presenter;

    private void init() {
        presenter =new  LoginPresenter(this,this);
        initMyResponseConnectivityListener(this);
        btnlogin.setOnClickListener(this);
        txtRegisterPage.setOnClickListener(this);
    }

    private void insertDataIntoSignUpBean() {
        bean = new SignUpBean();
        bean.setPassword(edpassword.getText().toString());
        bean.setEmailId(eduserId.getText().toString());
    }

    private void clearFields() {
        edpassword.setText(null);
        eduserId.setText(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__login);

        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(isNetworkConnected(Login.this)) {
            switch (i) {
                case R.id.submit:
                    insertDataIntoSignUpBean();
                    showDialog();
                    presenter.onClickLogin(bean);

                    break;
                case R.id.register_page:
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
        else
            showMsg(Login.this);
    }

    @Override
    public void onMyResponseConnectivity(int i) {
        dismissDialog();
        if(i==1){
            if(isNetworkConnected(Login.this)){
                insertDataIntoSignUpBean();
                presenter.onClickLogin(bean);
            }else{
                showMsg(Login.this);
            }
        }else{
            showMsg(Login.this);
        }
    }

    @Override
    public void onLoginSuccess(String message) {
        dismissDialog();
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
        Intent i = new Intent(Login.this, MainYoga.class);
        startActivity(i);
    }

    @Override
    public void onLoginFailure(String message) {
        dismissDialog();
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
        dismissDialog();
        clearFields();
    }
}
