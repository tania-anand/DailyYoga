package gym.minorproject.com.gym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AskRegister extends AppCompatActivity implements View.OnClickListener
{
    @InjectView(R.id.btnRegisterNow)
    Button btnRegisterNow;

    @InjectView(R.id.btnRegisterLater)
    Button btnRegisterLater;

    public void init()
    {
        btnRegisterLater.setOnClickListener(this);
        btnRegisterNow.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_register);

        ButterKnife.inject(this);

        init();


    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        Intent i;

        switch(id)
        {
            case R.id.btnRegisterLater:
                i = new Intent(AskRegister.this,MainYoga.class);
                startActivity(i);
                finish();

                break;

            case R.id.btnRegisterNow:
                 i = new Intent(AskRegister.this,Yoga_Login.class);
                 startActivity(i);
                 finish();
                break;

        }

    }
}
