package gym.minorproject.com.gym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

public class Exercise_Description extends AppCompatActivity
{
    TextView txtsteps;
    Toolbar toolbar;

    String Name_E;
    String Name_H;
    String[] Steps;
    String[] benefit;
    String[] Contraindications;
    String[] Muscles;
    String Video_Url;
    Intent rcv;
    // String Images;
    String[] Steps_Images;


    Exercise_Yoga_Bean exerciseYogaBean;

    ArrayList<String> stringArrayListM;
    ArrayList<String> stringArrayListC;

    private SliderLayout mDemoSlider;





    public void setImageSlider(String[] images)
    {
        HashMap<Integer,String> file_maps = new HashMap<Integer, String>();


        for(int i=0;i<images.length;i++)
        {
            file_maps.put(i,images[i]);
        }

        for(Integer name : file_maps.keySet())
        {
            TextSliderView textSliderView = new TextSliderView(this);
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
    void set_Steps()
    {
        txtsteps.setText("");
        for(int i=0;i<Steps.length;i++)
        {
            txtsteps.append(Steps[i]+"\n");
        }
    }



    void init()
    {

        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider1);



        stringArrayListM= new ArrayList<>();
        stringArrayListC= new ArrayList<>();

        txtsteps =(TextView) findViewById(R.id.txtSteps1);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider1);



    }

    void initVARIABLES()
    {
        Steps_Images = exerciseYogaBean.getSteps_Images();
        Contraindications=exerciseYogaBean.getContraindications();
        Video_Url = exerciseYogaBean.getVideo_Url();
        benefit = exerciseYogaBean.getBenefits();
        Steps=exerciseYogaBean.getSteps();
        Name_E=exerciseYogaBean.getName_E();
        Name_H=exerciseYogaBean.getName_H();
        Muscles = exerciseYogaBean.getMuscles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_top_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
            case R.id.video:
                Intent i = new Intent(Exercise_Description.this,VideoActivity.class);
                i.putExtra("video_url",Video_Url);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        Intent i;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.benefits:
                    i = new Intent(Exercise_Description.this, Benefits.class);
                    i.putExtra("benefit",benefit);
                    startActivity(i);
                    break;
                case R.id.More1:
                     i = new Intent(Exercise_Description.this,More.class);
                     i.putExtra("hindiN",Name_H);
                     i.putExtra("muscles",Muscles);
                     startActivity(i);
                     break;
                case R.id.Contra:
                    i = new Intent(Exercise_Description.this,Contraindications.class);
                    i.putExtra("contra",Contraindications);
                    startActivity(i);
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_layout);

        //for getting data from differnet exercise types
        rcv = getIntent();
        exerciseYogaBean = (Exercise_Yoga_Bean) rcv.getSerializableExtra("yoga_bean");

        initVARIABLES();
        init();
        set_Steps();

        setImageSlider(Steps_Images);

    }





}
