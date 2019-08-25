package gym.minorproject.com.gym.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.activities.ExerciseList;
import gym.minorproject.com.gym.adapter.YogaAdapter;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.contract.MainContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.YogaUtil;

public class MainPresenter implements MainContract.persenter , AdapterView.OnItemClickListener {

    private Context context;
    private MainContract.viewer viewer;

    private ListView list;

    private ArrayList<ExerciseNameBean> yogaList;
    private YogaAdapter yogaAdapter;

    public MainPresenter(Context context,MainContract.viewer viewer){
        this.context = context;
        this.viewer = viewer;


        list = ((Activity) context).findViewById(R.id.list_view);
    }

    public void setAdapter(){
        yogaList = new DBHelper(context).getListOfAsanas();
        yogaAdapter = new YogaAdapter(context, R.layout.listview_list_item, yogaList);
        list.setAdapter(yogaAdapter);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExerciseNameBean bean = yogaList.get(position);
        Intent i;

        switch(bean.getExercise_name()) {
            case YogaUtil.SITTING:
                i = new Intent(context, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.SITTING);
                context.startActivity(i);
                break;
            case YogaUtil.STANDING:
                i = new Intent(context, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.STANDING);
                context.startActivity(i);
                break;
            case YogaUtil.STOMACH:
                i = new Intent(context, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.STOMACH);
                context.startActivity(i);
                break;
            case YogaUtil.BACK:
                i = new Intent(context, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.BACK);
                context.startActivity(i);
                break;
            case YogaUtil.BREATHING:
                i = new Intent(context, ExerciseList.class);
                i.putExtra("keyExerciseType",YogaUtil.BREATHING);
                context.startActivity(i);
                break;

        }

    }




}
