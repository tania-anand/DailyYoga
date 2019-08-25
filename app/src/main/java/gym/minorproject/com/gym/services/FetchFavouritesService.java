package gym.minorproject.com.gym.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gym.minorproject.com.gym.activities.Login;
import gym.minorproject.com.gym.activities.MainYoga;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.ResponseParser;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchFavouritesService extends IntentService {

    private static final String TAG = "FetchFavouritesService";

    public FetchFavouritesService() {
        super("FetchFavouritesService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            retrieve_all_fav();
        }
    }


    void retrieve_all_fav() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(YogaUtil.COLEMAIL, SharedPreferencesUtil.getInstance(getApplicationContext()).getUser().getEmailId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall.getMyDB().initRequestQueue(getApplicationContext());
        NetworkCall.getMyDB().processRequest(Request.Method.POST, YogaUtil.RETRIEVE_ALL_FAV, jsonObject, new MyResponseListener() {
            @Override
            public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {
                ArrayList<Integer> arrayList =  new ResponseParser().parserFavouritesIdsResponse(jsonObject);
                new DBHelper(getApplicationContext()).updateFavintoDB(arrayList);
            }

            @Override
            public void onMyResponseFailure(String message) {
            }
        });
    }

}
