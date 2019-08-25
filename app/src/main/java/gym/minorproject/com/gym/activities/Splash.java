package gym.minorproject.com.gym.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;

public class Splash extends AppCompatActivity {
    ImageView img;
    TextView txtheading;
    private static int SPLASH_TIME_OUT = 10000;

    SharedPreferences preferences;
    boolean loginflag=false;

    void initViews() {
        img = (ImageView) findViewById(R.id.image_splash);
        txtheading=(TextView)findViewById(R.id.heading);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.yoga_animation);
        txtheading.startAnimation(animation);

        AnimationDrawable animationDrawable=(AnimationDrawable)img.getBackground();
        animationDrawable.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yoga__splash);
        initViews();
        loginflag = SharedPreferencesUtil.getInstance(getApplicationContext()).getLoggedIn();
        handler.sendEmptyMessageDelayed(101,4000);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 101) {
                Intent i ;
                if(loginflag) {
                    i = new Intent(Splash.this, MainYoga.class);
                    startActivity(i);
                    finish();
                }
                else {
                    i = new Intent(Splash.this, AskRegister.class);
                    startActivity(i);
                    finish();
                } }
        }
    };


//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        Intent i = new Intent(this,MainYoga.class);
//        startActivity(i);
//    }
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//        handler.sendEmptyMessageDelayed(101, 6000);
//    }
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        handler.sendEmptyMessageDelayed(101, 6000);
//
//    }

}
