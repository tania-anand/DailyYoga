package gym.minorproject.com.gym.bean;

import java.io.Serializable;

/**
 * Created by HP PAVILLION on 06-04-2017.
 */

public class ExerciseYogaBean implements Serializable {
    String Name_E;

    public ExerciseYogaBean(String name_E, String name_H, String steps, String benefits, String contraindications,
                            String video_Url, String images, String steps_Images, String muscles,
                            String exercise_Type, int id) {
        Name_E = name_E;
        Name_H = name_H;
        Steps = steps;
        Benefits = benefits;
        Contraindications = contraindications;
        Video_Url = video_Url;
        Images = images;
        Steps_Images = steps_Images;
        Muscles = muscles;
        Exercise_Type = exercise_Type;
        this.id = id;
    }

    public String getExercise_Type()
    {
        return Exercise_Type;
    }

    public void setExercise_Type(String exercise_Type)
    {
        Exercise_Type = exercise_Type;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    String Name_H;
    String Steps;
    String Benefits;
    String Contraindications;
    String Video_Url;
    String  Images;
    String Steps_Images;
    String Muscles;
    String Exercise_Type;
    int id;




    public String[] StringToStringArray(String s) {
        String[] url1;
        url1 = s.split("@");

        return url1;
    }





    public ExerciseYogaBean(String name_E, String name_H, String steps, String benefits, String contraindications,
                            String video_Url, String images, String steps_Images, String muscles) {
        Name_E = name_E;
        Name_H = name_H;
        Steps = steps;
        Benefits = benefits;
        Contraindications = contraindications;
        Video_Url = video_Url;
        Images = images;
        Steps_Images = steps_Images;
        Muscles=muscles;
    }


    public String getName_E()
    {
        return Name_E;
    }

    public void setName_E(String name_E)
    {
        Name_E = name_E;
    }

    public String getName_H()
    {
        return Name_H ;
    }

    public void setName_H(String name_H)
    {
        Name_H = name_H;
    }

    public String[] getSteps()
    {
        return StringToStringArray(Steps);
    }

    public void setSteps(String steps)
    {
        Steps = steps;
    }

    public String[] getBenefits()
    {
        return StringToStringArray(Benefits);
    }

    public void setBenefits(String benefits)
    {
        Benefits = benefits;
    }

    public String[] getContraindications() {
        return StringToStringArray(Contraindications);
    }

    public ExerciseYogaBean() {
    }

    public void setContraindications(String contraindications) {
        Contraindications = contraindications;
    }

    public String getVideo_Url()
    {
        return Video_Url;
    }

    public void setVideo_Url(String video_Url)
    {
        Video_Url = video_Url;
    }

    public String getImages()
    {
        return Images;
    }

    public void setImages(String images)
    {
        Images = images;
    }

    public String[] getSteps_Images()
    {
        return StringToStringArray(Steps_Images);
    }


    public void setSteps_Images(String steps_Images)
    {
        Steps_Images = steps_Images;
    }

    public String[] getMuscles()
{
    return StringToStringArray(Muscles);
}

    public void setMuscles(String steps_Images)
    {
        Muscles = steps_Images;
    }

    public String getStringSteps() {return Steps;}
    public String getStringStepsImages() {return Steps_Images;}
    public String getStringMuscles() {return Muscles;}
    public String getStringContraindications() {return Contraindications;}
    public String getStringBenefits() {return Benefits;}








}
