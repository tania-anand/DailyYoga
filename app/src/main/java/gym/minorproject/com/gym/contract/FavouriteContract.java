package gym.minorproject.com.gym.contract;

public interface FavouriteContract {

    interface  viewer{
        void onSuccessCallback(String type ,String message);
        void onFailureCallback(String type ,String message);

    }
    interface presenter{

    }
}
