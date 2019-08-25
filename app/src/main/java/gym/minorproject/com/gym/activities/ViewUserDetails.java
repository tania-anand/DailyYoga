package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.contract.ViewUserDetailsContract;
import gym.minorproject.com.gym.helper.UtilActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gym.minorproject.com.gym.interfaces.MyResponseConnectivity;
import gym.minorproject.com.gym.presenter.ViewUserDetailsPresenter;

public class ViewUserDetails extends UtilActivity implements View.OnClickListener, MyResponseConnectivity
        , ViewUserDetailsContract.viewer {
    @BindView(R.id.buttonView)
    Button button;

    private ViewUserDetailsPresenter presenter;


    private void init() {
        button.setOnClickListener(this);
        initMyResponseConnectivityListener(this);
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
        ButterKnife.bind(this);
        init();

        // for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ViewUserDetailsPresenter(this,this);
        getUserDetails();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonView) {
            Intent i = new Intent(ViewUserDetails.this, Register.class);
            i.putExtra("SignUpBean",presenter.getSignUpBean());
            startActivity(i);
            finish();
        }
    }

    private void getUserDetails(){
        if(isNetworkConnected(this)){
            showDialog();
            presenter.getUserDetails();
        }else
            showMsg(this);
    }

    @Override
    public void onMyResponseConnectivity(int i) {
        dismissDialog();
        if(i==1){
            if (isNetworkConnected(this)){
                showDialog();
                presenter.getUserDetails();
            }else
                showMsg(this);

        }else{
            showMsg(this);
        }

    }

    @Override
    public void onResponseSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        dismissDialog();
    }

    @Override
    public void onResponseFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        dismissDialog();

    }
}
