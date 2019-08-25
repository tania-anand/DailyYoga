package gym.minorproject.com.gym.presenter;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import gym.minorproject.com.gym.activities.ExerciseList;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;
import gym.minorproject.com.gym.contract.ExerciseListContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

public class ExerciseListPresenter implements ExerciseListContract.presenter {

    private Context context;
    private ExerciseListContract.viewer viewer;


    public  ExerciseListPresenter(Context context ,ExerciseListContract.viewer viewer){
        this.viewer = viewer;
        this.context = context;
    }

    private void insertFAVInCLoud(final ExerciseYogaBean yogaBean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(YogaUtil.COLEMAIL, SharedPreferencesUtil.getInstance(context).getUser().getEmailId());
            jsonObject.put(YogaUtil.KEY_ASAN_ID,String.valueOf(yogaBean.getId()));
            jsonObject.put(YogaUtil.EXERCISE_TYPE,yogaBean.getExercise_Type());
            jsonObject.put(YogaUtil.EXERCISE_NAME_E,yogaBean.getName_E());
            jsonObject.put(YogaUtil.EXERCISE_NAME_H,yogaBean.getName_H());
            jsonObject.put(YogaUtil.EXERCISE_STEPS,yogaBean.getStringSteps());
            jsonObject.put(YogaUtil.EXERCISE_IMAGE,yogaBean.getImages());
            jsonObject.put(YogaUtil.EXERCISE_BENEFITS,yogaBean.getStringBenefits());
            jsonObject.put(YogaUtil.EXERCISE_MUSCLES,yogaBean.getStringMuscles());
            jsonObject.put(YogaUtil.EXERCISE_STEP_IMAGES,yogaBean.getStringStepsImages());
            jsonObject.put(YogaUtil.EXERCISE_VIDEO,yogaBean.getVideo_Url());
            jsonObject.put(YogaUtil.EXERCISE_CONTRAINDICATIONS,yogaBean.getStringContraindications());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall.getMyDB().initRequestQueue(context);
        NetworkCall.getMyDB().processRequest(Request.Method.POST, YogaUtil.INSERT_FAV, jsonObject, new MyResponseListener() {
            @Override
            public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {
                String message = "Success";
                try {
                    message = jsonObject.getString("message");
                    viewer.onCallbackSuccess("insert",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    viewer.onCallbackFailure("insert",message);
                }
            }

            @Override
            public void onMyResponseFailure(String message) {
                viewer.onCallbackFailure("delete",message);

            }
        });
    }


    private void deleteFAVInCLoud(ExerciseYogaBean yogaBean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(YogaUtil.COLEMAIL, SharedPreferencesUtil.getInstance(context).getUser().getEmailId());
            jsonObject.put(YogaUtil.KEY_ASAN_ID,String.valueOf(yogaBean.getId()));
            jsonObject.put(YogaUtil.EXERCISE_TYPE,yogaBean.getExercise_Type());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall.getMyDB().initRequestQueue(context);
        NetworkCall.getMyDB().processRequest(Request.Method.POST,YogaUtil.DELETE_FAV, jsonObject, new MyResponseListener() {
            @Override
            public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {

                String message = "Success";
                try {
                    message = jsonObject.getString("message");
                    viewer.onCallbackSuccess("delete",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    viewer.onCallbackFailure("delete",message);
                }
            }
            @Override
            public void onMyResponseFailure(String message) {
                viewer.onCallbackFailure("delete",message);
            }
        });
    }


    public void addExerciseToFav(ExerciseNameBean eb) {
        eb.setFav_id(1);
        ExerciseYogaBean exerciseYogaBean = new DBHelper(context).getAsanaById(eb.getId());
        new DBHelper(context).updateFavintoDB(eb.getId(),1);
        insertFAVInCLoud(exerciseYogaBean);
    }

    public void deleteExerciseFromFav(ExerciseNameBean eb ) {
        eb.setFav_id(0);
        ExerciseYogaBean exerciseYogaBean = new DBHelper(context).getAsanaById(eb.getId());
        new DBHelper(context).updateFavintoDB(eb.getId(),0);
        deleteFAVInCLoud(exerciseYogaBean);
    }





}
