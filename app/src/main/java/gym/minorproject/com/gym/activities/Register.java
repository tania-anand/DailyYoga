package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.contract.RegisterContract;
import gym.minorproject.com.gym.helper.UtilActivity;
import gym.minorproject.com.gym.bean.SignUpBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import gym.minorproject.com.gym.interfaces.MyResponseConnectivity;
import gym.minorproject.com.gym.presenter.RegisterPresenter;

public class Register extends UtilActivity implements View.OnClickListener, MyResponseConnectivity, RegisterContract.viewer {

    @BindView(R.id.RName)
    EditText edName;

    @BindView(R.id.REmailId)
    EditText edEmail;

    @BindView(R.id.RPassword)
    EditText edPassword;

    @BindView(R.id.Rbtn)
    Button btnRegister;

    boolean updateMode;
    RegisterPresenter presenter;

    private void init() {
        btnRegister.setOnClickListener(this);
        initMyResponseConnectivityListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__register);
        ButterKnife.bind(this);
        init();
        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("SignUpBean");
        SignUpBean rcvbean = (SignUpBean)rcv.getSerializableExtra("SignUpBean");
        presenter = new RegisterPresenter(this,updateMode,this,rcvbean);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.Rbtn) {
            if (isNetworkConnected(this)){
                showDialog();
                presenter.onClick();
            }else
                showMsg(this);
        }
    }

    @Override
    public void onResponseSuccess(String message) {
        dismissDialog();
        if(updateMode) {
            Intent intent = new Intent(this, MainYoga.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onResponseFailure(String message) {
        dismissDialog();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMyResponseConnectivity(int i) {
        dismissDialog();
        if(i==1){
            if (isNetworkConnected(this)){
                showDialog();
                presenter.onClick();
            }else
                showMsg(this);

        }else{
            showMsg(this);
        }
    }
}
