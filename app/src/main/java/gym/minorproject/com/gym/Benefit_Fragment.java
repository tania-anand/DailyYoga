package gym.minorproject.com.gym;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Benefit_Fragment extends Fragment
{
    TextView textView;
    String[] Benefit;


    void init()
    {
        textView = (TextView)view.findViewById(R.id.benefitSteps);
    }

    void setBenefitText()
    {
        textView.setText("");
        for(String benefit:Benefit)
        { textView.append("\n"+benefit);}
    }


    public Benefit_Fragment()
    {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_benefits, container, false);
        Benefit = getArguments().getStringArray("benefits_f");
        init();
        setBenefitText();
        return view;
    }

}
