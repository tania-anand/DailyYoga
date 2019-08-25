package gym.minorproject.com.gym.contract;

public interface ViewUserDetailsContract {

    interface viewer{
            void onResponseSuccess(String message);
            void onResponseFailure(String message);
    }

    interface presenter{}
}
