package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gym.minorproject.com.gym.R;


public class Feedback extends AppCompatActivity implements View.OnClickListener {
    EditText feedback;
    Button send;
    String message;

    void init() {
        feedback=(EditText)findViewById(R.id.feedback);
        send= (Button)findViewById(R.id.send);
        send.setOnClickListener(this);

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
        setContentView(R.layout.activity_feedback);
        init();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send) {
         message=feedback.getText().toString().trim();
            String [] to ={"tanyaanand.anand@gmail.com","shriyagupta1996@gmail.com"};
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setData(Uri.parse("mailto:"));
            email.setType("text/plain");
            email.putExtra(Intent.EXTRA_CC,to);
            email.putExtra(Intent.EXTRA_SUBJECT,"Feedback/Query From Gym App user");
            email.putExtra(Intent.EXTRA_TEXT,message);
            startActivity(Intent.createChooser(email, "Choose an Email client :"));

            finish();

        } }
}
