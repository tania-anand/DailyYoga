package gym.minorproject.com.gym.adapter;

import android.content.Context;;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.ExerciseNameBean;

public class YogaAdapter extends ArrayAdapter {
    private  Context ct;
    private  int res;
    private ArrayList<ExerciseNameBean> exerciseList,tempList;
    Picasso picasso;
    CardView cardView;

    public YogaAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        ct = context;
        res = resource;
        exerciseList = objects;
        tempList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem;
        listItem = LayoutInflater.from(ct).inflate(res,parent,false);


        TextView exercise_n = (TextView)listItem.findViewById(R.id.exercise_name2);
        ImageView exerciseImage = (ImageView)listItem.findViewById(R.id.exercise_image2);
        cardView = (CardView)listItem.findViewById(R.id.card_layout);
        if(position%2==0)
        {cardView.setBackgroundColor(Color.parseColor("#b56d9cf4"));}
        else
        {cardView.setBackgroundColor(Color.parseColor("#b5b7d2ff"));}


        ExerciseNameBean tb = exerciseList.get(position);
        exercise_n.setText(tb.getExercise_name());
         String imageUrl = tb.getExercise_image();
        picasso.with(ct)
                .load(imageUrl)
                .into(exerciseImage);

        return listItem;
    }
}
