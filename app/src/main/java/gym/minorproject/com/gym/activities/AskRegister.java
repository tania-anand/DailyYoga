package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gym.minorproject.com.gym.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AskRegister extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnRegisterNow)
    Button btnRegisterNow;

    @BindView(R.id.btnRegisterLater)
    Button btnRegisterLater;

    public void init() {
        btnRegisterLater.setOnClickListener(this);
        btnRegisterNow.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_register);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.btnRegisterLater:
                i = new Intent(AskRegister.this, MainYoga.class);
                startActivity(i);
                finish();

                break;

            case R.id.btnRegisterNow:
                 i = new Intent(AskRegister.this, Login.class);
                 startActivity(i);
                 finish();
                break;

        }
    }
}
