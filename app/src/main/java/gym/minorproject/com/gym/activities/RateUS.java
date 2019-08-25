package gym.minorproject.com.gym.activities;

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

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;

public class RateUS extends AppCompatActivity implements View.OnClickListener,RatingBar.OnRatingBarChangeListener{
    private RatingBar ratingBar;
    private Button ok;

    float rating1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate__us);
        init();
        rating1 = SharedPreferencesUtil.getInstance(getApplicationContext()).getRating();
        ratingBar.setRating(rating1);

    }

    void init() {
        ratingBar = findViewById(R.id.ratingBarstar);
        ratingBar.setOnRatingBarChangeListener(this);
        ok=findViewById(R.id.rate_ok);
        ok.setOnClickListener(this);
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
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        rating1=rating;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        SharedPreferencesUtil.getInstance(getApplicationContext()).setRating(rating1);
        Intent i = new Intent(this, MainYoga.class);
        Toast.makeText(this,"Thank You For Rating Us ",Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }

}

