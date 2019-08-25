package gym.minorproject.com.gym.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import gym.minorproject.com.gym.services.FetchFavouritesService;
import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.SignUpBean;
import gym.minorproject.com.gym.contract.LoginContract;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

public class LoginPresenter implements LoginContract.presenter {

    private static final String TAG = "LoginPresenter";
    private Context context;
    private LoginContract.viewer viewer;

    @BindView(R.id.user_id)
    EditText eduserId;

    @BindView(R.id.password)
    EditText edpassword;

    private FirebaseAuth mAuth;

    public LoginPresenter(Context context, LoginContract.viewer viewer) {
        this.context = context;
        this.viewer = viewer;

        ButterKnife.bind(this,(Activity)context);
    }

    // validations for login page
    private boolean validateLoginFields() {
        boolean Flag = true;

        if (eduserId.getText().toString().isEmpty()) {
            eduserId.setError("User Id Cant Be Empty");
            Flag = false;
        }
        if (edpassword.getText().toString().isEmpty()) {
            edpassword.setError("Password Cant Be Empty");
            Flag = false;
        }
        return Flag;
    }


    public void onClickLogin(SignUpBean bean){
        if(validateLoginFields()){
            firebaseLogin(bean);
        }else
            viewer.onLoginFailure("Vaildation");
    }

    private void sendDataToServer(final SignUpBean bean){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(YogaUtil.COLEMAIL, bean.getEmailId());
            jsonObject.put("TOKEN", FirebaseInstanceId.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall.getMyDB().initRequestQueue(context);
        NetworkCall.getMyDB().processRequest(Request.Method.POST, YogaUtil.LOGINQUERY_DATA_PHP, jsonObject, new MyResponseListener() {
            @Override
            public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {

                String message = "Success";
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                viewer.onLoginSuccess(message);

                SharedPreferencesUtil.getInstance(context).setLoggedIn(true);
                SharedPreferencesUtil.getInstance(context).saveUser(bean);
                Intent i = new Intent(context, FetchFavouritesService.class);
                context.startService(i);
            }

            @Override
            public void onMyResponseFailure(String message) {
                viewer.onLoginFailure(message);
            }
        });

    }


    private void firebaseLogin(final SignUpBean bean){
        Log.d(TAG,bean.toString());
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(bean.getEmailId(), bean.getPassword())
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            sendDataToServer(bean);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            viewer.onLoginFailure(task.getException().getLocalizedMessage());
                        }
                    }
                });

    }

}
