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
import android.widget.Toast;


import java.util.ArrayList;

import gym.minorproject.com.gym.bean.ExerciseYogaBean;
import gym.minorproject.com.gym.R;
import gym.minorproject.com.gym.contract.FavouriteContract;
import gym.minorproject.com.gym.helper.DBHelper;
import gym.minorproject.com.gym.helper.UtilActivity;
import gym.minorproject.com.gym.bean.ExerciseNameBean;
import gym.minorproject.com.gym.adapter.ExerciseAdapter1;
import gym.minorproject.com.gym.interfaces.MyResponseConnectivity;
import gym.minorproject.com.gym.presenter.FavouritePresenter;

public class Favourite extends UtilActivity implements AdapterView.OnItemClickListener,
        MyResponseConnectivity, FavouriteContract.viewer {
    GridView list;
    ArrayList<ExerciseNameBean> favYogaList;
    ExerciseAdapter1 favYogaAdapter;

    ExerciseYogaBean exerciseYogaType;

    FavouritePresenter presenter;

    int pos;
    ExerciseNameBean eb;

    /*
    * 1 == Retreive All
    * 2 == Delete Favourite
    */
    int requestType = 0;


    void init() {
        list = findViewById(R.id.grid_view);

        initMyResponseConnectivityListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        init();
        setTitle("Favourites");

        presenter = new FavouritePresenter(this,this);

        if(isNetworkConnected(Favourite.this)) {
            showDialog();
            presenter.retreiveAllFavs();
        }
        else {
            requestType = 1;
            showMsg(Favourite.this);
        }
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

    void alertDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"Delete","View"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        requestType = 2;
                        if(isNetworkConnected(Favourite.this)){
                            showDialog();
                            presenter.deleteExerciseFromFav(eb);
                        }else
                            showMsg(Favourite.this);
                        break;
                    case 1:
                        exerciseYogaType = new DBHelper(Favourite.this).getAsanaById(eb.getId());
                        Intent j = new Intent(Favourite.this, ExerciseDetails.class);
                        j.putExtra("yoga_bean", exerciseYogaType);
                        startActivity(j);
                        break;
                }
            }
        });
        builder.create().show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        eb = favYogaList.get(pos);
        alertDialogBox();
    }

    @Override
    public void onMyResponseConnectivity(int i) {
        dismissDialog();
        if(i==1){
            if(isNetworkConnected(Favourite.this)) {
                showDialog();
                if (requestType == 1) {
                    presenter.retreiveAllFavs();
                } else if (requestType == 2) {
                    presenter.deleteExerciseFromFav(eb);
                }
            }else
                showMsg(Favourite.this);
        }
    }

    /*
    * message type
    * "retreive"
    * "delete"
    */
    @Override
    public void onSuccessCallback(String type, String message) {
        dismissDialog();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        if (type.equals("delete")){
            favYogaList.remove(pos);
            favYogaAdapter.notifyDataSetChanged();
        }else if(type.equals("retreive")){
            favYogaList = presenter.getList();
            favYogaAdapter = new ExerciseAdapter1(Favourite.this, R.layout.grid_list_item, favYogaList);
            list.setAdapter(favYogaAdapter);
            list.setOnItemClickListener(Favourite.this);
        }

    }

    @Override
    public void onFailureCallback(String type, String message) {
        dismissDialog();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
}

