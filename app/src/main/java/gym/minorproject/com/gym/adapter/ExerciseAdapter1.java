package gym.minorproject.com.gym.adapter;

/**
 * Created by HP PAVILLION on 18-03-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.ExerciseNameBean;


public class ExerciseAdapter1 extends ArrayAdapter {
    RatingBar ratingBar;
    private Context ct;
    private  int res;
    private ArrayList<ExerciseNameBean> exerciseList,tempList;
   // private HashMap<Integer,ExerciseNameBean> exerciseList,tempList;
    Picasso picasso;

    public ExerciseAdapter1(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        ct = context;
        res = resource;
        exerciseList = objects;
       tempList = new ArrayList<>();
//        tempList = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem;
        listItem = LayoutInflater.from(ct).inflate(res,parent,false);

        TextView exercise_n = (TextView)listItem.findViewById(R.id.exercise_name1);
        ImageView exerciseImage = (ImageView)listItem.findViewById(R.id.exercise_image1);
        ratingBar =(RatingBar)listItem.findViewById(R.id.fav);


        ExerciseNameBean tb = exerciseList.get(position);
        exercise_n.setText(tb.getExercise_name());
        String imageUrl = tb.getExercise_image();
        picasso.with(ct)
                .load(imageUrl)
                .into(exerciseImage);

        if(tb.getFav_id()==0) {
            ratingBar.setVisibility(View.INVISIBLE);
            //ratingBar.setStepSize(0);
        }
        if(tb.getFav_id()==1) {
            ratingBar.setRating(1f);
        }
        return listItem;
    }


}
