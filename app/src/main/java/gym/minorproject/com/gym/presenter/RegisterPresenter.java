package gym.minorproject.com.gym.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
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
import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.bean.SignUpBean;
import gym.minorproject.com.gym.contract.RegisterContract;
import gym.minorproject.com.gym.helper.NetworkCall;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.YogaUtil;
import gym.minorproject.com.gym.interfaces.MyResponseListener;

public class RegisterPresenter implements RegisterContract.presenter {

    private static final String TAG = "RegisterPresenter";
    private boolean isUpdateMode;
    private Context context;
    private RegisterContract.viewer viewer;

    @BindView(R.id.RName)
    EditText edName;

    @BindView(R.id.REmailId)
    EditText edEmail;

    @BindView(R.id.RPassword)
    EditText edPassword;

    private SignUpBean rcvbean;

    private FirebaseAuth mAuth;

    public RegisterPresenter(Context context,boolean isUpdateMode ,RegisterContract.viewer viewer,SignUpBean bean){
        this.context = context;
        this.isUpdateMode = isUpdateMode;
        this.viewer = viewer;
        this.rcvbean = bean;

        ButterKnife.bind(this,(Activity)context);
        if(isUpdateMode)
            setDataInEditText();
    }

    public void onClick(){
        if(validations()){
            if(isUpdateMode)
                insertOnCloud();
            else
                registerInFirebase();
        }
    }

    private void setDataInEditText() {
        edName.setText(rcvbean.getName());
        edEmail.setText(rcvbean.getEmailId());

        edPassword.setVisibility(View.GONE);
    }


    private boolean validations() {
        boolean flag=true;
        if(edName.getText().toString().isEmpty()) {
            edName.setError("Name cant be Empty");
            flag=false; }
        if(edEmail.getText().toString().isEmpty()) {

            edEmail.setError("EmailId cant be Empty");
            flag=false; }
        else {
            if(!(edEmail.getText().toString().contains("@") && edEmail.getText().toString().contains("."))){
                flag = false;
                edEmail.setError("Please Enter correct Email"); } }

        if(edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Password cant be Empty");
            flag=false; }
        else {
            if(edPassword.getText().toString().length()<8) {
                edPassword.setError("Minimum Length of Password should be 8");
                flag = false; } }
        return flag;
    }

    private void insertOnCloud(){
        String URL;
        if(isUpdateMode)
            URL= YogaUtil.UPDATE_DATA_PHP;
        else
            URL=YogaUtil.INSERT_DATA_PHP;

        final SignUpBean bean = new SignUpBean();


        JSONObject object= new JSONObject();
        try {
            object.put(YogaUtil.COLNAME,edName.getText().toString().trim());
            object.put(YogaUtil.COLEMAIL,edEmail.getText().toString().trim());
            object.put("TOKEN", FirebaseInstanceId.getInstance().getToken());

            if(isUpdateMode){
                bean.setName(edName.getText().toString().trim());
                bean.setEmailId(edEmail.getText().toString().trim());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkCall networkCall = NetworkCall.getMyDB();
        networkCall.initRequestQueue(context);
        networkCall.processRequest(Request.Method.POST,
                URL,
                object,
                new MyResponseListener() {
                    @Override
                    public void onMyResponseSuccess(boolean success, JSONObject jsonObject) {
                        // if success pass to display success
                        try {
                            if (success) {
                                viewer.onResponseSuccess(jsonObject.getString("message"));

                                if(isUpdateMode)
                                    SharedPreferencesUtil.getInstance(context).saveUser(bean);
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


    private void registerInFirebase(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString().trim(), edPassword.getText().toString().trim())
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            insertOnCloud();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            viewer.onResponseFailure(task.getException().getLocalizedMessage());
                        }
                    }
                });

    }
}
