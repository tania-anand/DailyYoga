package gym.minorproject.com.gym.presenter;

import android.content.Context;

import com.android.volley.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;
import gym.minorproject.com.gym.contract.FavouriteContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.ResponseParser;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

public class FavouritePresenter {

    private Context context;
    private FavouriteContract.viewer viewer;
    private ArrayList<ExerciseNameBean> list;


    public FavouritePresenter(Context context,FavouriteContract.viewer viewer){
        this.context = context;
        this.viewer = viewer;
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
                    viewer.onSuccessCallback("delete",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    viewer.onFailureCallback("delete",message);
                }
            }
            @Override
            public void onMyResponseFailure(String message) {
                viewer.onFailureCallback("delete",message);
            }
        });
    }

    public void deleteExerciseFromFav(ExerciseNameBean eb ) {
        eb.setFav_id(0);
        ExerciseYogaBean exerciseYogaBean = new DBHelper(context).getAsanaById(eb.getId());
        new DBHelper(context).updateFavintoDB(eb.getId(),0);
        deleteFAVInCLoud(exerciseYogaBean);
    }


    public void retreiveAllFavs() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(YogaUtil.COLEMAIL, SharedPreferencesUtil.getInstance(context).getUser().getEmailId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        NetworkCall.getMyDB().initRequestQueue(context);
        NetworkCall.getMyDB().processRequest(Request.Method.POST, YogaUtil.RETRIEVE_ALL_FAV, jsonObject, new MyResponseListener() {
            @Override
            public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {

                String message = "Success";
                try {
                    list = new ResponseParser().parserExercisesList(jsonObject);
                    message = jsonObject.getString("message");
                    viewer.onSuccessCallback("retreive", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    viewer.onFailureCallback("retreive", message);
                }
            }

            @Override
            public void onMyResponseFailure(String message) {
                viewer.onFailureCallback("retreive", message);
            }
        });
    }

    public ArrayList <ExerciseNameBean> getList() {
        return list;
    }

}
