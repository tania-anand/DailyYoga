package gym.minorproject.com.gym.bean;

/**
 * Created by HP PAVILLION on 25-02-2017.
 */

public class ExerciseNameBean {
    String exercise_name,exercise_image;
   int id;
    int fav_id;

    public ExerciseNameBean(String exercise_name, String exercise_image, int id) {
        this.exercise_name = exercise_name;
        this.exercise_image = exercise_image;
        this.id=id;
    }

    public ExerciseNameBean(String exercise_name, String exercise_image) {
        this.exercise_name = exercise_name;
        this.exercise_image = exercise_image;
    }


    public ExerciseNameBean(String exercise_name, String exercise_image, int fav_id, int id) {
        this.exercise_name = exercise_name;
        this.exercise_image = exercise_image;
        this.fav_id = fav_id;
        this.id = id;
    }

    public ExerciseNameBean() {
    }

    public String getExercise_name()
    {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name)
    {
        this.exercise_name = exercise_name;
    }

    public String getExercise_image()
    {
        return exercise_image;
    }

    public void setExercise_image(String exercise_image)
    {
        this.exercise_image = exercise_image;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }
}
