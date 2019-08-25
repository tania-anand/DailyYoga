package gym.minorproject.com.gym.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gym.minorproject.com.gym.activities.Favourite;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.bean.SignUpBean;

public class ResponseParser {
    private static final String TAG = "ResponseParser";


    public ArrayList<Integer>  parserFavouritesIdsResponse(JSONObject jsonObject){
        ArrayList<Integer> arrayList = new ArrayList <>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            int asan_id;
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    asan_id=jObj.getInt("ASAN_ID");
                    arrayList.add(asan_id);
                }
            }
        }
        catch (JSONException e) {
            Log.d(TAG, "onResponse: NO DATA FOR FAVOURITES ");
            e.printStackTrace();
        }

        return arrayList;
    }

    public SignUpBean parseUser(JSONObject object){
        SignUpBean signUpBean = new SignUpBean();

        try {
            JSONArray jsonArray = object.getJSONArray("data");
            JSONObject jObj = jsonArray.getJSONObject(0);

            signUpBean.setName(jObj.getString(YogaUtil.COLNAME));
            signUpBean.setEmailId(jObj.getString(YogaUtil.COLEMAIL));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return signUpBean;
    }

    public ArrayList<ExerciseNameBean> parserExercisesList(JSONObject object){

        ArrayList<ExerciseNameBean> list = new ArrayList <>();

        try {
            JSONArray jsonArray = object.getJSONArray("data");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jObj = jsonArray.getJSONObject(i);
                    ExerciseNameBean exerciseYogaBean = new ExerciseNameBean();
                    exerciseYogaBean.setId(jObj.getInt("ASAN_ID"));
                    exerciseYogaBean.setExercise_name(jObj.getString(YogaUtil.EXERCISE_NAME_E));
                    exerciseYogaBean.setExercise_image(jObj.getString(YogaUtil.EXERCISE_IMAGE));
                    list.add(exerciseYogaBean);
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
}
