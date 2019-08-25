package gym.minorproject.com.gym.helper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.activities.ExerciseList;
import gym.minorproject.com.gym.adapter.YogaAdapter;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;

public class DBHelper {
    private static final String TAG = "DBHelper";

    private Context context;

    public DBHelper(Context context){
        this.context = context;
    }



    public void updateFavintoDB(ArrayList<Integer> arrayList) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(YogaUtil.FAV, 1);

        for(int j=0;j<arrayList.size();j++ ) {
            int asan_id = arrayList.get(j);
            String where = YogaUtil.EXERCISE_ID + " = " + asan_id;
            int i = resolver.update(YogaUtil.uri_exercise_tab, values, where, null);
            if (i > 0) {
                Log.d(TAG, "updateFavintoDB: Favourite Updated: ");
            }
        }
    }


    public ArrayList<ExerciseNameBean> getListOfAsanas() {
        ArrayList<ExerciseNameBean> yogaList = new ArrayList <>();
        ContentResolver resolver = context.getContentResolver();
        String[] projection = {YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE};
        Cursor cursor = resolver.query(YogaUtil.uri_yoga_type,projection,null,null,null);

        String Exercise_Name="";
        String Exercise_Image="";

        while(cursor.moveToNext()) {
            Exercise_Name=cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            Exercise_Image=cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            yogaList.add(new ExerciseNameBean(Exercise_Name,Exercise_Image));
        }
        cursor.close();
        return yogaList;

    }

    public void deleteDataFromPhone() {
        ContentValues values = new ContentValues();
        values.put(YogaUtil.FAV,0);
        ContentResolver resolver = context.getContentResolver();
        resolver.update(YogaUtil.uri_exercise_tab,values,null,null);
        Toast.makeText(context,"You Successfully logged out",Toast.LENGTH_LONG).show();

    }

    public ArrayList<ExerciseNameBean> getAsanasExcerciseType(String exerciseType){
        ArrayList<ExerciseNameBean> yogaList = new ArrayList <>();
        ContentResolver resolver = context.getContentResolver();

        String[] projection = {YogaUtil.EXERCISE_TYPE, YogaUtil.EXERCISE_ID, YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE, YogaUtil.FAV};
        String where = YogaUtil.EXERCISE_TYPE + " like '" + exerciseType + "'";
        Cursor cursor = resolver.query(YogaUtil.uri_exercise_tab, projection, where, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            String Name_E = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            String image = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            int exercise_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.EXERCISE_ID));
            int fav_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.FAV));
            yogaList.add(new ExerciseNameBean(Name_E, image, fav_id, exercise_id));
        }
        cursor.close();

        return yogaList;
    }

    public ExerciseYogaBean getAsanaById(int id){

        ExerciseYogaBean bean = new ExerciseYogaBean();
        String[] projection = {
                YogaUtil.EXERCISE_ID, YogaUtil.EXERCISE_NAME_E, YogaUtil.EXERCISE_IMAGE, YogaUtil.EXERCISE_NAME_H,
                YogaUtil.EXERCISE_STEPS, YogaUtil.EXERCISE_STEP_IMAGES, YogaUtil.EXERCISE_CONTRAINDICATIONS,
                YogaUtil.EXERCISE_BENEFITS, YogaUtil.EXERCISE_VIDEO, YogaUtil.EXERCISE_MUSCLES, YogaUtil.EXERCISE_TYPE
        };

        ContentResolver resolver = context.getContentResolver();
        String where = YogaUtil.EXERCISE_ID + " = " + id;
        Cursor cursor = resolver.query(YogaUtil.uri_exercise_tab, projection, where, null, null);

        if (cursor != null) {
            cursor.moveToNext();
            int asan_id = cursor.getInt(cursor.getColumnIndex(YogaUtil.EXERCISE_ID));
            String exercise_type = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_TYPE));
            String Name_E = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_E));
            String image = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_IMAGE));
            String Name_H = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_NAME_H));
            String Steps = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_STEPS));
            String Steps_Images = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_STEP_IMAGES));
            String Contraindications = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_CONTRAINDICATIONS));
            String Benefits = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_BENEFITS));
            String Video_Url = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_VIDEO));
            String muscles = cursor.getString(cursor.getColumnIndex(YogaUtil.EXERCISE_MUSCLES));

            bean = new ExerciseYogaBean(Name_E, Name_H, Steps, Benefits,Contraindications, Video_Url,
                    image, Steps_Images, muscles, exercise_type, asan_id);
        }
        assert cursor != null;
        cursor.close();

        return bean;

    }

    public void updateFavintoDB(int id ,int favVal) {
        ContentValues values = new ContentValues();
        ContentResolver resolver = context.getContentResolver();
        values.put(YogaUtil.FAV, favVal);
        String where = YogaUtil.EXERCISE_ID + " = " + id;
        int i = resolver.update(YogaUtil.uri_exercise_tab, values, where, null);
        if (i > 0) {
                Log.d(TAG,"valaue updated");
        }
    }
}
