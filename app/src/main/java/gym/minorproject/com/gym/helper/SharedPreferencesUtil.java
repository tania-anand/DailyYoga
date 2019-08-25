package gym.minorproject.com.gym.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import gym.minorproject.com.gym.bean.SignUpBean;

public class SharedPreferencesUtil {
    private static final String SPFileName = "DailyYoga";

    private static SharedPreferencesUtil instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtil();
            String SHARE_USER_INFO = SPFileName;
            preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.apply();
        }
        return instance;
    }

    private final String USER_PREF = "user_bean";
    private final String LOGIN_FLAG = "login_flag";
    private final String RATING = "rating";



    public void saveUser(SignUpBean user){
       editor.putString(USER_PREF, new Gson().toJson(user));
       editor.commit();
    }

    public SignUpBean getUser(){
        String userString = preferences.getString(USER_PREF,null);
        return new Gson().fromJson(userString,  new TypeToken<SignUpBean>() {
        }.getType());
    }

    public void setLoggedIn(boolean flag){
        editor.putBoolean(LOGIN_FLAG,flag);
        editor.commit();

    }

    public boolean getLoggedIn(){
        return preferences.getBoolean(LOGIN_FLAG,false);
    }


    public void setRating(float rating){
        editor.putFloat(RATING,rating);
        editor.apply();
    }

    public float getRating(){
        return preferences.getFloat(RATING,0.0f);
    }

}
