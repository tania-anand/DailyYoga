package gym.minorproject.com.gym.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.ButterKnife;
import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.SignUpBean;
import gym.minorproject.com.gym.contract.ViewUserDetailsContract;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.ResponseParser;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

public class ViewUserDetailsPresenter {
    private Context context;
    private SignUpBean signUpBean;

    private ViewUserDetailsContract.viewer viewer;

    @BindView(R.id.VRName)
    TextView txtName;
    @BindView(R.id.VREmailId)
    TextView txtEmail;

    public ViewUserDetailsPresenter(Context context,ViewUserDetailsContract.viewer viewer){
        this.context = context;
        this.viewer = viewer;
        ButterKnife.bind(this,(Activity)context);
    }

    public void getUserDetails(){

        JSONObject object= new JSONObject();
        try {
            object.put(YogaUtil.COLEMAIL, SharedPreferencesUtil.getInstance(context).getUser().getEmailId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall networkCall = NetworkCall.getMyDB();
        networkCall.initRequestQueue(context);
        networkCall.processRequest(Request.Method.POST,
                YogaUtil.RETRIEVE_DATA_PHP,
                object,
                new MyResponseListener() {
                    @Override
                    public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {
                        // if success pass to display success
                        try {
                            if (success) {
                                signUpBean = new ResponseParser().parseUser(jsonObject);
                                SharedPreferencesUtil.getInstance(context).saveUser(signUpBean);
                                viewer.onResponseSuccess(jsonObject.getString("message"));
                                setDataToTextView();
                            }
                            else
                                viewer.onResponseFailure(jsonObject.getString("message"));
                        }catch(Exception e){
                            viewer.onResponseFailure("Exception");
                        }
                    }
                    @Override
                    public void onMyResponseFailure(String message) {
                        // if failure show message
                        viewer.onResponseFailure(message);

                    }
                });
    }

    public SignUpBean getSignUpBean(){
        return signUpBean;
    }


    private void setDataToTextView() {
        txtName.setText(signUpBean.getName());
        txtEmail.setText(signUpBean.getEmailId());
    }


}
