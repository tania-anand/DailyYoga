package gym.minorproject.com.gym.contract;

public interface RegisterContract {

    interface presenter{
    }

    interface viewer{
        void onResponseSuccess(String message);
        void onResponseFailure(String message);
    }


}
