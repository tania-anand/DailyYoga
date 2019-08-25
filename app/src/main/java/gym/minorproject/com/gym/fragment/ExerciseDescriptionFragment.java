package gym.minorproject.com.gym.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

import gym.minorproject.com.gym.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDescriptionFragment extends Fragment {
    private static final String TAG = "ExerciseDescriptionFrag";
    TextView txtsteps;
    String[] Steps;
    String[] Step_Images;


    private SliderLayout mDemoSlider;

    View view;

    void init() {
        txtsteps =view.findViewById(R.id.txtSteps1);
        mDemoSlider = view.findViewById(R.id.slider1);
    }


    public ExerciseDescriptionFragment() {
        // Required empty public constructor
    }

    public void setImageSlider(String[] images) {
        HashMap<Integer,String> file_maps = new HashMap<Integer, String>();


        for(int i=0;i<images.length;i++) {
            file_maps.put(i,images[i]);
            Log.i(TAG, "setImageSlider: "+images[i]);
        }

        for(Integer name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    //.description(name)
                    // .getPicasso(file_maps.get(name))
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)

            ;
            //.setOnSliderClickListener(this);
            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
//        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider.setDuration(4000);
    }

    void set_Steps() {
        txtsteps.setText("");
        for(int i=0;i<Steps.length;i++) {
            txtsteps.append(Steps[i]+"\n");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.activity_exercise__description, container, false);
        Steps = getArguments().getStringArray("steps_exercise_f");
        Step_Images = getArguments().getStringArray("images_steps_f");
        init();
        set_Steps();
        setImageSlider(Step_Images);

        return view;

    }

}
