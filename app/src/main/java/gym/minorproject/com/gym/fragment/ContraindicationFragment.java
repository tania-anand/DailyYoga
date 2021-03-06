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
public class ContraindicationFragment extends Fragment {
    String[] Contra;
    TextView txtcontra;

    void init()
    {
        txtcontra = view.findViewById(R.id.contra);
    }


    public ContraindicationFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_contraindications, container, false);
        init();

        Contra = getArguments().getStringArray("contraindications_f");

        set_StepsContra();
        return view;
    }

    void set_StepsContra() {
        txtcontra.setText("");
        for(int i=0;i<Contra.length;i++) {
            txtcontra.append(Contra[i]+"\n");
        }
    }

}
