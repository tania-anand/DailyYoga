package gym.minorproject.com.gym.contract;

public interface ExerciseListContract {

    interface viewer{
        void onCallbackSuccess(String type,String message);
        void onCallbackFailure(String type,String message);
    }

    interface presenter{
    }
}
