package gym.minorproject.com.gym.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.TextView;

import gym.minorproject.com.gym.fragment.BenefitFragment;
import gym.minorproject.com.gym.fragment.ContraindicationFragment;
import gym.minorproject.com.gym.fragment.ExerciseDescriptionFragment;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;
import gym.minorproject.com.gym.fragment.MoreFragment;
import gym.minorproject.com.gym.R;

public class ExerciseDetails extends AppCompatActivity {
    TextView txtsteps;

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

    ExerciseYogaBean exerciseYogaBean;

    ListView listView;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    BenefitFragment benefit_fragment;
    ExerciseDescriptionFragment description_fragment;
    ContraindicationFragment contraindication_fragment;
    MoreFragment more_fragment;

    void initVARIABLES() {
        Steps_Images = exerciseYogaBean.getSteps_Images();
        Contraindications=exerciseYogaBean.getContraindications();
        Video_Url = exerciseYogaBean.getVideo_Url();
        benefit = exerciseYogaBean.getBenefits();
        Steps=exerciseYogaBean.getSteps();
        Name_E=exerciseYogaBean.getName_E();
        Name_H=exerciseYogaBean.getName_H();
        Muscles = exerciseYogaBean.getMuscles();
    }

    void initFragment() {
       benefit_fragment = new BenefitFragment();
       description_fragment = new ExerciseDescriptionFragment();
       contraindication_fragment =  new ContraindicationFragment();
       more_fragment = new MoreFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise__details);

        initFragment();
        rcv = getIntent();
        exerciseYogaBean = (ExerciseYogaBean) rcv.getSerializableExtra("yoga_bean");

        initVARIABLES();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // for tabbed tittle
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(Name_E);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.video:
                Intent i = new Intent(ExerciseDetails.this, VideoActivity.class);
                i.putExtra("video_url",Video_Url);
                startActivity(i);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle;
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    // here need to pass images of steps and steps
                    bundle = new Bundle();
                    bundle.putStringArray("images_steps_f",Steps_Images);
                    bundle.putStringArray("steps_exercise_f",Steps);
                    description_fragment.setArguments(bundle);

                    return description_fragment;
                case 1:
                    // here need to pass benefits of exercise
                    bundle = new Bundle();
                    bundle.putStringArray("benefits_f",benefit);
                    benefit_fragment.setArguments(bundle);
                    return benefit_fragment;
                case 2:
                    // here need to pass contrindications of exercise
                    bundle = new Bundle();
                    bundle.putStringArray("contraindications_f",Contraindications);
                    contraindication_fragment.setArguments(bundle);
                     return  contraindication_fragment;
                case 3:
                    // here need to pass hindi name and target area of exercises
                    bundle = new Bundle();
                    bundle.putString("hindiN_f",Name_H);
                    bundle.putStringArray("muscles_f",Muscles);
                    more_fragment.setArguments(bundle);

                    return  more_fragment;
            }
            return  null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Exercise Description";
                case 1:
                    return "Benefits";
                case 2:
                    return "Contraindication";
                case 3:
                    return "More";
            }
            return null;
        }
    }
}
