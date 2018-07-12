package social.application.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import social.application.R;
import social.application.profile.adapter.MyAdapter;

public class infiniteView extends AppCompatActivity {
    List<Integer> lstImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_view);

        initData();

        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)findViewById(R.id.horizontal_cycle);
        MyAdapter adapter = new MyAdapter(lstImages,getBaseContext());
        pager.setAdapter(adapter);


    }


    private void initData() {
        lstImages.add(R.drawable.foll6);
        lstImages.add(R.drawable.foll7);
        lstImages.add(R.drawable.foll8);

    }
}
