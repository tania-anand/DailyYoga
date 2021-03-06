package gym.minorproject.com.gym.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gym.minorproject.com.gym.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    TextView txtmuscles;
    TextView txthindi_N;

    String[]  muscles;
    String hindi_N;

    void init() {
        txtmuscles = view.findViewById(R.id.Muscles);
        txthindi_N = view.findViewById(R.id.HindiN);
    }

    public MoreFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.activity_more, container, false);

        init();

        muscles = getArguments().getStringArray("muscles_f");
        hindi_N = getArguments().getString("hindiN_f");

        set_StepsHindiN();
        set_StepsMuscles();

        return view;
    }

    void set_StepsMuscles() {
        txtmuscles.setText("");
        for(String muscles1:muscles) {
            txtmuscles.append(muscles1+"\n");
        }
    }
    void set_StepsHindiN()
    {
        txthindi_N.setText(hindi_N);
    }

}
