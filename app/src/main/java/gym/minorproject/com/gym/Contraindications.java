package gym.minorproject.com.gym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Contraindications extends AppCompatActivity
{
    String[] Contra;
    TextView txtcontra;

    void init()
    {
        txtcontra = (TextView)findViewById(R.id.contra);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contraindications);
        init();
        Intent rcv = getIntent();
        Contra = rcv.getStringArrayExtra("contra");

        set_StepsContra();
    }

    void set_StepsContra()
    {
        txtcontra.setText("");
        for(int i=0;i<Contra.length;i++)
        {
            txtcontra.append(Contra[i]+"\n");
        }
    }
}
