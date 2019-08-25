package gym.minorproject.com.gym.interfaces;

import org.json.JSONObject;

/**
 * Created by tania on 07-Jan-16.
 */
public interface MyResponseListener {
    void onMyResponseSuccess(boolean success, JSONObject jsonObject);
    void onMyResponseFailure(String message);
}
