package gym.minorproject.com.gym.contract;

public interface LoginContract {

    interface viewer{
        void onLoginSuccess(String message);
        void onLoginFailure(String message);

    }

    interface presenter{
    }


}
