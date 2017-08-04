package gym.minorproject.com.gym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rate_US extends AppCompatActivity implements View.OnClickListener,RatingBar.OnRatingBarChangeListener{


    RatingBar ratingBar;
    Button ok;

    SharedPreferences preferences;


    boolean flag;
    float rating1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate__us);
        init();
//        Toast.makeText(this,"WE ARE IN  CREATE OF RATING BAR",Toast.LENGTH_LONG).show();
        preferences = getSharedPreferences("GymApp",MODE_PRIVATE);
        flag=preferences.getBoolean("flag",false);
        if(flag)
        {
            float rating2=  preferences.getFloat("rating",0);
            ratingBar.setRating(rating2);
        }


    }
    void init()
    {
        ratingBar = (RatingBar)findViewById(R.id.ratingBarstar);
        ratingBar.setOnRatingBarChangeListener(this);
        ok=(Button)findViewById(R.id.rate_ok);
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        ok.setOnClickListener(this);

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
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
    {
        SharedPreferences.Editor editor = preferences.edit();
        rating1=rating;
        flag=true;
        editor.putBoolean("flag",flag);
//        Toast.makeText(this,"value of flag in rating changes "+flag,Toast.LENGTH_LONG).show();
        editor.putFloat("rating",rating1);
        editor.apply();

        Toast.makeText(this, "Rating: " + rating1, Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onStop()
    {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v)
    {
        Intent i = new Intent(this,MainYoga.class);
        Toast.makeText(this,"Thank You For Rating Us ",Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }
}

