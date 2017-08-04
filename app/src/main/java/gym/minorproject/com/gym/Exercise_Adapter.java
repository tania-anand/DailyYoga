package gym.minorproject.com.gym;

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

/**
 * Created by HP PAVILLION on 18-03-2017.
 */

public class Exercise_Adapter extends ArrayAdapter
{
    RatingBar ratingBar;
    private Context ct;
    private  int res;
    private ArrayList<exercise_nameBean> exerciseList,tempList;
    Picasso picasso;

    public Exercise_Adapter(Context context, int resource, ArrayList objects)
    {
        super(context, resource, objects);
        ct = context;
        res = resource;
        exerciseList = objects;
        tempList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItem;
        listItem = LayoutInflater.from(ct).inflate(res,parent,false);

        TextView exercise_n = (TextView)listItem.findViewById(R.id.exercise_name2);
        ImageView exerciseImage = (ImageView)listItem.findViewById(R.id.exercise_image2);



        exercise_nameBean tb = exerciseList.get(position);
        exercise_n.setText(tb.getExercise_name());
        String imageUrl = tb.getExercise_image();
        picasso.with(ct)
                .load(imageUrl)
                .into(exerciseImage);



        return listItem;
    }
}
