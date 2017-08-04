package gym.minorproject.com.gym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class More extends AppCompatActivity
{
    TextView txtmuscles;

    TextView txthindi_N;

    String[]  muscles;
    String hindi_N;

    void init()
    {
        txtmuscles = (TextView)findViewById(R.id.Muscles);

        txthindi_N = (TextView)findViewById(R.id.HindiN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        init();
        Intent rcv = getIntent();
        muscles = rcv.getStringArrayExtra("muscles");
        hindi_N = rcv.getStringExtra("hindiN");

        set_StepsHindiN();
        set_StepsMuscles();


    }

    void set_StepsMuscles()
    {
        txtmuscles.setText("");
        for(int i=0;i<muscles.length;i++)
        {
            txtmuscles.append(muscles[i]+"\n");
        }
    }
    void set_StepsHindiN()
    {
        txthindi_N.setText(hindi_N);
    }
}
