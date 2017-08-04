package gym.minorproject.com.gym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Benefits extends AppCompatActivity
{
    TextView textView;
    String[] Benefit;


    void init()
    {
        textView = (TextView)findViewById(R.id.benefitSteps);

    }

    void setBenefitText()
    {
        textView.setText("");
        for(String benefit:Benefit)
        { textView.append("\n"+benefit);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefits);

        Intent rcv = getIntent();
        Benefit = rcv.getStringArrayExtra("benefit");
        init();
        setBenefitText();
    }
}
