package social.application.explore;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;

import social.application.R;


public class ExploreActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        initViews();
        initData();
    }

    public void initViews(){
//        gridView = (GridView) findViewById(R.id.explore_grid_view);
//        gridView.setAdapter(new ExplorePostAdapter());
    }

    public void initData(){

    }

}
