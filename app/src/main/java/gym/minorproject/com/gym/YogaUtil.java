package gym.minorproject.com.gym;

import android.net.Uri;

/**
 * Created by HP PAVILLION on 03-03-2017.
 */

public class YogaUtil
{
    public static final String DB_PATH ="/data/data/gym.minorproject.com.gym/databases/";
    public static final int DB_VERSION =1;
    public static final String DB_NAME ="YOGAAPP.db";
    public static final String TAB_NAME_YOGA_TYPE ="YOGA_TYPE";
    public static Uri uri_yoga_type=Uri.parse("content://com.gymapp/"+ YogaUtil.TAB_NAME_YOGA_TYPE);


    public static final String TAB_EXERCISE ="YOGA_EXERCISES";
    public static Uri uri_exercise_tab=Uri.parse("content://com.gymapp/"+ YogaUtil.TAB_EXERCISE);




    public static final String EXERCISE_NAME_H="EXERCISE_NAME_H";
    public static final String EXERCISE_NAME_E="EXERCISE_NAME_E";
    public static final String EXERCISE_STEPS="EXERCISE_STEPS";
    public static final String EXERCISE_BENEFITS="EXERCISE_BENEFITS";
    public static final String EXERCISE_MUSCLES="EXERCISE_MUSCLES";
    public static final String EXERCISE_IMAGE="EXERCISE_IMAGE";
    public static final String EXERCISE_STEP_IMAGES ="EXERCISE_STEP_IMAGES";
    public static final String EXERCISE_VIDEO="EXERCISE_VIDEO";
    public static final String EXERCISE_CONTRAINDICATIONS="EXERCISE_CONTRAINDICATIONS";
    public static final String EXERCISE_ID="_ID";
    public static final String FAV="FAV";
    public static final String EXERCISE_TYPE="EXERCISE_TYPE";


    // exercises name
    public static final String BACK="BACK";
    public static final String BREATHING="BREATHING";
    public static final String STANDING="STANDING";
    public static final String STOMACH="STOMACH";
    public static final String SITTING="SITTING";






    // functions url present in cloud
    public static final String INSERT_DATA_PHP = "https://taniaanand31.000webhostapp.com/yogaApp/insert.php";
    public static final String LOGINQUERY_DATA_PHP = "https://taniaanand31.000webhostapp.com/yogaApp/loginQuery.php";
    public static final String RETRIEVE_DATA_PHP = "https://taniaanand31.000webhostapp.com/yogaApp/retrieveRegistration.php";
    public static final String USER_ID_CHECK = "http://www.dailyyoga.esy.es/userChecktext.php";
    public static final String UPDATE_DATA_PHP = "https://taniaanand31.000webhostapp.com/yogaApp/update.php";

    // for favourite table
    public static final String INSERT_FAV = "https://taniaanand31.000webhostapp.com/yogaApp/insertFAV.php";
    public static final String DELETE_FAV = "https://taniaanand31.000webhostapp.com/yogaApp/deleteFAV.php";
    public static final String RETRIEVE_ALL_FAV= "https://taniaanand31.000webhostapp.com/yogaApp/retrieALLFAV.php";
    public static final String DELETEALL_FAV = "https://taniaanand31.000webhostapp.com/yogaApp/deleteALLFAV.php";



    //Shared Prefrence file detail
    public static final String SPFileName = "login_details";
    public static final String loginName = "loginName";
    public static final String loginPassword = "loginPassword";
    public static final String loginflag = "loginflag";


    // names of column of tables present in cloud of user table

    public static final String COLNAME ="NAME";
    public static final String COLEMAIL ="EMAIL";
    public static final String COLPHONENO ="PHONENO";
    public static final String COLPASSWORD="PASSWORD";
    public static final String COLLOGINID ="LOGINID";




}
