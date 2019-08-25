package gym.minorproject.com.gym.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.Toast;


import java.util.ArrayList;

import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.adapter.ExerciseAdapter1;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.contract.ExerciseListContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.SharedPreferencesUtil;
import gym.minorproject.com.gym.helper.UtilActivity;
import gym.minorproject.com.gym.bean.ExerciseYogaBean;
import gym.minorproject.com.gym.interfaces.MyResponseConnectivity;
import gym.minorproject.com.gym.presenter.ExerciseListPresenter;

public class ExerciseList extends UtilActivity implements AdapterView.OnItemClickListener,
        ExerciseListContract.viewer, MyResponseConnectivity {
    private RatingBar ratingBar;
    private GridView list;
    private ArrayList<ExerciseNameBean> yogaList;
    private ExerciseAdapter1 yogaAdapter;
    private ExerciseYogaBean exerciseYogaBean;
    private String text_fav_dialog_box = "Mark Exercise As Favourite";

    private boolean loginflag=false;
    private String keyExerciseType;
    private int pos;
    private ExerciseNameBean eb;

    private ExerciseListPresenter presenter;

    void init() {
        list = findViewById(R.id.grid_view);
        ratingBar = findViewById(R.id.fav);

        initMyResponseConnectivityListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        init();

        Intent rcv = getIntent();
        boolean flag = rcv.hasExtra("keyExerciseType");
        if (flag) {
            keyExerciseType = rcv.getStringExtra("keyExerciseType");
        }

        presenter = new ExerciseListPresenter(this,this);

        yogaList = new DBHelper(this).getAsanasExcerciseType(keyExerciseType);

        yogaAdapter = new ExerciseAdapter1(this, R.layout.grid_list_item, yogaList);
        list.setAdapter(yogaAdapter);
        list.setOnItemClickListener(this);

        setTitle(keyExerciseType);

        loginflag = SharedPreferencesUtil.getInstance(getApplicationContext()).getLoggedIn();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        eb = yogaList.get(pos);

        if (eb.getFav_id() == 0) {
            text_fav_dialog_box = "Mark Exercise As Favourite";
        }
        if (eb.getFav_id() == 1) {
            text_fav_dialog_box = "Remove As Favourite";
        }
        alertDialogBox();
    }

    void alertDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {text_fav_dialog_box, "View Exercise"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (isNetworkConnected(ExerciseList.this)) {
                            favouriteSet();
                        }
                        else {
                            showMsg(ExerciseList.this);
                        }
                        break;
                    case 1:
                        exerciseYogaBean = new DBHelper(ExerciseList.this).getAsanaById(eb.getId());
                        Intent j = new Intent(ExerciseList.this, ExerciseDetails.class);
                        j.putExtra("yoga_bean",exerciseYogaBean);
                        startActivity(j);
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void favouriteSet(){
        if (loginflag) {
            showDialog();
            if (eb.getFav_id() == 0) {
                presenter.addExerciseToFav(eb);
            } else {
                presenter.deleteExerciseFromFav(eb);
            }
            yogaList.set(pos, new ExerciseNameBean(eb.getExercise_name(), eb.getExercise_image(), eb.getFav_id(), eb.getId()));
            yogaAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(ExerciseList.this, "First Register Yourself to use this Feature", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onCallbackSuccess(String type, String message) {
        dismissDialog();
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCallbackFailure(String type, String message) {
        dismissDialog();
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMyResponseConnectivity(int i) {
        if (i == 1) {
            if(isNetworkConnected(ExerciseList.this)){
                favouriteSet();
            }else
                showMsg(ExerciseList.this);
        }
    }
}
